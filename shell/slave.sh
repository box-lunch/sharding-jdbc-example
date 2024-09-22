#!/bin/bash

# Check if the correct number of arguments are provided
if [ "$#" -ne 8 ]; then
    echo "Usage: $0 <CONTAINER_NAME> <MASTER_HOST> <slave_user> <slave_password> <MASTER_LOG_FILE> <MASTER_LOG_POS> <USERNAME> <USER_PASSWORD>"
    exit 1
fi

# Parse input arguments
CONTAINER_NAME=$1
MASTER_HOST=$2
slave_user=$3
slave_password=$4
MASTER_LOG_FILE=$5
MASTER_LOG_POS=$6
USERNAME=$7
USER_PASSWORD=$8

# Check if the container is running
if ! docker ps --format '{{.Names}}' | grep -q "^${CONTAINER_NAME}$"; then
    echo "Container $CONTAINER_NAME is not running."
    exit 1
fi
# Enter the Docker container and configure MySQL slave
sudo docker exec -it $CONTAINER_NAME env LANG=C.UTF-8 /bin/bash -c "
mysql -uroot -e \"
STOP SLAVE;
RESET SLAVE ALL;
FLUSH PRIVILEGES;
CHANGE MASTER TO MASTER_HOST='$MASTER_HOST', 
MASTER_USER='$slave_user', MASTER_PASSWORD='$slave_password', MASTER_PORT=3306,
MASTER_LOG_FILE='$MASTER_LOG_FILE', MASTER_LOG_POS=$MASTER_LOG_POS;
START SLAVE;
SHOW SLAVE STATUS\G;
SHOW MASTER STATUS;
\"
    USER_EXISTS=\$(mysql -uroot -sse \"SELECT COUNT(*) FROM mysql.user WHERE user = '$USERNAME';\")

    if [ \$USER_EXISTS -eq 1 ]; then
        echo 'User already exists. Modifying the user...';
        mysql -uroot -e \"
        ALTER USER '$USERNAME'@'%' IDENTIFIED WITH mysql_native_password BY '$USER_PASSWORD';
        \"
    else
        echo 'User does not exist. Creating the user...';
        mysql -uroot -e \"
        CREATE USER '$USERNAME'@'%' IDENTIFIED WITH mysql_native_password BY '$USER_PASSWORD';
        \"
    fi

 mysql -uroot -e \"
-- Grant all privileges on mytestdb to the user
GRANT SELECT ON *.* TO '$USERNAME'@'%';
FLUSH PRIVILEGES;
\"
"

# Check if the MySQL commands were successful
if [ $? -ne 0 ]; then
    echo "Failed to configure MySQL slave or create/alter user."
    exit 1
fi

echo "MySQL slave configured and user created/altered successfully."
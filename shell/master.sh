#!/bin/bash

# Check if the correct number of arguments are provided
if [ "$#" -ne 5 ]; then
    echo "Usage: $0 <container_name> <slave_username> <slave_password> <username_to_modify> <password>"
    exit 1
fi

# Assign arguments to variables
CONTAINER_NAME=$1
SLAVE_USERNAME=$2
SLAVE_PASSWORD=$3
USERNAME_TO_MODIFY=$4
USER_PASSWORD=$5

# Check if the container is running
if ! docker ps --format '{{.Names}}' | grep -q "^${CONTAINER_NAME}$"; then
    echo "Container $CONTAINER_NAME is not running."
    exit 1
fi

echo "Using Docker container with name $CONTAINER_NAME"

# Execute commands inside the running container
sudo docker exec -i $CONTAINER_NAME bash -c "
    echo 'Performing operations inside the container...'
    USER_EXISTS=\$(mysql -uroot -sse \"SELECT COUNT(*) FROM mysql.user WHERE user = '$SLAVE_USERNAME';\")
    
    if [ \$USER_EXISTS -eq 1 ]; then
        echo 'User already exists. Modifying the user...';
        mysql -uroot -e \"
        ALTER USER '$SLAVE_USERNAME'@'%' IDENTIFIED WITH mysql_native_password BY '$SLAVE_PASSWORD';
        \"
    else
        echo 'User does not exist. Creating the user...';
        mysql -uroot -e \"
        CREATE USER '$SLAVE_USERNAME'@'%' IDENTIFIED WITH mysql_native_password BY '$SLAVE_PASSWORD';
        \"
    fi

    mysql -uroot -e \"
    -- Grant replication slave privileges
    GRANT REPLICATION SLAVE ON *.* TO '$SLAVE_USERNAME'@'%';
    \"

    USER_EXISTS=\$(mysql -uroot -sse \"SELECT COUNT(*) FROM mysql.user WHERE user = '$USERNAME_TO_MODIFY';\")

    if [ \$USER_EXISTS -eq 1 ]; then
        echo 'User already exists. Modifying the user...';
        mysql -uroot -e \"
        ALTER USER '$USERNAME_TO_MODIFY'@'%' IDENTIFIED WITH mysql_native_password BY '$USER_PASSWORD';
        \"
    else
        echo 'User does not exist. Creating the user...';
        mysql -uroot -e \"
        CREATE USER '$USERNAME_TO_MODIFY'@'%' IDENTIFIED WITH mysql_native_password BY '$USER_PASSWORD';
        \"
    fi

    mysql -uroot -e \"
    -- Grant all privileges on mytestdb to the user
    GRANT ALL PRIVILEGES ON *.* TO '$USERNAME_TO_MODIFY'@'%';
    FLUSH PRIVILEGES;
    
    -- Reset master
    RESET MASTER;
    -- Show master status
    SHOW MASTER STATUS;
    \"
"
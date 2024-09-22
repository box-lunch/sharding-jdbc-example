# sharding-jdbc-example

一个快速搭建shading-jdbc的测试环境，及测试功能的样例项目。

## 创建容器环境

shell文件夹下包含了快速创建mysql容器的dockerfile文件，但是需要先创建文件夹及复制必要的conf文件。
配置主从服务器需要配置conf文件

```conf
## 主服务器
[mysqld]
# 服务器唯一id，默认值1
server-id=1
# 设置日志格式，
#STATEMENT：适用于大多数简单的复制场景，但在使用非确定性函数或复杂事务时可能会出现问题。
#ROW：适用于需要精确复制的场景，特别是在使用复杂事务或非确定性函数时。缺点是日志文件较大。
#MIXED：适用于需要在不同场景下自动切换日志格式的情况，提供了两者的优点，但也有可能带来复杂性。
binlog_format=ROW
# 二进制日志名，默认binlog
log-bin=binlog
# 设置需要复制的数据库，默认复制全部数据库
binlog-do-db=mytestdb
# 设置不需要复制的数据库
binlog-ignore-db=mysql
binlog-ignore-db=information_schema
[mysqld]

### 从服务器
[mysqld]
# 服务器唯一id，每台服务器的id必须不同，如果配置其他从机，注意修改id
server-id=3
# 中继日志名，默认xxxxxxxxxxxx-relay-bin
relay-log=relay-bin
[mysql]
```

- 使用compose.yaml创建服务。(请根据自己的实际情况，修改yaml文件里的挂载路径)
- 创建完以后以后，使用脚本完成主从服务器的设置

```shell
# <container_name> <slave_username> <slave_password> <username> <password>
sudo ./master.sh mysql-master1 master1_slave 123456 myuser mypassword 
sudo ./master.sh mysql-master2 master2_slave 123456 myuser mypassword 

# docker exec -it mysql-master1 bash -c
# 脚本会显示 从服务器所需的配置
# File    Position        Binlog_Do_DB    Binlog_Ignore_DB        Executed_Gtid_Set
# binlog.000001   157     mytestdb        mysql,information_schema

# File    Position        Binlog_Do_DB    Binlog_Ignore_DB        Executed_Gtid_Set
# binlog.000001   157     mytestdb        mysql,information_schema
# 修改从服务器的参数为上面脚本显示的参数
# <CONTAINER_NAME> <MASTER_HOST> <slave_user> <slave_password> <MASTER_LOG_FILE> <MASTER_LOG_POS> <USERNAME> <USER_PASSWORD>
sudo ./slave.sh mysql-slave1-1 172.20.0.2 master1_slave 123456 binlog.000001 157 myuser mypassword 
sudo ./slave.sh mysql-slave1-2 172.20.0.2 master1_slave 123456 binlog.000001 157 myuser mypassword 
sudo ./slave.sh mysql-slave2-1 172.20.0.3 master2_slave 123456 binlog.000001 157 myuser mypassword 
sudo ./slave.sh mysql-slave2-2 172.20.0.3 master2_slave 123456 binlog.000001 157 myuser mypassword
```

## 测试sharding

- 根据需要创建shading.yaml文件，或可重命名已创建的参考文件。详细配置参考sharing-jdbc官网，注意5.2.1版本以后不再有boot-starter，本项目是基于5.5的版本进行配置的。
- 对应entities文件的实体去数据库创建相应的表，仅在主库创建即可，从库会自动同步。若是需要创建自己的测试实体，除了表和实体以外，还要创建mapper文件。参考mybatis。
- 运行test下的测试方法。

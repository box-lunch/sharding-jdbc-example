-- mytestdb.t_dict definition

CREATE TABLE `t_dict` (
  `id` bigint NOT NULL,
  `dict` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `t_dict_unique` (`dict`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- mytestdb.t_order_0 definition

CREATE TABLE `t_order_0` (
  `id` bigint NOT NULL,
  `order_no` varchar(30) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `amount` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- mytestdb.t_order_1 definition

CREATE TABLE `t_order_1` (
  `id` bigint NOT NULL,
  `order_no` varchar(30) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `amount` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- mytestdb.t_order_item_0 definition

CREATE TABLE `t_order_item_0` (
  `id` bigint NOT NULL,
  `order_no` varchar(30) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `count` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- mytestdb.t_order_item_1 definition

CREATE TABLE `t_order_item_1` (
  `id` bigint NOT NULL,
  `order_no` varchar(30) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `count` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- mytestdb.t_user definition

CREATE TABLE `t_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
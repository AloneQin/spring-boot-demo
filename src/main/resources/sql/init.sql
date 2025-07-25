CREATE SCHEMA `spring-boot-demo` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci ;

use `spring-boot-demo`;

CREATE TABLE `phone` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `phone_code` varchar(45) NOT NULL COMMENT '手机编号',
  `name` varchar(45) NOT NULL COMMENT '手机名称',
  `brand` varchar(45) NOT NULL COMMENT '品牌',
  `prod_date` date NOT NULL COMMENT '生产日期',
  `price` decimal(7,2) NOT NULL COMMENT '售价',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='手机表';

insert into phone(phone_code, name, brand, prod_date, price, remark) values('24d3901c-42a4-4584-92f1-af835e1a2efa', 'iphone 12', 'apple', '2020-10-14', 6299.00, '磁吸无线充电超方便');
insert into phone(phone_code, name, brand, prod_date, price, remark) values('4eb6760d-a85b-4e88-b5c8-60f5e21f3e25', 'Mate 40 Pro', '华为', '2020-10-22', 6999.00, '莱卡三摄拍摄更清晰');
insert into phone(phone_code, name, brand, prod_date, price, remark) values('5472da83-0293-46b2-bdbc-2adc3786d932', 'X50 Pro', 'vivo', '2020-06-01', 4298.00, '星空模式好玩好用');
insert into phone(phone_code, name, brand, prod_date, price, remark) values('93b36718-61af-46b9-8148-7a1199d69bd4', 'Reno4 Pro', 'OPPO', '2020-06-05', 3799.00, '夜景视频拍摄很出色');
insert into phone(phone_code, name, brand, prod_date, price, remark) values('a4e1da74-54ee-459d-87e8-982db945d702', '小米10至尊版', '小米', '2020-08-11', 5299.00, '有线无线电池回血都超快');

CREATE TABLE `log` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `content` TINYTEXT NOT NULL COMMENT '内容',
  `created_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `created_by` VARCHAR(45) NOT NULL COMMENT '创建人',
  `updated_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `updated_by` varchar(45) NOT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT = '日志表';

INSERT INTO `spring-boot-demo`.`log` (`id`, `content`, `created_by`, `updated_by`) VALUES ('1', 'test', '1', '1');

CREATE TABLE `sys_user` (
    `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    `username` varchar(255) NOT NULL COMMENT '用户名',
    `real_name` varchar(255) NOT NULL COMMENT '真实姓名',
    `gender` TINYINT NOT NULL DEFAULT '1' COMMENT '性别: 0-女, 1-男',
    `age` INT DEFAULT NULL COMMENT '年龄',
    `created_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `created_by` VARCHAR(45) NOT NULL COMMENT '创建人',
    `updated_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `updated_by` varchar(45) NOT NULL COMMENT '修改人',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户表';







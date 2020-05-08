DROP DATABASE IF EXISTS `chatroom`;
CREATE DATABASE `chatroom` DEFAULT CHARACTER SET utf8;
USE `chatroom`;

-- ------------
-- user table  |
-- ------------
CREATE TABLE `sys_user` (
    `id` INT UNSIGNED AUTO_INCREMENT COMMENT 'userID',
    `username` VARCHAR(12) UNIQUE COMMENT 'username',
    `password` VARCHAR(100) NOT NULL COMMENT 'password',
     PRIMARY KEY(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ---------------
-- User role table|
-- ---------------
CREATE TABLE `sys_role` (
    `id` INT UNSIGNED AUTO_INCREMENT COMMENT '角色ID',
    `name` VARCHAR(12) UNIQUE COMMENT '角色名',
     PRIMARY KEY(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table profile (
    identity int,
    username varchar(12),
    nickname varchar (30),
    lastname varchar (25),
    firstname varchar (25),
    address varchar (100),
    city varchar (20),
    state varchar (20),
    zipcode varchar (10),
    email varchar(50),
    sex varchar (10),
    age varchar (5),
    phone varchar (20),
    description varchar (200)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------
-- Friend request|
-- --------------
CREATE TABLE requests (
    sender varchar(12),
    receiver varchar(12)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- -----------------
-- Friend List table|
-- -----------------
create table friendList(
    sender varchar (12),
    receiver varchar (12)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -----------------
-- history message  |
-- -----------------
create table history(
    sender varchar (12),
    receiver varchar (12),
    message varchar (255)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------
-- pending appointment |
-- --------------------
create table appointmentrequest(
    id int(10) AUTO_INCREMENT,
    sender varchar (12),
    receiver varchar (12),
    nickname varchar (30),
    date varchar (20),
    description varchar (255),
    location varchar (255),
    hour varchar (10),
    PRIMARY KEY(`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- ------------
-- appointment |
-- ------------
create table appointment(
    id int(10) AUTO_INCREMENT,
    sender varchar (12),
    receiver varchar (12),
    nickname varchar (30),
    date varchar (20),
    description varchar (255),
    location varchar (255),
    hour varchar (10),
    PRIMARY KEY(`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table reports(
    id int(10) AUTO_INCREMENT,
    doctor varchar (12),
    receiver varchar (12),
    path varchar (255),
    filename varchar (30),
    PRIMARY KEY(`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- ------------------
-- 创建用户角色关系表  |
-- ------------------
CREATE TABLE `sys_user_role` (
    `id` INT UNSIGNED AUTO_INCREMENT COMMENT '关系ID',
    `user_id` INT UNSIGNED COMMENT '用户ID',
    `role_id` INT UNSIGNED COMMENT '角色ID',
     PRIMARY KEY(`id`), 
     FOREIGN KEY(`user_id`) REFERENCES `sys_user`(`id`),
     FOREIGN KEY(`role_id`) REFERENCES `sys_role`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



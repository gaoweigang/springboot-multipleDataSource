--创建数据dev
CREATE DATABASE zkmkt;

--切换到数据库dev
USE zkmkt;

--查看当前正在使用的数据库
select DATABASE();

--查看当前库中存在哪些表
show tables;

--创建表
--人员信息表,仅仅是人员信息，公司在职人员并不一定可以登陆系统
create table `tbl_user` (
  `id` bigint(32) not null auto_increment comment '主键',
  `user_id` varchar(20) not null comment '用户id',
  `username` varchar(40) default null comment '员工姓名',
  `sex` varchar(10) NOT NULL DEFAULT '0' comment '性别, 0: 女， 1：男',
   birthday timestamp NULL DEFAULT NULL COMMENT '出生日期',
   card_no varchar(32) DEFAULT NULL COMMENT '身份证号',
  `email` varchar(40) default null comment '邮箱',
  `mobile` varchar(20) default null comment '电话',
  `position` varchar(10) default null comment '职位',
   status tinyint(1) NOT NULL DEFAULT '1' comment '人员状态： 1：在职，0：离职',
  `valid_flag` tinyint(1) not null DEFAULT '1' comment '记录是否有效 1:有效, 0:无效',
  `entry_time` timestamp not null default current_timestamp comment '入职时间',
  `resign_time` datetime default null comment '离职日期',
   create_time timestamp NULL DEFAULT NULL comment '创建时间',
   modify_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
   creator varchar(32) default null comment '创建人',
   modifier varchar(32) default null comment '修改人',
   remark varchar(255) default null comment '描述',
  primary key (`id`),
  unique key `unique_user_id` (`user_id`) using btree
) engine=innodb default charset=utf8 comment='人员信息表';


CREATE TABLE `tbl_role` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_code` varchar(255) DEFAULT NULL,
  `role_name` varchar(255) DEFAULT NULL,
  `valid_flag` tinyint(1) NOT NULL DEFAULT '1' COMMENT '0: 无效， 1有效',
   create_time timestamp NULL DEFAULT NULL comment '创建时间',
   modify_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
   creator varchar(32) default null comment '创建人',
   modifier varchar(32) default null comment '修改人',
   remark varchar(255) default null comment '描述',
  PRIMARY KEY (`id`),
  UNIQUE key `unique_role_code` (`role_code`) using btree
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='角色表';



INSERT INTO `tbl_user`(USER_ID,USERNAME,SEX,birthday,card_no,EMAIL,MOBILE,POSITION,VALID_FLAG,entry_time) VALUES ('00000012', 'gaoweigang', 0, '2018-3-12 16:40:00', '420881199101095170', '1245508721@qq.com', '13817191469', '开发', 1, '2018-3-12 16:40:00');
INSERT INTO `tbl_user`(USER_ID,USERNAME,SEX,birthday,card_no,EMAIL,MOBILE,POSITION,VALID_FLAG,entry_time) VALUES ('00000013', 'test', 0, '2018-3-12 16:40:00', '420881199101095172', '13817191469@163.com', '13817191469', '开发', 1, '2018-3-12 16:40:00');


INSERT INTO `tbl_role` VALUES (1, 'MARKET', '市场', 1, '2018-5-3 13:26:32', '2018-5-3 13:26:32', NULL, NULL, NULL);
INSERT INTO `tbl_role` VALUES (2, 'XLOAN', '信审', 1, '2018-5-8 13:53:57', '2018-5-3 13:26:34', NULL, NULL, NULL);
INSERT INTO `tbl_role` VALUES (3, 'ADMIN', '管理员', 1, '2018-5-7 22:02:54', '2018-5-3 13:26:36', NULL, NULL, NULL);
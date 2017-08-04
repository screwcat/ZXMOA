
###人员分组表
CREATE TABLE sys_personnel_group(
 `group_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '分组ID',
	`group_name` VARCHAR(50) DEFAULT NULL  COMMENT '分组名称',
	`people_number` int(11) DEFAULT NULL  COMMENT '分组人数',
	`create_user_id` varchar(50) DEFAULT NULL COMMENT '创建人id',
  `create_user_name` varchar(50) DEFAULT NULL COMMENT '创建人姓名',
  `create_timestamp` datetime NOT NULL COMMENT '创建时间',
  `last_update_user` varchar(50) DEFAULT NULL COMMENT '最后修改人',
  `last_update_timestamp` datetime DEFAULT NULL COMMENT '最后修改时间',
	`enable_flag` char(1) NOT NULL DEFAULT '1' COMMENT '标记该记录的启用状态1：正常，0：禁用（已删除）',
PRIMARY KEY(group_id)
) ENGINE = INNODB DEFAULT CHARSET=utf8 COMMENT='人员分组表';


###人员分组关系表
CREATE TABLE sys_personnel_group_relation(
	 `group_relation_id` int(11) not null AUTO_INCREMENT COMMENT '分组关系表ID',
  `group_id` int(11) NOT NULL  COMMENT '组ID',
	`personnel_id` int(11) NOT NULL  COMMENT '人员ID',
	`create_user_id` varchar(50) DEFAULT NULL COMMENT '创建人id',
  `create_user_name` varchar(50) DEFAULT NULL COMMENT '创建人姓名',
  `create_timestamp` datetime NOT NULL COMMENT '创建时间',
  `last_update_user` varchar(50) DEFAULT NULL COMMENT '最后修改人',
  `last_update_timestamp` datetime DEFAULT NULL COMMENT '最后修改时间',
	`enable_flag` char(1) NOT NULL DEFAULT '1' COMMENT '标记该记录的启用状态1：正常，0：禁用（已删除）',

PRIMARY KEY(group_relation_id)
) ENGINE = INNODB DEFAULT CHARSET=utf8 COMMENT='人员分组关系表';

##添加任务类型id和名称
alter TABLE sys_task add `task_type_id` INT DEFAULT NULL COMMENT '任务类型id';
alter TABLE sys_task add `task_type_name` VARCHAR(50) DEFAULT NULL COMMENT '任务类型名称';

update sys_task set task_type_id=1,task_type_name='任务';


##添加任务类型数据字典数据（注：参数47为第一条语句的id）
INSERT into sys_dict (sys_dict_id,dict_code,dict_name,dict_num,create_timestamp,remark,enable_flag)values (47,"DATA00027","任务类型",null,now(),"MOA任务类型",1);
INSERT INTO `sys_dict_data` VALUES ('', '1', '任务', null, '47', '0', null, '1', now(), 'moa任务类型', '1');
INSERT INTO `sys_dict_data` VALUES ('', '2', '会议', null, '47', '0', null, '1', now(), 'moa任务类型', '2');
INSERT INTO `sys_dict_data` VALUES ('', '3', '公告', null, '47', '0', null, '1', now(), 'moa任务类型', '3');

UPDATE sys_task SET finish_datetime=update_datetime  WHERE task_status IN (3,4) AND finish_type IN (2,3);
    
ALTER VIEW tasklist
			AS 
			SELECT 
				task.task_id AS task_id,
				task.task_pid AS task_pid,
				task.task_status AS task_status,
				task.task_title AS task_title,
				DATE_FORMAT(task.create_datetime,'%Y-%m-%d %H:%i') AS create_datetime,
				task.publish_user_name AS publish_user_name,
				task.publish_user_code AS publish_user_code,
				task.create_user_name AS create_user_name,
				task.create_user_code AS create_user_code,
				feed.feedback_user_name AS feedback_user_name,
				feed.feedback_user_code AS feedback_user_code,
				task.publicsh_unread AS publicsh_unread,
				task.feedback_unread AS feedback_unread,
				###IF(task_status = 3 OR task_status = 4,IF( finish_datetime<=finish_time,0,(FLOOR((UNIX_TIMESTAMP(finish_datetime) - UNIX_TIMESTAMP(finish_time))/ 3600 ))),IF( NOW()<=finish_time,0,(FLOOR((UNIX_TIMESTAMP(NOW()) - UNIX_TIMESTAMP(finish_time))/ 3600 ))) )AS delay_time,
				IF(task_status = 3 OR task_status = 4,0,IF( NOW()<=finish_time,0,(FLOOR((UNIX_TIMESTAMP(NOW()) - UNIX_TIMESTAMP(finish_time))/ 3600 ))) )AS delay_time,
				task.accept_feedback_id AS accept_feedback_id,
				feed.feedback_user_id AS feedback_user_id,
				task.task_type_id,task.task_type_name,task.create_id,
				task.task_num
			FROM 
				sys_task task,sys_task_feedback_user feed
			WHERE 
				task.task_id = feed.task_id  
				AND task.enable_flag = 1
				AND feed.enable_flag = 1;		    
		    

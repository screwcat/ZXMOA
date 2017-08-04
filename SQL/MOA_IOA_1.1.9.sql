CREATE TABLE sys_notice_head(
`notice_id` INT(11) AUTO_INCREMENT NOT NULL  COMMENT '公告表id',
 notice_title  VARCHAR(100) NOT NULL COMMENT '公告标题',
 publish_date  DATE NOT NULL COMMENT '发布时间',
 view_url  VARCHAR(100) DEFAULT NULL COMMENT '公告URL',
  notice_type  VARCHAR(100) DEFAULT NULL COMMENT '公告分类',
 `enable_flag` CHAR(1) DEFAULT '1',

PRIMARY KEY (`notice_id`)
)COMMENT "公告表";

INSERT INTO sys_notice_head (notice_title,publish_date,view_url,notice_type,enable_flag)
VALUES('卓信办公APP改版升级，华丽上线！',curdate(),'/moaNotice/notice_com.html','新版上线',1);

UPDATE pm_personnel SET personnel_name=TRIM(personnel_name),last_update_timestamp=NOW() WHERE personnel_name<>TRIM(personnel_name);
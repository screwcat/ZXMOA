ALTER TABLE sys_task_info ADD task_info_mini VARCHAR(200) COMMENT '缩略图路径';
ALTER TABLE sys_task_message ADD message_info_mini VARCHAR(200) COMMENT '缩略图路径';

UPDATE sys_task_info SET task_info_mini=task_info WHERE info_type=3;
UPDATE sys_task_message SET message_info_mini=message_info WHERE message_info_type=3;

###生产执行
GRANT EXECUTE ON FUNCTION hrm.findVolumnByDeptId TO 'moa'@'%';
GRANT SHOW VIEW ON hrm.* TO 'moa'@'%';
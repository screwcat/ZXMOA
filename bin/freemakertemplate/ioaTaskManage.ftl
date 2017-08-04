<#if user == '01'>
	<#if num == '01'>
		今天${feedback_user_name}尚未反馈任何任务消息。
	</#if>
	<#if num == '02'>
		<#if message_info_type == 1>
			${personnel_name}${personnel_shortCode}：${message_info}
		</#if>
		<#if message_info_type == 2>
			${personnel_name}${personnel_shortCode}：发送来一条语音消息!
		</#if>
		<#if message_info_type == 3>
			${personnel_name}${personnel_shortCode}：发送了一张图片!
		</#if>
	</#if>
	<#if num == '03'>
		${personnel_name}的任务需要您验收。
	</#if>
	<#if num == '04'>
		${personnel_name}将任务协同给${name}
	</#if>
	<#if num == '05'>
		${personnel_name}将任务转移给${name}。
	</#if>
	<#if num == '06'>
		${personnel_name}申请延期截止时间到${finish_time}。
	</#if>
</#if>
<#if user == '02'>
	<#if num == '01'>
		${accept_feedback_name}给您发布了一条任务。
	</#if>
	<#if num == '02'>
		${personnel_name}邀请您协同${accept_feedback_name}发布的任务。
	</#if>
	<#if num == '03'>
		${personnel_name}将${accept_feedback_name}发布的任务转移给您。
	</#if>
	<#if num == '04'>
		您今天有任务尚未反馈任何消息，请及时反馈。
	</#if>
	<#if num == '05'>
		您今天有任务尚未反馈任何消息，请及时反馈。
	</#if>
	<#if num == '06'>
		此任务截止时间是${finish_time},请尽快执行任务。
	</#if>
	<#if num == '07'>
		<#if message_info_type == 1>
			${personnel_name}${personnel_shortCode}：${message_info}
		</#if>
		<#if message_info_type == 2>
			${personnel_name}${personnel_shortCode}：发送来一条语音消息!
		</#if>
		<#if message_info_type == 3>
			${personnel_name}${personnel_shortCode}：发送了一张图片!
		</#if>
	</#if>
	<#if num == '08'>
		${personnel_name}:恭喜您，此任务已成功被验收。
	</#if>
	<#if num == '09'>
		${personnel_name}:非常抱歉，此任务需要继续执行，请您再接再厉。
	</#if>
	<#if num == '10'>
		${personnel_name}任务的截止时间已经更改为${finish_time}
	</#if>
	
	<#if num == '11'>
		${personnel_name}:发布的任务已经提前结束
	</#if>
	
	<#if num == '12'>
		${personnel_name}:发布的任务已撤销
	</#if>
	
	<#if num == '13'>
        ${accept_feedback_name}指派任务给${feedback_user_name}
    </#if>
	
	
</#if>
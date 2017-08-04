package com.zx.moa.ioa.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 任务管理模块APP操作按钮工具类
 * @author MATF
 *
 */
public class TaskButtonUtil {
	
	/**
	 * APP操作按钮
	 */
//	public final static Button[] BUTTONS = new Button[]{
//		new Button("任务详情", 1, 0),
//		new Button("验收", 2,8704),         //10 0010 0000 0000
//		new Button("重办", 3,8704),         //10 0010 0000 0000
//		new Button("更改截止时间", 4,4608),   //01 0010 0000 0000
//		new Button("查看子任务", 5,0),
//		new Button("提前结束", 6,4640),      //01 0010 0010 0000
//		new Button("撤销", 7,4624),         //01 0010 0001 0000
//		new Button("指派", 8,4352),         //01 0001 0000 0000
//		new Button("转移", 9,4368),         //01 0001 0001 0000
//		new Button("协同", 10,4352),        //01 0001 0000 0000
//		new Button("完成", 11,4352),        //01 0001 0000 0000
//		new Button("申请延期", 12,4354)      //01 0001 0000 0010
//	};
	
	/**
	 * 
	 * @param task_status 任务状态  1 进行中、2待验收、3结束、4撤销
	 * @param user 1反馈人、2被反馈人
	 * @param is_feedback 0未反馈、1反馈
	 * @param isdelay true有申请、false无申请
	 * @return
	 */
	public static List<Map<String, Object>> getButtons(int task_status,int user,String is_feedback,boolean isdelay){
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		//按钮map
		Map<String, Object> taskinfo = new HashMap<String ,Object>();
		taskinfo.put("button_id", "1");
		taskinfo.put("button_name", "任务详情");
		
		Map<String, Object> yanshou = new HashMap<String ,Object>();
		yanshou.put("button_id", "2");
		yanshou.put("button_name", "验收");
		
		Map<String, Object> chongban = new HashMap<String ,Object>();
		chongban.put("button_id", "3");
		chongban.put("button_name", "重办");
		
		Map<String, Object> changefinishtime = new HashMap<String ,Object>();
		changefinishtime.put("button_id", "4");
		changefinishtime.put("button_name", "更改截止时间");
		
		Map<String, Object> getChildenTask = new HashMap<String ,Object>();
		getChildenTask.put("button_id", "5");
		getChildenTask.put("button_name", "查看子任务");
		
		Map<String, Object> tiqianjieshu = new HashMap<String ,Object>();
		tiqianjieshu.put("button_id", "6");
		tiqianjieshu.put("button_name", "提前结束");
		
		Map<String, Object> chexiao = new HashMap<String ,Object>();
		chexiao.put("button_id", "7");
		chexiao.put("button_name", "撤销");
		
		Map<String, Object> zhipai = new HashMap<String ,Object>();
		zhipai.put("button_id", "8");
		zhipai.put("button_name", "指派");
		
		Map<String, Object> zhuanyi = new HashMap<String ,Object>();
		zhuanyi.put("button_id", "9");
		zhuanyi.put("button_name", "转移");
		
		Map<String, Object> xietong = new HashMap<String ,Object>();
		xietong.put("button_id", "10");
		xietong.put("button_name", "协同");
		
		Map<String, Object> wancheng  = new HashMap<String ,Object>();
		wancheng.put("button_id", "11");
		wancheng.put("button_name", "完成");
		
		Map<String, Object> shenqingyanqi  = new HashMap<String ,Object>();
		shenqingyanqi.put("button_id", "12");
		shenqingyanqi.put("button_name", "申请延期");
		
		

		//被反馈人
		if(user == 2){
			//任务状态
			if(task_status == 1){	//进行中
				//是否反馈
				if(is_feedback.equals("0") ){
					//是否有延期申请
					if(true == isdelay){
						list.add(taskinfo);
						list.add(getChildenTask);
						list.add(changefinishtime);
						list.add(tiqianjieshu);
						list.add(chexiao);
						return list;
					}else if(false == isdelay){
						list.add(taskinfo);
						list.add(getChildenTask);
						list.add(changefinishtime);
						list.add(tiqianjieshu);
						list.add(chexiao);
						return list;
					}
				}else if(is_feedback.equals("1")){
					//是否有延期申请
					if(true == isdelay){
						list.add(taskinfo);
						list.add(getChildenTask);
						list.add(changefinishtime);
						list.add(tiqianjieshu);
						return list;
					}else if(false == isdelay){
						list.add(taskinfo);
						list.add(getChildenTask);
						list.add(changefinishtime);
						list.add(tiqianjieshu);
						return list;
					}
				}
			}
			
			
			else if(task_status == 2){	//待验收
				//是否有反馈
				if(is_feedback.equals("0")){
					//是否有延期申请
					if(true == isdelay){
						list.add(taskinfo);
						list.add(getChildenTask);
						list.add(yanshou);
						list.add(chongban);
						list.add(changefinishtime);
						list.add(tiqianjieshu);
						return list;
					}else if(false == isdelay){
						list.add(taskinfo);
						list.add(getChildenTask);
						list.add(yanshou);
						list.add(chongban);
						list.add(changefinishtime);
						list.add(tiqianjieshu);
						return list;
					}
				}else if(is_feedback.equals("1")){
					//是否有延期申请
					if(true == isdelay){
						list.add(taskinfo);
						list.add(getChildenTask);
						list.add(yanshou);
						list.add(chongban);
						list.add(changefinishtime);
						list.add(tiqianjieshu);
						return list;
					}else if(false == isdelay){
						list.add(taskinfo);
						list.add(getChildenTask);
						list.add(yanshou);
						list.add(chongban);
						list.add(changefinishtime);
						list.add(tiqianjieshu);
						return list;
					}
				}	

			}
			
			
			else if(task_status == 3){	//结束
				//是否有反馈
				if(is_feedback .equals("0") ){
					//是否有延期申请
					if(true == isdelay){
						list.add(taskinfo);
						list.add(getChildenTask);
						return list;
					}else if(false == isdelay){
						list.add(taskinfo);
						list.add(getChildenTask);
						return list;
					}
				}else if(is_feedback.equals("1") ){
					//是否有延期申请
					if(true == isdelay){
						list.add(taskinfo);
						list.add(getChildenTask);
						return list;
					}else if(false == isdelay){
						list.add(taskinfo);
						list.add(getChildenTask);
						return list;
					}
				}
			}
			
			
			else if(task_status == 4){	//撤销
				//是否有反馈
				if(is_feedback.equals("0")){
					//是否有延期申请
					if(true == isdelay){
						list.add(taskinfo);
						list.add(getChildenTask);
						return list;
					}else if(false == isdelay){
						list.add(taskinfo);
						list.add(getChildenTask);
						return list;
					}
				}else if(is_feedback.equals("1")){
					//是否有延期申请
					if(true == isdelay){
						list.add(taskinfo);
						list.add(getChildenTask);
						return list;
					}else if(false == isdelay){
						list.add(taskinfo);
						list.add(getChildenTask);
						return list;
					}
				}
			}
		}
		//=================================================================
		
		//反馈人
				if(user == 1){
					//任务状态
					if(task_status == 1){	//进行中
						//是否反馈
						if(is_feedback.equals("0")){
							//是否有延期申请
							if(true == isdelay){
								list.add(taskinfo);
								list.add(getChildenTask);
								list.add(zhipai);
								list.add(zhuanyi);
								list.add(xietong);
								list.add(wancheng);
								return list;
							}else if(false == isdelay){
								list.add(taskinfo);
								list.add(getChildenTask);
								list.add(zhipai);
								list.add(zhuanyi);
								list.add(xietong);
								list.add(wancheng);
								list.add(shenqingyanqi);
								return list;
							}
						}else if(is_feedback.equals("1")){
							//是否有延期申请
							if(true  == isdelay){
								list.add(taskinfo);
								list.add(getChildenTask);
								list.add(zhipai);
								list.add(xietong);
								list.add(wancheng);
								return list;
							}else if(false  == isdelay){
								list.add(taskinfo);
								list.add(getChildenTask);
								list.add(zhipai);
								list.add(xietong);
								list.add(wancheng);
								list.add(shenqingyanqi);
								return list;
							}
						}
						
	
					}else if(task_status == 2){ //待验收
						//是否反馈
						if(is_feedback.equals("0")){
							//是否有延期申请
							if(true == isdelay){
								list.add(taskinfo);
								list.add(getChildenTask);
								return list;
							}else if(false == isdelay){
								list.add(taskinfo);
								list.add(getChildenTask);
								return list;
							}
						}else if(is_feedback.equals("1")){
							//是否有延期申请
							if(false == isdelay){
								list.add(taskinfo);
								list.add(getChildenTask);
								return list;
							}else if(true == isdelay){
								list.add(taskinfo);
								list.add(getChildenTask);
								return list;
							}
						}
					}
					
					
					else if(task_status == 3){	//结束
						//是否反馈
						if(is_feedback.equals("0")){
							//是否有延期申请
							if(true == isdelay){
								list.add(taskinfo);
								list.add(getChildenTask);
								return list;
							}else if(false == isdelay){
								list.add(taskinfo);
								list.add(getChildenTask);
								return list;
							}
						}else if(is_feedback.equals("1")){
							//是否有延期申请
							if(false == isdelay){
								list.add(taskinfo);
								list.add(getChildenTask);
								return list;
							}else if(true == isdelay){
								list.add(taskinfo);
								list.add(getChildenTask);
								return list;
							}
						}
					}
					
					
					else if(task_status == 4){
						list.add(taskinfo);
						list.add(getChildenTask);
						return list;
					}
				}
				
				
				//=================================================================
				//user = 3 
				if(user == 3 ){
					list.add(taskinfo);
					list.add(getChildenTask);
					return list;
				}
                

		return list;
	}
	
	
	//-------------------------------------查看子任务-------------------------------------------------------------
	
	
	/**
	 * 
	 * @param task_status 任务状态  1 进行中、2待验收、3结束、4撤销
	 * @param user 1反馈人、2被反馈人 3查看子任务人员 4创建人
	 * @param is_feedback 0未反馈、1反馈
	 * @param isdelay true有申请、false无申请
	 * @param is_readonly 1是查看子任务，0或者null是不查看子任务
	 * @return
	 */
	public static List<Map<String, Object>> getButtons(int task_status,int user,String is_feedback,boolean isdelay,int is_readonly){
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		//按钮map
		Map<String, Object> taskinfo = new HashMap<String ,Object>();
		taskinfo.put("button_id", "1");
		taskinfo.put("button_name", "任务详情");
		
		Map<String, Object> yanshou = new HashMap<String ,Object>();
		yanshou.put("button_id", "2");
		yanshou.put("button_name", "验收");
		
		Map<String, Object> chongban = new HashMap<String ,Object>();
		chongban.put("button_id", "3");
		chongban.put("button_name", "重办");
		
		Map<String, Object> changefinishtime = new HashMap<String ,Object>();
		changefinishtime.put("button_id", "4");
		changefinishtime.put("button_name", "更改截止时间");
		
		Map<String, Object> getChildenTask = new HashMap<String ,Object>();
		getChildenTask.put("button_id", "5");
		getChildenTask.put("button_name", "查看子任务");
		
		Map<String, Object> tiqianjieshu = new HashMap<String ,Object>();
		tiqianjieshu.put("button_id", "6");
		tiqianjieshu.put("button_name", "提前结束");
		
		Map<String, Object> chexiao = new HashMap<String ,Object>();
		chexiao.put("button_id", "7");
		chexiao.put("button_name", "撤销");
		
		Map<String, Object> zhipai = new HashMap<String ,Object>();
		zhipai.put("button_id", "8");
		zhipai.put("button_name", "指派");
		
		Map<String, Object> zhuanyi = new HashMap<String ,Object>();
		zhuanyi.put("button_id", "9");
		zhuanyi.put("button_name", "转移");
		
		Map<String, Object> xietong = new HashMap<String ,Object>();
		xietong.put("button_id", "10");
		xietong.put("button_name", "协同");
		
		Map<String, Object> wancheng  = new HashMap<String ,Object>();
		wancheng.put("button_id", "11");
		wancheng.put("button_name", "完成");
		
		Map<String, Object> shenqingyanqi  = new HashMap<String ,Object>();
		shenqingyanqi.put("button_id", "12");
		shenqingyanqi.put("button_name", "申请延期");
		
		if(is_readonly == 0){
			//被反馈人
			if(user == 2){
				//任务状态
				if(task_status == 1){	//进行中
					//是否反馈
					if(is_feedback.equals("0") ){
						//是否有延期申请
						if(true == isdelay){
							list.add(taskinfo);
							list.add(getChildenTask);
							list.add(changefinishtime);
							list.add(tiqianjieshu);
							list.add(chexiao);
							return list;
						}else if(false == isdelay){
							list.add(taskinfo);
							list.add(getChildenTask);
							list.add(changefinishtime);
							list.add(tiqianjieshu);
							list.add(chexiao);
							return list;
						}
					}else if(is_feedback.equals("1")){
						//是否有延期申请
						if(true == isdelay){
							list.add(taskinfo);
							list.add(getChildenTask);
							list.add(changefinishtime);
							list.add(tiqianjieshu);
							return list;
						}else if(false == isdelay){
							list.add(taskinfo);
							list.add(getChildenTask);
							list.add(changefinishtime);
							list.add(tiqianjieshu);
							return list;
						}
					}
				}
				
				
				else if(task_status == 2){	//待验收
					//是否有反馈
					if(is_feedback.equals("0")){
						//是否有延期申请
						if(true == isdelay){
							list.add(taskinfo);
							list.add(getChildenTask);
							list.add(yanshou);
							list.add(chongban);
							list.add(changefinishtime);
							list.add(tiqianjieshu);
							return list;
						}else if(false == isdelay){
							list.add(taskinfo);
							list.add(getChildenTask);
							list.add(yanshou);
							list.add(chongban);
							list.add(changefinishtime);
							list.add(tiqianjieshu);
							return list;
						}
					}else if(is_feedback.equals("1")){
						//是否有延期申请
						if(true == isdelay){
							list.add(taskinfo);
							list.add(getChildenTask);
							list.add(yanshou);
							list.add(chongban);
							list.add(changefinishtime);
							list.add(tiqianjieshu);
							return list;
						}else if(false == isdelay){
							list.add(taskinfo);
							list.add(getChildenTask);
							list.add(yanshou);
							list.add(chongban);
							list.add(changefinishtime);
							list.add(tiqianjieshu);
							return list;
						}
					}	

				}
				
				
				else if(task_status == 3){	//结束
					//是否有反馈
					if(is_feedback .equals("0") ){
						//是否有延期申请
						if(true == isdelay){
							list.add(taskinfo);
							list.add(getChildenTask);
							return list;
						}else if(false == isdelay){
							list.add(taskinfo);
							list.add(getChildenTask);
							return list;
						}
					}else if(is_feedback.equals("1") ){
						//是否有延期申请
						if(true == isdelay){
							list.add(taskinfo);
							list.add(getChildenTask);
							return list;
						}else if(false == isdelay){
							list.add(taskinfo);
							list.add(getChildenTask);
							return list;
						}
					}
				}
				
				
				else if(task_status == 4){	//撤销
					//是否有反馈
					if(is_feedback.equals("0")){
						//是否有延期申请
						if(true == isdelay){
							list.add(taskinfo);
							list.add(getChildenTask);
							return list;
						}else if(false == isdelay){
							list.add(taskinfo);
							list.add(getChildenTask);
							return list;
						}
					}else if(is_feedback.equals("1")){
						//是否有延期申请
						if(true == isdelay){
							list.add(taskinfo);
							list.add(getChildenTask);
							return list;
						}else if(false == isdelay){
							list.add(taskinfo);
							list.add(getChildenTask);
							return list;
						}
					}
				}
			}
			//=================================================================
			
			//反馈人
					if(user == 1){
						//任务状态
						if(task_status == 1){	//进行中
							//是否反馈
							if(is_feedback.equals("0")){
								//是否有延期申请
								if(true == isdelay){
									list.add(taskinfo);
									list.add(getChildenTask);
									list.add(zhipai);
									list.add(zhuanyi);
									list.add(xietong);
									list.add(wancheng);
									return list;
								}else if(false == isdelay){
									list.add(taskinfo);
									list.add(getChildenTask);
									list.add(zhipai);
									list.add(zhuanyi);
									list.add(xietong);
									list.add(wancheng);
									list.add(shenqingyanqi);
									return list;
								}
							}else if(is_feedback.equals("1")){
								//是否有延期申请
								if(true  == isdelay){
									list.add(taskinfo);
									list.add(getChildenTask);
									list.add(zhipai);
									list.add(xietong);
									list.add(wancheng);
									return list;
								}else if(false  == isdelay){
									list.add(taskinfo);
									list.add(getChildenTask);
									list.add(zhipai);
									list.add(xietong);
									list.add(wancheng);
									list.add(shenqingyanqi);
									return list;
								}
							}
							
		
						}else if(task_status == 2){ //待验收
							//是否反馈
							if(is_feedback.equals("0")){
								//是否有延期申请
								if(true == isdelay){
									list.add(taskinfo);
									list.add(getChildenTask);
									return list;
								}else if(false == isdelay){
									list.add(taskinfo);
									list.add(getChildenTask);
									return list;
								}
							}else if(is_feedback.equals("1")){
								//是否有延期申请
								if(false == isdelay){
									list.add(taskinfo);
									list.add(getChildenTask);
									return list;
								}else if(true == isdelay){
									list.add(taskinfo);
									list.add(getChildenTask);
									return list;
								}
							}
						}
						
						
						else if(task_status == 3){	//结束
							//是否反馈
							if(is_feedback.equals("0")){
								//是否有延期申请
								if(true == isdelay){
									list.add(taskinfo);
									list.add(getChildenTask);
									return list;
								}else if(false == isdelay){
									list.add(taskinfo);
									list.add(getChildenTask);
									return list;
								}
							}else if(is_feedback.equals("1")){
								//是否有延期申请
								if(false == isdelay){
									list.add(taskinfo);
									list.add(getChildenTask);
									return list;
								}else if(true == isdelay){
									list.add(taskinfo);
									list.add(getChildenTask);
									return list;
								}
							}
						}
						
						
						else if(task_status == 4){
							list.add(taskinfo);
							list.add(getChildenTask);
							return list;
						}
					}
					
					
					//=================================================================
					//user = 3 
					if(user == 3){
						list.add(taskinfo);
						list.add(getChildenTask);
						return list;
					}else if(user==4){
	                    list.add(taskinfo);
	                    list.add(getChildenTask);
	                    //如果有申请就返回提前结束，如果没有申请就返回撤消
	                    if(is_feedback.equals("1")){
	                        list.add(tiqianjieshu);
	                    }else{
	                        list.add(chexiao);
	                    }
	                    return list;
	                }
					
			
		}else if (is_readonly == 1){
			//查看子任务，只能有两个按钮
			list.add(taskinfo);
			list.add(getChildenTask);
			return list;
		}
		return list;

	}
	
	
	
	
//	/**
//	 * APP操作按钮
//	 */
//	public final static Button[] BUTTONS = new Button[]{
//		new Button("任务详情", 1, 0),
//		new Button("验收", 2,8704), //0010 0010 0000 0000
//		new Button("重办", 3,8704),
//		new Button("更改截止时间", 4,4608),
//		new Button("查看子任务", 5,0),
//		new Button("提前结束", 6,4640),
//		new Button("撤销", 7,4624),
//		new Button("指派", 8,4352),
//		new Button("转移", 9,4368),
//		new Button("协同", 10,4352),
//		new Button("完成", 11,4352),
//		new Button("申请延期", 12,4354)
//	};
//	
//	/**
//	 * 
//	 * @param task_status 任务状态  1 进行中、2待验收、3结束、4撤销
//	 * @param user 1反馈人、2被反馈人
//	 * @param is_feedback 0未反馈、1反馈
//	 * @param isdelay true有申请、false无申请
//	 * @return
//	 */
//	public static List<Map<String, Object>> getButtons(int task_status,int user,String is_feedback,boolean isdelay){
//		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//		for(Button b : BUTTONS){
//			//根据 任务状态、登录人类型、是否反馈判断是否显示按钮
//			if(isTrue(b.code>>12,1<<(task_status-1)) &&
//					isTrue(b.code>>8&15, 1<<(user-1)) &&
//					isTrue(b.code>>4&15, 1<<(Integer.parseInt(is_feedback)))&&
//					isTrue(b.code&15, isdelay?1:2)){
//				Map<String, Object> m = new HashMap<String, Object>();
//				m.put("button_id", b.button_id);
//				m.put("button_name", b.button_name);
//				list.add(m);
////				System.out.println("->"+b.button_name);
//			}
//		}
//
//		return list;
//	}
//	
//
//	/**
//	 * 是否可用
//	 * @param base 基础数据
//	 * @param param 校验参数
//	 * @return
//	 */
//	private static boolean isTrue(int base,int param){
//		return base == 0 || (base & param)!=0;
//	}	
//	
//	/**
//	 * APP操作按钮实体类
//	 * @author MATF
//	 *
//	 */
//	public static class Button{
//		//按钮名称
//		public String button_name;
//		//按钮ID
//		public int button_id;
//		//按钮逻辑编码（不返回前台）
//		public int code;
//		public Button(String button_name,int button_id,int code){
//			this.button_id = button_id;
//			this.button_name = button_name;
//			this.code = code;
//		}
//	}
//	
//	public static void main(String[] args){
////		int a = 0b10 0010 0001;
////		
//				
//		System.out.println("被反馈人--进行中--没有反馈信息:");
//		List<Map<String, Object>> temp = getButtons(1,2,"0",false);
//		for(int i = 0;i<temp.size();i++){
//			System.out.println(temp.get(i).get("button_name"));
//		}
//		System.out.println("被反馈人--进行中--有反馈信息:");
//		List<Map<String, Object>> temp2 = getButtons(1,2,"1",false);
//		for(int i = 0;i<temp2.size();i++){
//			System.out.println(temp2.get(i).get("button_name"));
//		}
//		System.out.println("被反馈人--待验收--有反馈信息:");
//		getButtons(2,2,"1",false);
//		System.out.println("被反馈人--结束--有反馈信息:");
//		getButtons(3,2,"1",false);
//		System.out.println("被反馈人-撤销--有反馈信息:");
//		getButtons(4,2,"1",false);
//		
//		System.out.println("反馈人--进行中--没有反馈信息--wu:");
//		getButtons(1,1,"0",false);
//		System.out.println("反馈人--进行中--有反馈信息:");
//		getButtons(1,1,"1",false);
//		System.out.println("反馈人--待验收--有反馈信息:");
//		getButtons(2,1,"1",false);
//		System.out.println("反馈人--结束--有反馈信息:");
//		getButtons(3,1,"1",false);
//		System.out.println("反馈人--进行中--没有反馈信息--you:");
//		getButtons(1,1,"0",true);
//		System.out.println("反馈人--进行中--有反馈信息:");
//		getButtons(1,1,"1",true);
//		System.out.println("反馈人--待验收--有反馈信息:");
//		getButtons(2,1,"1",true);
//		System.out.println("反馈人--结束--有反馈信息:");
//		getButtons(3,1,"1",true);
//	}
//	
	

	
}

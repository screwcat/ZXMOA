
var mainmenus = [{img:'menu-approve.png',text:'待我审批',view:'ioa_approve_list'},
                 {img:'menu-aboutme.png',text:'我的单据',view:'ioa_about_me'},
                 {img:'menu-customer.png',text:'客户管理',view:'crm_customer_list'},
                 
                 {img:'menu-loan.png', text:'贷款申请', view:'house_loan_apply'},
                 {img:'menu-house.png', text:'房贷办件', view:'house_loan_certificate'},
                 {img:'menu-housedoc.png', text:'房贷单据', view:'house_loan_list'}]; 

var bottominfo = [{text:'首页',img:'home',view:'home'},
                  {text:'办公',img:'office',view:'office'},
                  {text:'业务',img:'business',view:'business'},
                  {text:'我',img:'me',view:'me'}];

var fastmenus = [{img:'menu-approve.png',text:'待我审批'},
                 {img:'menu-aboutme.png',text:'我的单据'},
                 {img:'menu-customer.png',text:'客户管理'},
                 {img:'menu-loan.png',text:'贷款申请'},
 				 {img:'menu-house.png',text:'房贷办件'},
 				 {img:'menu-housedoc.png',text:'房贷单据'}];

var officemenu = [{text:'待我审批',url:'',img:'menu-approve-check.png',view:'ioa_approve_list'},
                  {text:'我的单据',url:'',img:'menu-aboutme-check.png',view:'ioa_about_me'}];

var businessmenu = [{text:'客户管理',url:'',img:'menu-customer-check.png',view:'crm_customer_list'},
                    {text:'贷款申请',url:'',img:'menu-loan-check.png',view:'house_loan_apply'},
                    {text:'房贷办件',url:'',img:'menu-house-check.png',view:'house_loan_certificate'},
                    {text:'房贷单据',url:'',img:'menu-housedoc-check.png',view:'house_loan_list'}];

/**
 * type:1 load页面、2 二级菜单、3 load页面不显示底部导航、4 每次重新加载页面
 * id:每个页面标识
 * url:请求页面时的页面地址
 * fun:页面加载成功时的调用函数
 * back:后退返回时，到达的view
 * bottom:底部菜单对应选中项索引
 * other_ids:加载页面时，同时清除的其他页面（主要用于页面3）
 * reloadData:重新加载数据方法 默认参数 true
 * 
 */
var viewpage = [{id:'home',type:1,url:'home.html',fun:'inithomepage',reloadData:'loadHomeApproveData'},
                {id:'office',type:2,menu:officemenu,title:'办公'},
                {id:'business',type:2,menu:businessmenu,title:'业务'},
                {id:'me',type:1,url:'me.html',fun:'initMePage'},
                {id:'crm_customer_list',type:1,url:'crm/customList.html',fun:'initcustomerpage',back:'business',bottom:'2',reloadData:'loadCusomterListData'},
                {id:'ioa_approve_list',type:1,url:'ioa/approval.html',back:'office',fun:'initApprovalPage',bottom:'1',reloadData:'loadApproveListData'},
                {id:'ioa_about_me',type:1,url:'ioa/bill.html',back:'office',fun:'initBillPage',bottom:'1',reloadData:'loadAboutMeListData'},
                {id:'ioa_order_info',type:3,url:'ioa/ApprovalDocuments.html',back:'ioa_about_me',fun:'initApprovalDocumentsPage',other_ids:'ioa_approve_info'},
                {id:'ioa_approve_info',type:3,url:'ioa/ApprovalDocuments.html',back:'ioa_approve_list',fun:'initApprovalDocumentsPage',other_ids:'ioa_order_info'},
                {id:'crm_customer_info',type:3,url:'crm/customerinfo.html',back:'crm_customer_list',fun:'initCustomerInfoPage'},
                {id:'contact_us',type:3,url:'contactUs.html',back:'me',fun:'initcustactpage',bottom:'3'},
                
                //贷款申请
                {id:'house_loan_apply',type:3,url:'wms/loan/houseLoanUpload.html',back:'business',fun:'initHouseLoanUpload',bottom:'2',other_ids:'add_house_loan_certificate,add_house_loan_list'},
                //房贷办件
                {id:'house_loan_certificate',type:1,url:'wms/loan/wmsHousingCertificates.html',back:'business',fun:'initdatas',bottom:'2'},
                //房贷单据
                {id:'house_loan_list',type:1,url:'wms/loan/houseLoanSearchList.html',back:'business',fun:'initHouseLoanSearch',bottom:'2'},
                //房贷单据明细
                {id:'house_loan_Info',type:3,url:'wms/loan/houseLoanInfo.html',back:'house_loan_list',fun:'initHouseLoanInfo'},
                //房贷办件上传
                {id:'add_house_loan_certificate',type:3,url:'wms/loan/houseLoanUpload.html',back:'house_loan_certificate',fun:'initHouseLoanUpload',other_ids:'house_loan_apply,add_house_loan_list'},
                //房贷补件上传
                {id:'add_house_loan_list',type:3,url:'wms/loan/houseLoanUpload.html',back:'house_loan_list',fun:'initHouseLoanUpload',other_ids:'house_loan_apply,add_house_loan_certificate'},
                ];



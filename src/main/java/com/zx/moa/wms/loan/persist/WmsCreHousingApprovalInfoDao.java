package com.zx.moa.wms.loan.persist;

import java.util.List;
import java.util.Map;

import com.zx.moa.util.gen.entity.wms.WmsCreHousingApprovalInfo;
import com.zx.moa.util.mybatis.BaseDao;
import com.zx.moa.util.mybatis.MyBatisRepository;


@MyBatisRepository
public interface WmsCreHousingApprovalInfoDao extends BaseDao<WmsCreHousingApprovalInfo> {
	/**
	 * 
	 * @Title: getBizProgressStatistical
	 * @Description: TODO(获取团队、门店、公司单据统计信息)
	 * @param paramsMap
	 * @return 
	 * @author: handongchun
	 * @time:2017年3月22日 上午11:46:37
	 * history:
	 * 1、2017年3月22日 handongchun 创建方法
	 */
    List<Map<String,Object>> getBizProgressStatistical(Map<String,Object> paramsMap);

    /**
     * @Title: searchAllInfo
     * @Description: <!-- mis查询单据历程信息使用 -->
     * @param map
     * @return 
     * @author: baisong
     * @time:2017年3月23日 下午5:16:16
     * history:
     * 1、2017年3月23日baisong 创建方法
     */
    List<Map<String, Object>> searchAllInfo(Map<String, Object> map);
}
package com.zx.moa.wms.loan.persist;

import java.util.List;
import java.util.Map;

import com.zx.moa.util.gen.entity.wms.WmsCreHousingApplyAtt;
import com.zx.moa.util.mybatis.BaseDao;
import com.zx.moa.util.mybatis.MyBatisRepository;

@MyBatisRepository
public interface WmsCreHousingApplyAttDao extends BaseDao<WmsCreHousingApplyAtt> {
    /**
     * Description :获取上传成功的图片数量
     * @url /wms/getPictureQuantity.do
     * @param queryInfo
     * @return Map
     * @author  baisong
     * @date 2016-6-6
     */
	List<Map<String,Object>> getPictureQuantity(Map<String,Object> map);
}
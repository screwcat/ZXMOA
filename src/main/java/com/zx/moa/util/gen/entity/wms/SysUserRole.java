package com.zx.moa.util.gen.entity.wms;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.zx.moa.util.mybatis.BaseEntity;
import com.zx.moa.wms.loan.vo.SysUserRoleVO;
import com.zx.platform.database.AbstractSimpleDao;


/*
 * @version 2.0
 */

public class SysUserRole implements BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer user_id;

    private Integer role_id;

    /**
     * default val cols name array
     */
    private static String[] defaultValColArr = {};

    /**
     * pk cols name array
     */
    private static String[] pkColArr = { "id" };

    private static String[] columnNameArr = { "id", "user_id", "role_id" };

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer obj)
    {
        id = obj;
    }

    public Integer getUser_id()
    {
        return user_id;
    }

    public void setUser_id(Integer obj)
    {
        user_id = obj;
    }

    public Integer getRole_id()
    {
        return role_id;
    }

    public void setRole_id(Integer obj)
    {
        role_id = obj;
    }

    /**
     * put all columns into a map
     */
    public void putInMap(Map<String, Object> paramMap)
    {
        paramMap.put("id", this.id);
        paramMap.put("user_id", this.user_id);
        paramMap.put("role_id", this.role_id);
    }

    /**
     * return the columns map
     */
    public Map<String, Object> getInfoMap()
    {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        this.putInMap(paramMap);
        return paramMap;
    }

    /**
     * remove default value and pk if it is null
     */
    private Map<String, Object> dealWithMap(Map<String, Object> paramMap)
    {
        Set<String> set = new HashSet<String>();
        for (String colName : defaultValColArr)
        {
            set.add(colName);
        }
        for (String colName : pkColArr)
        {
            set.add(colName);
        }
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext())
        {
            String colName = iterator.next();
            if (paramMap.get(colName) == null)
            {
                paramMap.remove(colName);
            }
        }
        return paramMap;
    }

    public Map<String, Object> setSymbolInsert(Map<String, Object> paramMap)
    {
        paramMap = dealWithMap(paramMap);
        Boolean flag = true;
        for (String ss : columnNameArr)
        {
            if (flag)
            {
                if (paramMap.containsKey(ss) && paramMap.get(ss) != null)
                {
                    paramMap.put(ss + "Symbol", " ");
                    flag = false;
                }
            }
            else
            {
                paramMap.put(ss + "Symbol", ", ");
            }
        }
        return paramMap;
    }

    private Map<String, Object> setSymbolUpdateWithNullValue(Map<String, Object> paramMap)
    {
        return setSymbolUpdate(dealWithMap(paramMap));
    }

    private Map<String, Object> setSymbolUpdateWithoutNullValue(Map<String, Object> paramMap)
    {
        return setSymbolUpdate(dealWithMapNullVal(paramMap));
    }

    public Map<String, Object> setSymbolUpdate(Map<String, Object> paramMap)
    {
        Boolean flag = true;
        for (String ss : columnNameArr)
        {
            if (flag)
            {
                if (paramMap.containsKey(ss) && paramMap.get(ss) != null && !Arrays.asList(pkColArr).contains(ss))
                {
                    paramMap.put(ss + "Symbol", " ");
                    flag = false;
                }
            }
            else
            {
                paramMap.put(ss + "Symbol", ", ");
            }
        }
        return paramMap;
    }

    /**
     * remove null
     */
    private Map<String, Object> dealWithMapNullVal(Map<String, Object> paramMap)
    {
        if (paramMap.get("id") == null)
        {
            paramMap.remove("id");
        }
        if (paramMap.get("user_id") == null)
        {
            paramMap.remove("user_id");
        }
        if (paramMap.get("role_id") == null)
        {
            paramMap.remove("role_id");
        }
        return paramMap;
    }

    /**
     * this table insert function, nonsupport null val
     */
    public int insertRecord(AbstractSimpleDao dao)
    {
        return dao.updateByTemplate(SqlString.AUTO2_SYS_USER_ROLE_INSERT, setSymbolInsert(this.getInfoMap()));
    }

    /**
     * delete records by primary key
     */
    public static int deleteRecordsByPK(AbstractSimpleDao dao, Integer id)
    {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", id);
        return dao.updateByTemplate(SqlString.AUTO2_SYS_USER_ROLE_DELETE, paramMap);
    }

    /**
     * this table update row function, need primary key, support null val
     */
    public int updateRecordAll(AbstractSimpleDao dao)
    {
        return dao.updateByTemplate(SqlString.AUTO2_SYS_USER_ROLE_UPDATE_ALL,
                                    setSymbolUpdateWithNullValue(this.getInfoMap()));
    }

    /**
     * this table update column function, need primary key, nonsupport null val
     */
    public int updateRecordColumn(AbstractSimpleDao dao)
    {
        return dao.updateByTemplate(SqlString.AUTO2_SYS_USER_ROLE_UPDATE,
                                    setSymbolUpdateWithoutNullValue(this.getInfoMap()));
    }

    /**
     * return single record domain by primary key
     */
    public static SysUserRole getRecordDomainByPK(AbstractSimpleDao dao, Integer id)
    {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", id);
        SysUserRole bean = dao.qryObj(SqlString.AUTO2_SYS_USER_ROLE_LIST, paramMap, SysUserRole.class);
        return bean;
    }

    /**
     * return single record vo by primary key
     */
    public static SysUserRoleVO getRecordVOByPK(AbstractSimpleDao dao, Integer id)
    {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", id);
        List<Map<String, Object>> resList = dao.queryForListByTemplate(SqlString.AUTO2_SYS_USER_ROLE_LIST, paramMap);
        SysUserRoleVO bean = new SysUserRoleVO();
        if (resList.size() > 0)
        {
            Map<String, Object> row = resList.get(0);
            if (row.get("id") != null)
            {
                bean.setId((Integer) row.get("id"));
            }
            if (row.get("user_id") != null)
            {
                bean.setUser_id((Integer) row.get("user_id"));
            }
            if (row.get("role_id") != null)
            {
                bean.setRole_id((Integer) row.get("role_id"));
            }
        }
        return bean;
    }

    /**
     * delete records by domain, nonsupport null val
     */
    public int deleteRecordsByDomain(AbstractSimpleDao dao)
    {
        return dao.updateByTemplate(SqlString.AUTO2_SYS_USER_ROLE_DELETE, dealWithMapNullVal(this.getInfoMap()));
    }

    /**
     * get list by "and" method, need new SysUserRole() include query-params
     */
    public static List<SysUserRole> getSingleTableListByAndMethod(AbstractSimpleDao dao, SysUserRole queryInfo,
                                                                  Boolean isExcludePKFlag)
    {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        if (queryInfo.getId() != null)
        {
            paramMap.put("id", queryInfo.getId());
        }
        if (queryInfo.getUser_id() != null)
        {
            paramMap.put("user_id", queryInfo.getUser_id());
        }
        if (queryInfo.getRole_id() != null)
        {
            paramMap.put("role_id", queryInfo.getRole_id());
        }
        return dao.qryObjList(SqlString.AUTO2_SYS_USER_ROLE_LIST, setSymbolPKPrior(paramMap, isExcludePKFlag, false),
                              SysUserRole.class);
    }

    /**
     * get list by "or" method, need new SysUserRole() include query-params
     */
    public static List<SysUserRole> getSingleTableListByOrMethod(AbstractSimpleDao dao, SysUserRole queryInfo,
                                                                 Boolean isExcludePKFlag)
    {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        if (queryInfo.getId() != null)
        {
            paramMap.put("id", queryInfo.getId());
        }
        if (queryInfo.getUser_id() != null)
        {
            paramMap.put("user_id", queryInfo.getUser_id());
        }
        if (queryInfo.getRole_id() != null)
        {
            paramMap.put("role_id", queryInfo.getRole_id());
        }
        return dao.qryObjList(SqlString.AUTO2_SYS_USER_ROLE_LIST_BY_OR,
                              setSymbolPKPrior(paramMap, isExcludePKFlag, true), SysUserRole.class);
    }

    private static Map<String, Object> setSymbolPKPrior(Map<String, Object> paramMap, Boolean exclude_pk, Boolean isOr)
    {
        if (paramMap == null || paramMap.size() == 0)
        {
            return paramMap;
        }
        if (exclude_pk)
        {
            for (String ss : pkColArr)
            {
                paramMap.put(ss + "Symbol", "exists");
            }
            paramMap.put("exclude_pk", true);
        }
        Boolean flag = true;
        if (isOr)
        {
            paramMap.put("params_exists", true);
            for (String ss : columnNameArr)
            {
                if (flag)
                {
                    if (paramMap.containsKey(ss) && !paramMap.containsKey(ss + "Symbol"))
                    {
                        paramMap.put(ss + "Symbol", " ");
                        flag = false;
                    }
                }
                else
                {
                    paramMap.put(ss + "Symbol", " or ");
                }
            }
        }
        return paramMap;
    }
}
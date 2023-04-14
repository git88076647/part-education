/*
 * Copyright (c) 2017 Baidu, Inc. All Rights Reserve.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.czyl.framework.table.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 *
 * @author tanghx
 */
public interface BillBaseMapper {

    /**
     * 
     * @param versionField 版本字段
     * @param tableName 表名
     * @param idField ID字段
     * @param id ID值
     * @return
     */
    Long getVersion(@Param("versionField") String versionField, @Param("tableName") String tableName, @Param("idField") String idField, @Param("id") Long id);

    /**
     *  <pre>
     *  Map<String,Object> params = new HashMap<>();
     *  params.put("sql","select count(*) from sys_user where user_code like #{code}");
     *  params.put("code","1%");
     *  </pre>
     * @param params
     * @return
     */
   List<Map> getList(Map<String,Object> params);
   
   /**
    *  <pre>
    *  Map<String,Object> params = new HashMap<>();
    *  params.put("sql","update sys_user set user_code=#{userCode} where user_id like #{userId}");
    *  params.put("userId","1");
    *  params.put("userCode","2");
    *  </pre>
    * @param params
    * @return
    */
   int update (Map<String,Object> params);
   
   /**
    *  <pre>
    *  Map<String,Object> params = new HashMap<>();
    *  params.put("sql","insert into table1(f1,f2) values(#{f1},#{f2})");
    *  params.put("f1","1");
    *  params.put("f2","2");
    *  </pre>
    * @param params
    * @return
    */
   int insert (Map<String,Object> params);

}

package com.czyl.project.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.czyl.common.exception.CustomException;
import com.czyl.common.utils.AppContextUtils;
import com.czyl.common.utils.DateUtils;
import com.czyl.framework.feign.dto.OrgDto;
import com.czyl.project.system.domain.SysUserPermission;
import com.czyl.project.system.mapper.SysUserPermissionMapper;
import com.czyl.project.system.service.ISysUserPermissionService;

import com.czyl.framework.feign.HnymServerClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户数据权限Service业务层处理
 *
 * @author fangxioaoh
 * @date 2021-12-06
 */
@Service
public class SysUserPermissionServiceImpl implements ISysUserPermissionService {

    public static final Long ZD_ID = 4985593261194625024L;
    public static final Long SY_ID = 4954732718587543552L;

    public static final Integer TYPE_ZD = 1;
    public static final Integer TYPE_SY = 2;

    @Autowired
    private SysUserPermissionMapper mapper;
    @Autowired
    HnymServerClient hnymServerClient;


    /**
     * 查询用户数据权限
     *
     * @param id 用户数据权限ID
     * @return 用户数据权限
     */
    @Override
    public SysUserPermission selectById(Long id){
        return mapper.selectById(id);
    }

    /**
     * 查询用户数据权限列表
     *
     * @param entity 用户数据权限
     * @return 用户数据权限
     */
    @Override
    public List<SysUserPermission> selectList(SysUserPermission entity){
        return selectList(entity,false);
    }
    /**
     * 查询用户数据权限列表
     *
     * @param entity 用户数据权限
     * @param fill 是否填充虚字段
     * @return 用户数据权限
     */
    @Override
    public List<SysUserPermission> selectList(SysUserPermission entity, boolean fill){
        List<SysUserPermission> list;
        if (CollectionUtil.isNotEmpty(entity.getIds())) {
            list = mapper.selectByIds(entity.getIds());
        } else {
            list = mapper.selectList(entity);
        }
        if(fill && list != null && !list.isEmpty()){
            fillEntity(list);
        }
        return list;
    }

    /**
     * 导入数据权限走的接口   因为导入是一条一条进来的 ，手动新增数据全是存在List中的
     * 所以新增一个方法方便一些
     * @param entity 角色数据权限
     * @return
     */
    @Override
    @Transactional
    public int importData(SysUserPermission entity){
        entity.setCreateTime(DateUtils.getNowDate());
        if(entity.getCreateBy() == null || entity.getCreateBy() == 0) {
            entity.setCreateBy(AppContextUtils.getUserId());
        }
        checkout(entity);
        return mapper.insert(entity);
    }

    /**
     * 新增用户数据权限
     *  注意  手动新增时候 数据都是存在List中的  比如 dataTypes  dataNodes  datas
     * @param entity 用户数据权限
     * @return 结果
     */
    @Override
    @Transactional
    public int insert(SysUserPermission entity){
        entity.setCreateTime(DateUtils.getNowDate());
        if(entity.getCreateBy() == null || entity.getCreateBy() == 0) {
            entity.setCreateBy(AppContextUtils.getUserId());
        }
        if (CollectionUtil.isEmpty(entity.getDataIds())){
            throw new CustomException("未获取到数据权限信息！");
        }
        //获取当前数据权限list
        List<OrgDto> orgIds = hnymServerClient.getOrgIds(entity.getDataIds());
        if (CollectionUtil.isEmpty(orgIds)){
            throw new CustomException("未获取到数据权限信息！");
        }
        //终端list
        List<OrgDto> zdlist = orgIds.stream().filter(o -> o.getJglxId().equals(ZD_ID)).collect(Collectors.toList());
        //商业list
        List<OrgDto> sylist = orgIds.stream().filter(o -> o.getJglxId().equals(SY_ID)).collect(Collectors.toList());
        //创建集合来存储需要插入的数据
        List<SysUserPermission> listData = new ArrayList<>();
        //将分组的数据权限   存入用户 权限类型 以及权限节点
        splitData(entity, zdlist,TYPE_ZD ,listData);
        splitData(entity, sylist,TYPE_SY ,listData);
        //将所选权限分配到每个选择的用户 权限类型 权限菜单
        if (listData != null && !listData.isEmpty()){
            listData.forEach(o ->{
                checkout(o);
                mapper.insert(o);
            });
            return listData.size();
            //return mapper.insertBatch(listData);
        }
        return 0;
    }

    /**
     *
     * @param entity
     * @param dataList
     * @param DataType
     * @param listData
     */
    private void splitData(SysUserPermission entity, List<OrgDto> dataList, Integer DataType, List<SysUserPermission> listData) {
        if (entity.getDataNodes()!= null && !entity.getDataNodes().isEmpty()){
            entity.getDataNodes().forEach(node ->{
                if (dataList != null && !dataList.isEmpty()){
                    dataList.forEach(data -> {
                        SysUserPermission db = new SysUserPermission();
                        //拷贝 用户 版本 创建人 时间 等
                        BeanUtils.copyProperties(entity,db);
                        //设置权限数据id
                        db.setDataId(data.getId());
                        //设置权限类型
                        db.setDataType(DataType);
                        //设置权限节点
                        db.setDataNode(node);
                        listData.add(db);
                    });
                }
            });

        }
    }

    /**
     * 检验重复
     * @param entity
     */
    public void checkout(SysUserPermission entity){
        if (entity.getDataId()==null){
            throw new CustomException("数据权限不能为空");
        }
        if (entity.getDataNode()==null){
            throw new CustomException("节点不能为空");
        }
        if (entity.getDataType()==null){
            throw new CustomException("权限类型不能为空");
        }
        if (entity.getUserId()==null){
            throw new CustomException("用户不能为空");
        }
        SysUserPermission db = mapper.checkout(entity);
        if (db != null){
            throw new CustomException(String.format("当前用户已拥有类型[%s],节点[%s],数据[%S]的权限！"
                    ,db.getDataType()==1?"终端":"商业",db.getDataNode()==1?"发货":(db.getDataNode()==2?"流向":"政策"),db.getDataIdName()));
        }
    }


    /**
     * 修改用户数据权限
     *
     * @param entity 用户数据权限
     * @return 结果
     */
    @Override
    @Transactional
    public int update(SysUserPermission entity){
        entity.setUpdateTime(DateUtils.getNowDate());
        if(AppContextUtils.getUserId2() != null && AppContextUtils.getUserId2() > 0) {
            entity.setUpdateBy(AppContextUtils.getUserId2());
        }
        return mapper.update(entity);
    }

    /**
     * 批量删除用户数据权限
     *
     * @param ids 需要删除的用户数据权限ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteByIds(List<Long> ids){
        return mapper.deleteByIds(ids);
    }

    /**
     * 删除用户数据权限信息
     *
     * @param id 用户数据权限ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteById(Long id){
        return mapper.deleteById(id);
    }



    /**
     * 填充 权限实体
     *
     * @param list
     */
    private void fillEntity(List<SysUserPermission> list) {
        list.forEach(o ->{
            switch (o.getDataType()) {
                case 1:
                    o.setDataTypeName("终端");
                case 2:
                    o.setDataTypeName("商业");
            }
            switch (o.getDataNode()) {
                case 1:
                    o.setDataNodeName("发货计算");
                case 2:
                    o.setDataNodeName("流向计算");
                case 3:
                    o.setDataNodeName("政策档案");
            }
        });

    }
}


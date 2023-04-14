package com.czyl.project.report.service.impl;

import com.czyl.common.exception.CustomException;
import com.czyl.common.meta.DataResultMeta;
import com.czyl.common.report.itf.IDataQuery;
import com.czyl.common.report.itf.IQueryResultDataVO;
import com.czyl.common.utils.DateUtils;
import com.czyl.common.utils.StringUtils;
import com.czyl.project.report.domain.RepSemantic;
import com.czyl.project.report.mapper.RepSemanticMapper;
import com.czyl.project.report.service.IRepSemanticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 语义模型Service业务层处理
 *
 * @author tanghx
 * @date 2020-04-03
 */
@Service
public class RepSemanticServiceImpl implements IRepSemanticService {
    @Autowired
    private RepSemanticMapper repSemanticMapper;

    /**
     * 默认的 查询实现类 如果 数据源没有配置查询类
     */
    @Value("${report.config.defaultQueryClass}")
    private String defaultQueryClass;

    /**
     * 查询语义模型
     *
     * @param pkSemantic 语义模型ID
     * @return 语义模型
     */
    @Override
    public RepSemantic selectRepSemanticById(Long pkSemantic) {
        return repSemanticMapper.selectRepSemanticById(pkSemantic);
    }

    /**
     * 查询语义模型列表
     *
     * @param repSemantic 语义模型
     * @return 语义模型
     */
    @Override
    public List<RepSemantic> selectRepSemanticList(RepSemantic repSemantic) {
        return repSemanticMapper.selectRepSemanticList(repSemantic);
    }

    /**
     * 新增语义模型
     *
     * @param repSemantic 语义模型
     * @return 结果
     */
    @Override
    public int insertRepSemantic(RepSemantic repSemantic) {
        repSemantic.setCreateTime(DateUtils.getNowDate());
        return repSemanticMapper.insertRepSemantic(repSemantic);
    }

    /**
     * 修改语义模型
     *
     * @param repSemantic 语义模型
     * @return 结果
     */
    @Override
    public int updateRepSemantic(RepSemantic repSemantic) {
        repSemantic.setUpdateTime(DateUtils.getNowDate());
        return repSemanticMapper.updateRepSemantic(repSemantic);
    }

    /**
     * 批量删除语义模型
     *
     * @param pkSemantics 需要删除的语义模型ID
     * @return 结果
     */
    @Override
    public int deleteRepSemanticByIds(Long[] pkSemantics) {
        return repSemanticMapper.deleteRepSemanticByIds(pkSemantics);
    }

    /**
     * 删除语义模型信息
     *
     * @param pkSemantic 语义模型ID
     * @return 结果
     */
    @Override
    public int deleteRepSemanticById(Long pkSemantic) {
        return repSemanticMapper.deleteRepSemanticById(pkSemantic);
    }

    @Override
    public IQueryResultDataVO exec(RepSemantic repSemantic, Map<Integer, Object> parmarMap)
            throws CustomException, ClassNotFoundException
            , IllegalAccessException, InstantiationException, SQLException {

        IDataQuery queryDao = buildDataQueryByRepSemantic(repSemantic);
        IQueryResultDataVO queryResultDataVO = queryDao.queryAll(parmarMap);

        return queryResultDataVO;
    }

    @Override
    public DataResultMeta getResultMeta(RepSemantic repSemantic) throws CustomException
            , ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        IDataQuery queryDao = buildDataQueryByRepSemantic(repSemantic);
        DataResultMeta dataResultMate = queryDao.getDataResultMate();

        return dataResultMate;
    }

    @Override
    public IDataQuery buildDataQueryByRepSemantic(RepSemantic repSemantic) throws ClassNotFoundException
            , IllegalAccessException, InstantiationException {
        Assert.notNull(repSemantic, "语义模型不存在");
        final String queryClass = StringUtils.isEmpty(repSemantic.getQuerclass()) ?
                defaultQueryClass : repSemantic.getQuerclass();

        if (StringUtils.isEmpty(queryClass)) {
            throw new NullPointerException("查询处理类不存在");
        }

        Class<?> queryClz = this.getClass().getClassLoader().loadClass(queryClass);
        if (!IDataQuery.class.isAssignableFrom(queryClz)) {
            throw new CustomException("查询处理类必须实现查询接口");
        }

        IDataQuery queryDao = (IDataQuery) queryClz.newInstance();
        queryDao.setRepSemantic(repSemantic);

        return queryDao;
    }
}

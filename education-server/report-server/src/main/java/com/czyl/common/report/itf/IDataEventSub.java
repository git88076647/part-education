package com.czyl.common.report.itf;

import com.czyl.common.enums.DataEventTypeEnum;
import com.czyl.common.exception.CustomException;

/**
 * 自由报表 数据查询或者其他操作事件的 监听类 <br/>
 * <br/>
 * <br/>
 *
 * @author air Email:209308343@qq.com
 * @date 2020/4/2 0002 15:43
 * @project
 * @Version
 * @see com.czyl.common.enums.DataEventTypeEnum
 */
public interface IDataEventSub {
    /**
     * 返回订阅的消息类型
     *
     * @return
     */
    DataEventTypeEnum getSubType();

    /**
     * 如果消息发生了，会调用这个方法 处理消息（同步 同线程）
     *
     * @param query
     * @param mata
     * @return false 不执行剩下的订阅类
     * @throws CustomException
     */
    boolean process(IDataQuery query, IQueryResultDataVO mata) throws CustomException;
}

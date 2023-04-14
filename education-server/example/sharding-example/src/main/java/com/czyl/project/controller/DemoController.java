package com.czyl.project.controller;

import java.util.Date;
import java.util.List;

import org.apache.shardingsphere.api.hint.HintManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.czyl.common.utils.DateUtils;
import com.czyl.common.web.domain.AjaxResult;
import com.czyl.framework.web.controller.BaseController;
import com.czyl.framework.web.page.TableDataInfo;
import com.czyl.project.domain.Material;
import com.czyl.project.domain.Msg;
import com.czyl.project.domain.OrderDetailEntity;
import com.czyl.project.domain.OrderEntity;
import com.czyl.project.mapper.MaterialMapper;
import com.czyl.project.mapper.MsgMapper;
import com.czyl.project.mapper.ShardingInsertBatchMapper;
import com.czyl.project.mapper.ShardingXXMapper;
import com.google.common.collect.Lists;

/**
 * 演示
 * 
 * @author tanghx
 */
@RestController
@RequestMapping("/test")
public class DemoController extends BaseController {

	@Autowired
	MaterialMapper materialMapper;

	@Autowired
	ShardingXXMapper shardingXXMapper;

	@Autowired
	ShardingInsertBatchMapper shardingInsertBatchMapper;
	
	@Autowired
	MsgMapper msgMapper;

	/**
	 * http://127.0.0.1:9001/example1-api/test/list 获取参数配置列表
	 * <br/>
	 * 分表 根据分片字段查询
	 */
	@GetMapping("/list")
	public TableDataInfo list() {
		startPage();
		List<OrderEntity> list = shardingXXMapper.selectList(DateUtils.parseDate("2020-05-22"));
		return getDataTable(list);
	}

	/**
	 * http://127.0.0.1:9001/example1-api/test/between?beginTime=2020-05-22&endTime=2021-05-22
	 * <br/>
	 * 分表 区间查询
	 * @return
	 */
	@GetMapping("/between")
	public TableDataInfo between() {
		startPage();
		List<OrderEntity> list = shardingXXMapper.selectListBetween(DateUtils.parseDate("2020-05-22"), DateUtils.parseDate("2021-05-22"));
		return getDataTable(list);
	}

	/**
	 * http://127.0.0.1:9001/example1-api/test/range?beginTime=2020-05-22&endTime=2021-05-22
	 * <br/>
	 * 分表 区间查询,强制走主数据源不走从库查
	 * @return
	 */
	@GetMapping("/range")
	public TableDataInfo range() {
		// 强制走 master数据源
		HintManager.getInstance().setMasterRouteOnly();
		startPage();
		List<OrderEntity> list = shardingXXMapper.selectListRange(DateUtils.parseDate("2020-05-22"), DateUtils.parseDate("2021-05-22"));
		return getDataTable(list);
	}

	/**
	 * http://127.0.0.1:9001/example1-api/test/getInfo
	 * 分表查询
	 * @return
	 */
	@GetMapping("/getInfo")
	public AjaxResult getInfo() {
		OrderEntity info = shardingXXMapper.selectById(1L);
		return AjaxResult.success(info);
	}

	/**
	 * http://127.0.0.1:9001/example1-api/test/getDetail
	 * <br/>
	 * 表关联  分表 全路由查询
	 * @return
	 */
	@GetMapping("/getDetail")
	public AjaxResult getDetail() {
		OrderDetailEntity info = shardingXXMapper.selectByPId(1L);
		return AjaxResult.success(info);
	}

	/**
	 * http://127.0.0.1:9001/example1-api/test/insert
	 * <br/>
	 * 分表插入
	 * @return
	 */
	@GetMapping("/insert")
	public AjaxResult insert() {
		OrderEntity order = new OrderEntity();
		order.setBilldate(new Date());
		order.setVnote("qqqqqqqqqqqqqqqqqqqq");
		int ret = shardingXXMapper.insert(order);
		return toAjax(ret);
	}

	/**
	 * http://127.0.0.1:9001/example1-api/test/update
	 * <br/>
	 * 根据非分片字段更新(全路由)
	 * @return
	 */
	@GetMapping("/update")
	public AjaxResult update() {
		OrderEntity order = new OrderEntity();
		order.setId(1L);
		order.setBilldate(new Date());
		order.setVnote("wwwwwwwwwwwwww");
		order.setVersion(1L);
		int ret = shardingXXMapper.update(order);
		return toAjax(ret);
	}

	/**
	 * http://127.0.0.1:9001/example1-api/test/insertBatch 
	 * <br/>
	 * 不分表 批量插入
	 * 
	 * @return
	 */
	@GetMapping("/insertBatch")
	public AjaxResult insertBatch() {
		List<Material> list = Lists.newLinkedList();
		for (int i = 0; i < 3; i++) {
			Material e = new Material();
			list.add(e);
			e.setName("测试" + i);
			e.setVersion(1L);
		}

		int ret = materialMapper.insertBatch(list);
		return toAjax(ret);
	}

	/**
	 * http://127.0.0.1:9001/example1-api/test/insertSharding
	 * <br/>
	 * 分表 批量插入
	 * @return
	 */
	@GetMapping("/insertSharding")
	public AjaxResult insertBatchSharding() {
		List<OrderEntity> list = Lists.newLinkedList();
		for (int i = 0; i < 2; i++) {
			OrderEntity order = new OrderEntity();
			list.add(order);
			order.setBilldate(new Date());
			order.setVnote("备注" + i);
			order.setVersion(1L);
		}
		for (int i = 0; i < 2; i++) {
			OrderEntity order = new OrderEntity();
			list.add(order);
			order.setBilldate(DateUtils.parseDate("2021-05-26"));
			order.setVnote("2021备注" + i);
			order.setVersion(1L);
		}
		int ret = shardingInsertBatchMapper.insertBatch(list);
		return toAjax(ret);
	}

	/**
	 * http://127.0.0.1:9001/example1-api/test/insertSharding2
	 * <br/>
	 * 分库分表 批量插入
	 * @return
	 */
	@GetMapping("/insertSharding2")
	public AjaxResult insertBatchSharding2() {
		List<Msg> list = Lists.newLinkedList();
		for (int i = 0; i < 2; i++) {
			Msg order = new Msg();
			list.add(order);
			order.setSenddate(new Date());
			order.setVmsg("消息" + i);
			order.setVtype(i);
			order.setVersion(1L);
		}
		for (int i = 0; i < 2; i++) {
			Msg order = new Msg();
			list.add(order);
			order.setSenddate(DateUtils.parseDate("2021-05-26"));
			order.setVmsg("消息" + i);
			order.setVtype(i);
			order.setVersion(1L);
		}
		int ret = msgMapper.insertBatch(list);
		return toAjax(ret);
	}
	
	/**
	 * http://127.0.0.1:9001/example1-api/test/list2
	 * <BR/>
	 * 分库分表查询
	 * @return
	 */
	@GetMapping("/list2")
	public TableDataInfo list2() {
		startPage();
		Msg msg = new Msg();
		msg.setSenddate(DateUtils.parseDate("2020-05-26"));
		List<Msg> list = msgMapper.selectList(msg);
		return getDataTable(list);
	}
	
	/**
	 * http://127.0.0.1:9001/example1-api/test/getInfo2
	 * 分库分表查询
	 * @return
	 */
	@GetMapping("/getInfo2")
	public AjaxResult getInfo2() {
		Msg info = msgMapper.selectById(4356827503814582273L);
		return AjaxResult.success(info);
	}
}

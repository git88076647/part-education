package com.czyl.project.system.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.czyl.common.utils.LockUtils;
import com.czyl.common.utils.SecurityUtils;
import com.czyl.common.web.domain.AjaxResult;
import com.czyl.framework.aspectj.lang.annotation.Lock4j;
import com.czyl.framework.aspectj.lang.annotation.Log;
import com.czyl.framework.aspectj.lang.enums.BusinessType;
import com.czyl.framework.web.controller.BaseController;
import com.czyl.framework.web.page.TableDataInfo;
import com.czyl.project.system.domain.SysNotice;
import com.czyl.project.system.service.ISysNoticeService;

/**
 * 公告 信息操作处理
 * 
 * @author tanghx
 */
@RestController
@RequestMapping("/system/notice")
public class SysNoticeController extends BaseController {
	@Autowired
	private ISysNoticeService noticeService;

	/**
	 * 获取通知公告列表
	 */
	@PreAuthorize("@ss.hasPermi('system:notice:list')")
	@GetMapping("/list")
	public TableDataInfo list(SysNotice notice) {
		startPage();
		List<SysNotice> list = noticeService.selectNoticeList(notice);
		return getDataTable(list);
	}

	/**
	 * 根据通知公告编号获取详细信息
	 */
	@PreAuthorize("@ss.hasPermi('system:notice:query')")
	@GetMapping(value = "/{noticeId}")
	public AjaxResult getInfo(@PathVariable Long noticeId) {
		return AjaxResult.success(noticeService.selectNoticeById(noticeId));
	}

	/**
	 * 新增通知公告
	 */
	@PreAuthorize("@ss.hasPermi('system:notice:add')")
	@Log(title = "通知公告", businessType = BusinessType.INSERT)
	@PostMapping
	@Lock4j(keys= {"#notice.noticeTitle"},autoPrefix = true)
	public AjaxResult add(@Validated @RequestBody SysNotice notice) {
		notice.setCreateBy(SecurityUtils.getUserId());
		return toAjax(noticeService.insertNotice(notice));
	}

	/**
	 * 修改通知公告
	 */
	@PreAuthorize("@ss.hasPermi('system:notice:edit')")
	@Log(title = "通知公告", businessType = BusinessType.UPDATE)
	@PutMapping
	public AjaxResult edit(@Validated @RequestBody SysNotice notice) {
		try {
			LockUtils.lock(notice.getNoticeId());
			notice.setUpdateBy(SecurityUtils.getUserId());
			return toAjax(noticeService.updateNotice(notice));
		} finally {
			LockUtils.releaseLock(notice.getNoticeId());
		}
	}

	/**
	 * 删除通知公告
	 */
	@PreAuthorize("@ss.hasPermi('system:notice:remove')")
	@Log(title = "通知公告", businessType = BusinessType.DELETE)
	@DeleteMapping("/{noticeId}")
	public AjaxResult remove(@PathVariable Long noticeId) {
		return toAjax(noticeService.deleteNoticeById(noticeId));
	}
}

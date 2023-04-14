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
import com.czyl.common.constant.UserConstants;
import com.czyl.common.utils.LockUtils;
import com.czyl.common.utils.SecurityUtils;
import com.czyl.common.utils.poi.ExcelUtil;
import com.czyl.common.web.domain.AjaxResult;
import com.czyl.framework.aspectj.lang.annotation.Lock4j;
import com.czyl.framework.aspectj.lang.annotation.Log;
import com.czyl.framework.aspectj.lang.enums.BusinessType;
import com.czyl.framework.web.controller.BaseController;
import com.czyl.framework.web.page.TableDataInfo;
import com.czyl.project.system.domain.SysPost;
import com.czyl.project.system.service.ISysPostService;

/**
 * 岗位信息操作处理
 * 
 * @author tanghx
 */
@RestController
@RequestMapping("/system/post")
public class SysPostController extends BaseController {
	@Autowired
	private ISysPostService postService;

	/**
	 * 获取岗位列表
	 */
	@PreAuthorize("@ss.hasPermi('system:post:list')")
	@GetMapping("/list")
	public TableDataInfo list(SysPost post) {
		startPage();
		List<SysPost> list = postService.selectPostList(post);
		return getDataTable(list);
	}

	@Log(title = "岗位管理", businessType = BusinessType.EXPORT)
	@PreAuthorize("@ss.hasPermi('system:config:export')")
	@GetMapping("/export")
	public AjaxResult export(SysPost post) {
		List<SysPost> list = postService.selectPostList(post);
		ExcelUtil<SysPost> util = new ExcelUtil<SysPost>(SysPost.class);
		return util.exportExcel(list, "岗位数据");
	}

	/**
	 * 根据岗位编号获取详细信息
	 */
	@PreAuthorize("@ss.hasPermi('system:post:query')")
	@GetMapping(value = "/{postId}")
	public AjaxResult getInfo(@PathVariable Long postId) {
		return AjaxResult.success(postService.selectPostById(postId));
	}

	/**
	 * 新增岗位
	 */
	@PreAuthorize("@ss.hasPermi('system:post:add')")
	@Log(title = "岗位管理", businessType = BusinessType.INSERT)
	@PostMapping
	@Lock4j(keys= {"#post.postCode"},autoPrefix = true)
	public AjaxResult add(@Validated @RequestBody SysPost post) {
		if (UserConstants.NOT_UNIQUE.equals(postService.checkPostNameUnique(post))) {
			return AjaxResult.error("新增岗位'" + post.getPostName() + "'失败，岗位名称已存在");
		} else if (UserConstants.NOT_UNIQUE.equals(postService.checkPostCodeUnique(post))) {
			return AjaxResult.error("新增岗位'" + post.getPostName() + "'失败，岗位编码已存在");
		}
		post.setCreateBy(SecurityUtils.getUserId());
		return toAjax(postService.insertPost(post));
	}

	/**
	 * 修改岗位
	 */
	@PreAuthorize("@ss.hasPermi('system:post:edit')")
	@Log(title = "岗位管理", businessType = BusinessType.UPDATE)
	@PutMapping
	public AjaxResult edit(@Validated @RequestBody SysPost post) {
		try {
			LockUtils.lock(post.getPostId());
			if (UserConstants.NOT_UNIQUE.equals(postService.checkPostNameUnique(post))) {
				return AjaxResult.error("修改岗位'" + post.getPostName() + "'失败，岗位名称已存在");
			} else if (UserConstants.NOT_UNIQUE.equals(postService.checkPostCodeUnique(post))) {
				return AjaxResult.error("修改岗位'" + post.getPostName() + "'失败，岗位编码已存在");
			}
			post.setUpdateBy(SecurityUtils.getUserId());
			return toAjax(postService.updatePost(post));
		} finally {
			LockUtils.releaseLock(post.getPostId());
		}
	}

	/**
	 * 删除岗位
	 */
	@PreAuthorize("@ss.hasPermi('system:post:remove')")
	@Log(title = "岗位管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{postIds}")
	public AjaxResult remove(@PathVariable Long[] postIds) {
		return toAjax(postService.deletePostByIds(postIds));
	}

	/**
	 * 获取岗位选择框列表
	 */
	@GetMapping("/optionselect")
	public AjaxResult optionselect() {
		List<SysPost> posts = postService.selectPostAll();
		return AjaxResult.success(posts);
	}
}

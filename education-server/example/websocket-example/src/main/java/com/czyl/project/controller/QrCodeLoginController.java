package com.czyl.project.controller;

import com.czyl.common.web.domain.AjaxResult;
import com.czyl.framework.web.controller.BaseController;
import com.czyl.project.entity.Command;
import com.czyl.project.util.QrLoginUtil;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 演示 扫码登录
 *
 * @author tanghx
 */
@RestController
@RequestMapping("/login")
public class QrCodeLoginController extends BaseController {

    /**
     * PC显示desUuid 二维码
     *
     * @return
     */
    @GetMapping("/getLoginId")
    public AjaxResult getLoginId() {
        return AjaxResult.success("操作成功", QrLoginUtil.getLoginId());
    }

    /**
     * 移动端查验登录二维码是否正确<br/>
     * 需要登录移动端才可调用此接口
     *
     * @return
     */
    @GetMapping("/valideLoginId")
    public AjaxResult valideLoginId(@RequestParam String uuid) {
        if (QrLoginUtil.valideLoginId(uuid)) {
            return AjaxResult.success("查验合法", true);
        }
        return AjaxResult.success("查验不合法", false);
    }

    /**
     * 移动端确认登录或取消登录 调用接口通知PC端扫码登录结果<br/>
     * 需要登录移动端才可调用此接口
     * @param uuid
     * @return
     */
    @PostMapping("/confirmLogin")
    public AjaxResult confirmLogin(@RequestParam String uuid, @RequestBody Map data) {
        logger.info("移动端发送扫码登录结果id:{},业务数据:{}",uuid,data);
        Command command = new Command();
        command.setAction("loginResult");
        command.setData(data);
        QrLoginUtil.sendLoginClientMessage(uuid,command);
        return AjaxResult.success();
    }

}

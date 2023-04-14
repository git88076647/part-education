package com.czyl.project.util;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import com.alibaba.fastjson.JSON;
import com.czyl.project.entity.Command;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.Session;
import java.nio.charset.StandardCharsets;

/**
 * @author tanghx
 * @version 1.0
 * @date 2022/3/30 11:32
 */
@Slf4j
public class QrLoginUtil {

    //    private static Map<String, Session> loginClients = new ConcurrentHashMap<>();
    private static TimedCache<String, Session> loginClients = CacheUtil.newTimedCache(5000 * 60);

    static {
        loginClients.schedulePrune(5000);
    }

    final static String AES_PASSWORD = "sezWnPD830iB607G";
    final static String LOGIN_PREFIX = "LOGIN:";

    synchronized public static void setLoginSession(String uuid, Session session) {
        loginClients.put(uuid, session);
    }

    public static Session getLoginSession(String uuid) {
        return loginClients.get(uuid, false);
    }

    public static boolean sendLoginClientMessage(String id, Command command) {
        Session session = QrLoginUtil.getLoginSession(id);
        if (session != null) {
            String message = JSON.toJSONString(command);
            try {
                session.getAsyncRemote().sendText(message);
                return true;
            } catch (Exception e) {
                log.error("推送消息到PC异常", e);
            }
            log.info("给登录id:{},发送消息:{}", id, message);
        } else {
            log.info("未找到登录id:{}", id);
        }
        return false;
    }


    public static String getLoginId() {
        String uuid = LOGIN_PREFIX + IdUtil.fastSimpleUUID();
        AES aes = SecureUtil.aes(AES_PASSWORD.getBytes(StandardCharsets.UTF_8));
        try {
            String encryptHex = aes.encryptHex(uuid);
            return encryptHex;
        } catch (Exception e) {
            throw new RuntimeException("密钥格式错误");
        }
    }

    public static boolean valideLoginId(String uuid) {
        AES aes = SecureUtil.aes(AES_PASSWORD.getBytes(StandardCharsets.UTF_8));
        try {
            String desUuid = aes.decryptStr(uuid, CharsetUtil.CHARSET_UTF_8);
            if (StrUtil.startWith(desUuid, LOGIN_PREFIX)) {
                return true;
            }
        } catch (Exception e) {
            log.info("校验LoginId失败,格式错误");
        }
        return false;
    }

    public static void main(String[] args) {
        String uuid = getLoginId();
        valideLoginId(uuid);
        log.info("校验完成{}", uuid);
    }

}

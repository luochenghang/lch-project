package com.lch.controller;

import com.lch.component.annotation.auth.AuthIgnore;
import com.lch.component.wechat.wxprogram.WeChatUtils;
import com.lch.service.lamp.wechat.LampCustomerReplyService;
import com.lch.utils.ConfigUtils;
import com.lch.utils.Decript;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/lampApp/v1")
public class CustomerController {

    @Autowired
    private LampCustomerReplyService lampCustomerReplyService;

    // 获取客服处配置的token值，同时和配置文件的对应上
    private static String Wechat_Token = ConfigUtils.getInstance().getConfigValue("official.accounts.token");

    @RequestMapping(value = "/lampReply", method = RequestMethod.GET)
    @AuthIgnore(login = false)
    public void check(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 微信加密签名
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");

        // 排序
        String sortString = WeChatUtils.sort(Wechat_Token, timestamp, nonce);
        // 加密
        String mytoken = Decript.SHA1(sortString);
        // 校验签名
        if (mytoken != null && mytoken != "" && mytoken.equals(signature)) {
            response.getWriter().println(echostr); // 如果检验成功输出echostr，微信服务器接收到此输出，才会确认检验完成。
        }
    }

    /**
     * 客服消息回复
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/lampReply", method = RequestMethod.POST)
    @AuthIgnore(login = false)
    public void push(HttpServletRequest request, HttpServletResponse response) throws Exception {
        lampCustomerReplyService.replyMessage(request, response);

    }
}

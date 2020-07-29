 package com.lch.service.lamp.wechat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

 public interface LampCustomerReplyService {

      public String replyMessage(HttpServletRequest request, HttpServletResponse response) throws Exception;
 }

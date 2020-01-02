 package com.lch.service.subpervise;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CustomerReplyService {

	 public String replyMessage(HttpServletRequest request, HttpServletResponse response) throws Exception;
}

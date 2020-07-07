package com.lch.controller;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.lch.common.config.UserUtils;
import com.lch.entity.common.authority.SysUser;
import com.lch.entity.lamp.SysLog;
import com.lch.service.common.SysUserService;
import com.lch.service.lamp.SysLogService;

/**
 * Created by lmb on 2018/9/5.
 */
@Aspect
@Component
public class AspectLogger {

    @Autowired
	private SysLogService sysLogService;
    
    @Autowired
	private SysUserService sysUserService;
//    /**
//     * 定义切入点，切入点为com.example.aop下的所有函数
//     */
//    @Pointcut("execution(public * com.lch.controller..*.*(..))")
//    public void webLog(){}

    
    @AfterReturning(value = "execution (public * com.lch.controller.*.add*(..))", returning = "returnObj")
    public void addTransaction(JoinPoint joinPoint, Object returnObj) throws Throwable {
    	ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Long uid = UserUtils.getCurrentUserId();
        SysUser user = sysUserService.getSysUser(uid);
        if(user != null) {
        	String accountName = user.getUserName();
            String operation = "添加操作";
        	//此方法返回的是一个数组，数组中包括request以及ActionCofig等类对象
            String argNames = JSON.toJSONString(joinPoint.getArgs());
            //获取方法名
            String methodName =joinPoint.getSignature().getName();
            String ip = getRemoteHost(request);
            
            String returning = returnObj == null ? "" : JSON.toJSONString(returnObj);
            SysLog sysLog = new SysLog(accountName, operation, methodName, argNames, ip, returning);
            sysLogService.addSysLog(sysLog);
        }
        
    }
    
    @AfterReturning(value = "execution (public * com.lch.controller.*.reg*(..))", returning = "returnObj")
    public void regTransaction(JoinPoint joinPoint, Object returnObj) throws Throwable {
    	ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Long uid = UserUtils.getCurrentUserId();
        SysUser user = sysUserService.getSysUser(uid);
        if(user != null) {
        	String accountName = user.getUserName();
            String operation = "注册操作";
        	//此方法返回的是一个数组，数组中包括request以及ActionCofig等类对象
            String argNames = JSON.toJSONString(joinPoint.getArgs());
            //获取方法名
            String methodName =joinPoint.getSignature().getName();
            String ip = getRemoteHost(request);
            
            String returning = returnObj == null ? "" : JSON.toJSONString(returnObj);
            SysLog sysLog = new SysLog(accountName, operation, methodName, argNames, ip, returning);
            sysLogService.addSysLog(sysLog);
        }
        
    }
    
    @AfterReturning(value = "execution (public * com.lch.controller.*.del*(..))", returning = "returnObj")
    public void delTransaction(JoinPoint joinPoint, Object returnObj) throws Throwable {
    	ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Long uid = UserUtils.getCurrentUserId();
        SysUser user = sysUserService.getSysUser(uid);
        if(user != null) {
        	String accountName = user.getUserName();
            String operation = "删除操作";
        	//此方法返回的是一个数组，数组中包括request以及ActionCofig等类对象
            String argNames = JSON.toJSONString(joinPoint.getArgs());
            //获取方法名
            String methodName =joinPoint.getSignature().getName();
            String ip = getRemoteHost(request);
            
            String returning = returnObj == null ? "" : JSON.toJSONString(returnObj);
            SysLog sysLog = new SysLog(accountName, operation, methodName, argNames, ip, returning);
            sysLogService.addSysLog(sysLog);
        }
        
    }
    //public * com.lch.controller..*.*(..))
    @AfterReturning(value = "execution (public * com.lch.controller.*.upd*(..))", returning = "returnObj")
    public void updTransaction(JoinPoint joinPoint, Object returnObj) throws Throwable {
    	ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Long uid = UserUtils.getCurrentUserId();
        SysUser user = sysUserService.getSysUser(uid);
        if(user != null) {
        	String accountName = user.getUserName();
            String operation = "更新操作";
        	//此方法返回的是一个数组，数组中包括request以及ActionCofig等类对象
            String argNames = JSON.toJSONString(joinPoint.getArgs());
            //获取方法名
            String methodName =joinPoint.getSignature().getName();
            String ip = getRemoteHost(request);
            
            String returning = returnObj == null ? "" : JSON.toJSONString(returnObj);
            SysLog sysLog = new SysLog(accountName, operation, methodName, argNames, ip, returning);
            sysLogService.addSysLog(sysLog);
        }
        
    }
    
    @AfterThrowing(pointcut = "execution (public * com.lch.controller.*.upd*(..))", throwing = "ex")
    public void printException(Exception ex) {

        System.out.println("执行update方法时发生错误" + ex.getMessage());

    }
    
    /**
     * 获取目标主机的ip
     * @param request
     * @return
     */
    @SuppressWarnings("unused")
	private String getRemoteHost(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

    

}
package com.lch.entity.lamp;

import java.util.Date;

import com.lch.common.base.DataEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class SysLog extends DataEntity<SysLog>{
	

	private static final long serialVersionUID = 1L;
	 
		private String accountName; //用户名
	 
	    private String operation; //操作
	 
	    private String methodName;//方法名
	 
	    private String argNames;//输入参数
	 
	    private String ip; //ip地址
	    
	    private String returning;//输出
	 
	    private Date createDate; //操作时间

	    
	    
		public SysLog(String accountName, String operation, String methodName, String argNames, String ip,
				String returning) {
			super();
			this.accountName = accountName;
			this.operation = operation;
			this.methodName = methodName;
			this.argNames = argNames;
			this.ip = ip;
			this.returning = returning;
		}



		public SysLog() {
			super();
		}
}

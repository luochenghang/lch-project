package com.lch.common.base;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lch.common.config.UserUtils;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 基础entity类，封装了实体类的公共属性
 *
 * @ClassName: BaseEntity
 *  @date 2019年12月10日 下午3:23:56
 * @param <T>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseEntity<T> extends DataEntity<T> {

	private static final long serialVersionUID = 1L;

	@JsonIgnore
	private Long createUserId;// 创建人ID

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createDate;// 创建时间

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateDate;// 更新时间

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	//@ApiModelProperty(value = "开始时间", required = false)
	private Date beginDate;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	//@ApiModelProperty(value = "结束时间", required = false)
	private Date endDate;

	@JsonIgnore
	private String sql;//自定义SQL对象

	private String queryStr;//自定义查询对象

	@JsonIgnore
	private boolean isNewRecord = false;// 是否是新记录（默认：false）

	@JsonIgnore
	@Length(min = 1, max = 1)
	private Integer delFlag;// 删除标记(0 正常 1删除)

	//@ApiModelProperty(value = "页码", required = false)
	private Integer pageNum;

	//@ApiModelProperty(value = "页大小", required = false)
	private Integer pageSize;

	public Integer getPageNum() {
		return pageNum == null ? 1 : pageNum;
	}

	public Integer getPageSize() {
		return pageSize == null ? 15 : pageSize;
	}

	/**
	 * 是否是新记录（默认：false）,设置为true后强制执行插入语句，否则为更新
	 * @return
	 */
	public boolean getIsNewRecord() {
        return isNewRecord || getId() == null;
    }

	public BaseEntity() {
		super();
		this.delFlag = 0;
		this.createUserId = UserUtils.getCurrentUserId();
	}

}

package com.lch.entity.lamp;

import com.lch.common.base.DataEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class Address extends DataEntity<Address> {

    private static final long serialVersionUID = 1L;

    private  Long userId; //用户id

    private String linkMan; //联系人

    private String mobile; //手机号

    private String address; //地址

}

package com.lch.service.lamp.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lch.service.common.impl.SysUserServiceImpl;
import com.lch.service.lamp.SysUserRoleService;

@Service
@Transactional(readOnly = true)
public class SysUserRoleServiceImpl extends SysUserServiceImpl implements SysUserRoleService {

}

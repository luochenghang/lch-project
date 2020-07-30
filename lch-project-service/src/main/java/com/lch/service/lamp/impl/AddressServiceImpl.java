package com.lch.service.lamp.impl;

import com.lch.common.base.DataService;
import com.lch.common.config.UserSessionUtils;
import com.lch.common.exceptions.ServiceException;
import com.lch.entity.lamp.Address;
import com.lch.repo.lamp.AddressRepo;
import com.lch.service.lamp.AddressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = false)
public class AddressServiceImpl extends DataService<AddressRepo, Address> implements AddressService {


    @Override
    public List<Address> getAddressList() {

        return repo.getAddressList(UserSessionUtils.getCurrentUserId());
    }

    @Override
    public Address getAddressById(Long id) {
        return repo.get(id);
    }

    @Override
    public Integer delAddress(Long id) throws ServiceException {
        Long userId = UserSessionUtils.getCurrentUserId();
        if (userId == null){
            doThrow("没有登录");
        }
        return repo.delAddress(id, userId);
    }

    @Override
    public Integer updAddress(Address address) throws ServiceException {
        Long id = address.getId();
        address.setUserId(UserSessionUtils.getCurrentUserId());
        if (id != null){
            return repo.update(address);
        }else{
            return repo.insert(address);
        }
    }
}

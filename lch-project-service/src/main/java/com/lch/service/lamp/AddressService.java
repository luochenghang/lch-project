package com.lch.service.lamp;

import com.lch.common.exceptions.ServiceException;
import com.lch.entity.lamp.Address;

import java.util.List;

public interface AddressService {

    /**
     * 查询全部的
     *
     * @return
     */
    List<Address> getAddressList();

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    Address getAddressById(Long id);

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws ServiceException
     */
    Integer delAddress(Long id) throws ServiceException;

    /**
     * 编辑或者修改
     *
     * @param address
     * @return
     * @throws ServiceException
     */
    Integer updAddress(Address address) throws ServiceException;

}
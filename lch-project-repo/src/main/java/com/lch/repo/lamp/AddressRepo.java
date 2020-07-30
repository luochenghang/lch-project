package com.lch.repo.lamp;

import com.lch.common.base.BaseRepo;
import com.lch.entity.lamp.Address;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AddressRepo extends BaseRepo<Address> {

    List<Address> getAddressList(Long userId);

    Integer delAddress(Long id, Long userId);
}

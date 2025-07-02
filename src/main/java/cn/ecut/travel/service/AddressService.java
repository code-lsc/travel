package cn.ecut.travel.service;

import cn.ecut.travel.entity.AddressEntity;

import java.util.List;

public interface AddressService {

    List<AddressEntity> selectAllAddress();

    List<String> selectHotCity();

    void updateHotCity(List<String> hotCity);

    AddressEntity selectAddress(AddressEntity address);
}

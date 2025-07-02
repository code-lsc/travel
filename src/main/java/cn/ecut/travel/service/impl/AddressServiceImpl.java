package cn.ecut.travel.service.impl;

import cn.ecut.travel.common.constants.CommonConstants;
import cn.ecut.travel.dao.AddressDao;
import cn.ecut.travel.entity.AddressEntity;
import cn.ecut.travel.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressDao addressDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<AddressEntity> selectAllAddress() {
        return addressDao.selectAllAddress();
    }

    @Override
    public List<String> selectHotCity() {
        Object hotCity = redisTemplate.opsForValue().get(CommonConstants.HOTCITY_KEY);
        List hotCitys = (List) hotCity;
        return hotCitys;
    }

    @Override
    public void updateHotCity(List<String> hotCity) {
        redisTemplate.opsForValue().set(CommonConstants.HOTCITY_KEY,hotCity);
    }

    @Override
    public AddressEntity selectAddress(AddressEntity address) {
        return addressDao.selectAddress(address);
    }
}

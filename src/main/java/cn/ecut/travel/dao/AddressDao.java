package cn.ecut.travel.dao;

import cn.ecut.travel.entity.AddressEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AddressDao {
    /**
     * 查询所有地址
     * @return
     */
    List<AddressEntity> selectAllAddress();

    AddressEntity selectAddress(AddressEntity address);
}

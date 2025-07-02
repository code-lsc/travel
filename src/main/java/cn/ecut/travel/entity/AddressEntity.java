package cn.ecut.travel.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class AddressEntity implements Serializable {
    private Integer id;
    private String province;
    private String city;
    private String country;
}

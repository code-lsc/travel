package cn.ecut.travel.controller;

import cn.ecut.travel.entity.AddressEntity;
import cn.ecut.travel.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping("/all")
    public List<AddressEntity> allAddress(){
        return addressService.selectAllAddress();
    }
}

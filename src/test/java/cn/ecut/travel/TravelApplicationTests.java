package cn.ecut.travel;

import cn.ecut.travel.service.AddressService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class TravelApplicationTests {

    @Autowired
    private AddressService addressService;
    @Test
    void contextLoads() {
        List list= new ArrayList();
        list.add("上饶");
        list.add("汕头");
        list.add("北京");
        list.add("厦门");
        list.add("长沙");
        list.add("南昌");
        list.add("哈尔滨");
        addressService.updateHotCity(list);
    }

}

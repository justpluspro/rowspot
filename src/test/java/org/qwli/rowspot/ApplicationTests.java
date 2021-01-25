package org.qwli.rowspot;

import org.junit.jupiter.api.Test;
import org.qwli.rowspot.service.NewUser;
import org.qwli.rowspot.service.UserService;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class ApplicationTests {


    @Resource
    private UserService userService;

    @Test
    void contextLoads() throws Exception {

    }

}

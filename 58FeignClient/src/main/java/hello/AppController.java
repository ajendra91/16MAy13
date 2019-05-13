package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AppController {

    @Autowired
    private UserClient client;

    @RequestMapping(value = "/findAllUser")
    public String getAllUser() {
        return client.getUsers();
    }

    @RequestMapping(value = "/findId/{name}")
    public String findId(@PathVariable String name) {
        return client.getId(name);
    }


}

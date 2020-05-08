package xyz.cglzwz.chatroom.UserManager;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//just for test, it is a application monitor
@Configuration
@Endpoint(id = "user")
public class UserEndpoint {
    @ReadOperation
    public List<Map<String, Object>> health(){
        List<Map<String, Object>> list=new ArrayList<>();
        Map<String, Object> map=new HashMap<>();
        map.put("userID",100014);
        map.put("username", "jianjun");
        list.add(map);
        return list;
    }
}

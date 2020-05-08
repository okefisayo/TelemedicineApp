package xyz.cglzwz.chatroom.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import xyz.cglzwz.chatroom.dao.InfoMapper;
import xyz.cglzwz.chatroom.domain.info;
import xyz.cglzwz.chatroom.entity.R;
import xyz.cglzwz.chatroom.entity.ResponseResult;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
public class ProfileController {
    @Resource
    private InfoMapper infoMapper;

    @RequestMapping("/showprofile")
    @ResponseBody
    public ResponseResult ShowProfile(@RequestBody info information, HttpSession session){
        info resInfo=infoMapper.getInfoByUsername(String.valueOf(session.getAttribute("username")));
        //System.err.println(String.valueOf(session.getAttribute("username")));
        try{
            if(resInfo!=null){
                return R.OK("ok", resInfo);
            }else{
                System.err.println("Fail");
                return R.FAIL("Fail");
            }
        }catch (Exception e){
            e.printStackTrace();
            return R.exception(e);
        }
    }
    @RequestMapping("/updateprofile")
    @ResponseBody
    public ResponseResult updateProfile(@RequestBody info information, HttpSession session){
        String username=String.valueOf(session.getAttribute("username"));
        information.setUsername(username);
        //System.err.println(information);
        try{
            boolean updateInfo=infoMapper.UpdateInfo(information);
            return R.OK("OK");
        }catch (Exception e){
            e.printStackTrace();
            return R.exception(e);
        }
    }
}

package xyz.cglzwz.chatroom.controller;

import org.springframework.web.bind.annotation.*;
import xyz.cglzwz.chatroom.dao.FriendMapper;
import xyz.cglzwz.chatroom.dao.InfoMapper;
import xyz.cglzwz.chatroom.dao.RequestMapper;
import xyz.cglzwz.chatroom.domain.friendList;
import xyz.cglzwz.chatroom.domain.info;
import xyz.cglzwz.chatroom.domain.requests;
import xyz.cglzwz.chatroom.entity.R;
import xyz.cglzwz.chatroom.entity.ResponseResult;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
public class FriendsController {
    @Resource
    private FriendMapper friendMapper;
    @Resource
    private RequestMapper requestMapper;
    @Resource
    private InfoMapper infoMapper;
    @RequestMapping("/showPatients")
    @ResponseBody
    public ResponseResult showPatients(@RequestBody String identity, HttpSession session){
        String username=String.valueOf(session.getAttribute("username"));
        info iden=infoMapper.selectIdentityByUsername(username);
        if(iden.getIdentity()==2){
            return null;
        }
        List<info> res=infoMapper.selectPatientByIdentity(2);
        if(res!=null){
            return R.OK("ok", res);
        }else {
            return R.FAIL("No patients found");
        }
    }

    @RequestMapping("/showDoctors")
    @ResponseBody
    public ResponseResult showDoctor(@RequestBody String identity){
        List<info> res=infoMapper.selectDoctorByIdentity(1);
        if(res!=null){
            return R.OK("ok", res);
        }else {
            return R.FAIL("No patients found");
        }
    }

    @RequestMapping("/searchUser")
    @ResponseBody
    public ResponseResult searchUser(@RequestBody info nickname){
        //System.err.println("___________test______________nickname: "+nickname.getNickname());
        List<info> res;
        if(nickname.getNickname().equals("") || nickname.getNickname()==null){
            //System.out.println("error loop1");
            return R.FAIL("user does not exist.");
        }else{
            res=infoMapper.selectNameAndDescriptionByNickname(nickname.getNickname());
            //System.err.println("error loop2 res=infoMapper.selectNameAndDescriptionByNickname(nickname.getNickname()");
            if(res==null){
                R.FAIL("F");
                //System.out.println("res==null");
            }
            return R.OK("Success", res);
        }
    }
    //table: username1 - username2
    @RequestMapping("/sendRequest/{receiver}")
    @ResponseBody
        public ResponseResult sendFriendRequest(HttpSession session, @PathVariable String receiver, HttpServletResponse response){
        String senderName=String.valueOf(session.getAttribute("username"));
        List<friendList> list=friendMapper.showFriends(senderName);
        for(friendList l:list){
            if(l.getSender().equals(receiver) || l.getReceiver().equals(receiver)){
                return R.FAIL("You are already friends!");
            }
        }
        if(requestMapper.storeRequest(senderName, receiver)){
            try{
                response.sendRedirect("http://localhost:8090/friends");
            }catch (Exception e){
                e.printStackTrace();
            }
            //return R.OK("send friend request successfully");
        }else {
            //return R.FAIL("failed");
        }
        return R.OK("ok");
    }

    @RequestMapping("/showRequest")
    @ResponseBody
    public ResponseResult showRequest(HttpSession session){
        String username=String.valueOf(session.getAttribute("username"));
        List<requests> list=requestMapper.haveSent(username);
        List<info> nicknameList=new ArrayList<>();
        for (requests s: list){
            info a=infoMapper.selectNicknameAndUserNameByUsername(s.getReceiver());
            if(a!=null){
                nicknameList.add(a);
            }
        }
        if(!nicknameList.isEmpty()){
            return R.OK("ok", nicknameList);
        }else{
            return R.FAIL("Not found!");
        }
    }

    @RequestMapping("/undoRequest/{receiver}")
    @ResponseBody
    public ResponseResult undoRequest(HttpSession session, @PathVariable String receiver, HttpServletResponse response){
        String username=String.valueOf(session.getAttribute("username"));
        boolean res=requestMapper.deleteRequest(username, receiver);
        if(res==false){
            //redirect to error page 404
        }else{
            try{
                response.sendRedirect("http://localhost:8090/friends");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return R.OK("success");
    }
    @RequestMapping("/receiveRequest")
    @ResponseBody
    public ResponseResult receiveRequest(HttpSession session){
        String username=String.valueOf(session.getAttribute("username"));
        List<requests> received=requestMapper.haveReceived(username);
        if(received.isEmpty()){return R.FAIL("Failed");}
        List<info> res=new ArrayList<>();
        for(requests a: received){
            boolean success=res.add(infoMapper.selectNicknameAndUserNameByUsername(a.getSender()));
        }
        if(!res.isEmpty()){
            return R.OK("success", res);
        }else{
            return R.FAIL("Failed");
        }
    }

    @RequestMapping("/AcceptRequest/{acceptName}")
    @ResponseBody
    public ResponseResult AcceptRequest(HttpSession session, @PathVariable String acceptName, HttpServletResponse response){
        String username=String.valueOf(session.getAttribute("username"));
        boolean J=friendMapper.AcceptRequest(username, acceptName);
        if(J==false){
            //error page
        }else{
            try{
                response.sendRedirect("http://localhost:8090/friends");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        requestMapper.deleteRequest(acceptName, username);
        return R.OK("ok");
    }

    @RequestMapping("/showFriendList")
    @ResponseBody
    public ResponseResult showFriendList(HttpSession session){
        String username=String.valueOf(session.getAttribute("username"));
        List<friendList> list=friendMapper.showFriends(username);
        List<info> res=new ArrayList<>();
        for(friendList friend: list){
            if(friend.getSender().equals(username)){
                info a=infoMapper.selectNicknameAndUserNameByUsername(friend.getReceiver());
                res.add(a);
            }else {
                info a=infoMapper.selectNicknameAndUserNameByUsername(friend.getSender());
                res.add(a);
            }
        }
        if(res.isEmpty()){
            return R.FAIL("No friends");
        }else{
            return R.OK("success", res);
        }
    }
    @RequestMapping("/deleteFriend/{FriendUsername}")
    @ResponseBody
    public void deleteFriend(@PathVariable String FriendUsername, HttpSession session, HttpServletResponse response){
        String username=String.valueOf(session.getAttribute("username"));
        boolean J=friendMapper.deleteFriend(username, FriendUsername);
        if(J==false){
            //error page
        }else {
            try{
                response.sendRedirect("http://localhost:8090/friends");
            }catch (Exception e){
                e.printStackTrace();
                //error page
            }
        }
    }

}

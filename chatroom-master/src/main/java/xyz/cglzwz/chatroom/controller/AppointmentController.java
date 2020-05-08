package xyz.cglzwz.chatroom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import xyz.cglzwz.chatroom.dao.AppointmentMapper;
import xyz.cglzwz.chatroom.dao.AppointmentRequestMapper;
import xyz.cglzwz.chatroom.dao.InfoMapper;
import xyz.cglzwz.chatroom.domain.Appointment;
import xyz.cglzwz.chatroom.domain.AppointmentRequest;
import xyz.cglzwz.chatroom.entity.R;
import xyz.cglzwz.chatroom.entity.ResponseResult;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class AppointmentController {
    @Resource
    AppointmentRequestMapper appointmentRequestMapper;
    @Resource
    InfoMapper infoMapper;
    @Resource
    AppointmentMapper appointmentMapper;

    @RequestMapping("/appointmentRequest")
    @ResponseBody
    public boolean sendRequest(@RequestBody AppointmentRequest request, HttpSession session){
        String username=String.valueOf(session.getAttribute("username"));
        request.setSender(username);
        String receiverNickname=infoMapper.selectNicknameByUsername(request.getReceiver()).getNickname();
        if(receiverNickname!=null || !receiverNickname.equals("")){
            request.setNickname(receiverNickname);
        }else{
            return false;
        }
        boolean k=appointmentRequestMapper.sendRequest(request);
        return k;
    }

    @RequestMapping("/showAppointmentRequest")
    @ResponseBody
    public ResponseResult showRequest(HttpSession session){
        String username=String.valueOf(session.getAttribute("username"));
        List<AppointmentRequest> res=appointmentRequestMapper.selectAll(username);
        //System.err.println(res);
        for(AppointmentRequest tmp: res){
            String sender=tmp.getSender();
            tmp.setNickname(infoMapper.selectNicknameByUsername(sender).getNickname());
        }
        return R.OK("ok", res);
    }


    @RequestMapping("/Refuse/{id}")
    @ResponseBody
    public boolean Refuse(@PathVariable int id){
        //String username=String.valueOf(session.getAttribute("username"));
        boolean J=appointmentRequestMapper.deleteRequest(id);
        return J;
    }

    @RequestMapping("/Agree/{id}")
    @ResponseBody
    public boolean Agree(HttpSession session, @PathVariable int id){
        String username=String.valueOf(session.getAttribute("username"));
        AppointmentRequest tmp=appointmentRequestMapper.selectOneById(id);
        Appointment appointment=new Appointment();
        appointment.setDate(tmp.getDate());
        appointment.setDescription(tmp.getDescription());
        appointment.setLocation(tmp.getLocation());
        appointment.setNickname(tmp.getNickname());
        appointment.setReceiver(tmp.getReceiver());
        appointment.setSender(tmp.getSender());
        appointment.setHour(tmp.getHour());
        if(tmp!=null) appointmentMapper.agreeRequest(appointment);
        appointmentRequestMapper.deleteRequest(id);
        return true;
//        return new RedirectView("http://localhost:8090/home");
    }
    @RequestMapping("/showAppointments")
    @ResponseBody
    public ResponseResult appointment(@RequestBody Appointment appointment, HttpSession session){
        String username=String.valueOf(session.getAttribute("username"));
        List<Appointment> res=appointmentMapper.selectAllByUsername(username);
//        System.out.println(res.size());
        String SessionNickname = "";
        if(res.size()>0)  SessionNickname=infoMapper.selectNicknameByUsername(res.get(0).getSender()).getNickname();
        for(Appointment a: res){
            a.setSender(infoMapper.selectNicknameByUsername(a.getReceiver()).getNickname());
            a.setReceiver(SessionNickname);
        }
        return R.OK("ok", res);
    }
}
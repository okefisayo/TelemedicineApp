package xyz.cglzwz.chatroom.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.cglzwz.chatroom.dao.FriendMapper;
import xyz.cglzwz.chatroom.dao.InfoMapper;
import xyz.cglzwz.chatroom.dao.ReportsMapper;
import xyz.cglzwz.chatroom.domain.Reports;
import xyz.cglzwz.chatroom.domain.friendList;
import xyz.cglzwz.chatroom.domain.info;
import xyz.cglzwz.chatroom.entity.R;
import xyz.cglzwz.chatroom.entity.ResponseResult;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ReportsController {
    @Resource
    InfoMapper infoMapper;
    @Resource
    FriendMapper friendMapper;
    @Resource
    ReportsMapper reportsMapper;

    @RequestMapping("/DownloadReport/{id}")
    @ResponseBody
    public void PatientReceive(HttpSession session, HttpServletResponse response, @PathVariable int id){
        String username=String.valueOf(session.getAttribute("username"));
        Reports res=reportsMapper.receiveById(id);
        if(res==null){return;}
        File file=new File(res.getPath());
        if(file.exists()){
            response.addHeader("Content-Disposition", "attachment;fileName=" + res.getFilename());
            byte[] buffer=new byte[1024];
            FileInputStream fis=null;
            BufferedInputStream bis=null;
            try{
                fis=new FileInputStream(file);
                bis=new BufferedInputStream(fis);
                OutputStream outputStream=response.getOutputStream();
                int i=bis.read(buffer);
                while (i!=-1){
                    outputStream.write(buffer, 0 ,i);
                    i=bis.read(buffer);
                }
            }catch (IOException e){
                e.printStackTrace();
            }finally {
                if(bis!=null){
                    try{
                        bis.close();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
                if (fis!=null){
                    try{
                        fis.close();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @RequestMapping("/getReports")
    @ResponseBody
    public ResponseResult getReports(HttpSession session){
        String username=String.valueOf(session.getAttribute("username"));
        List<Reports> res=reportsMapper.selectAllByUsername(username);
        if(!res.isEmpty()){
            return R.OK("ok", res);
        }else {
            return R.FAIL("There is no reports for you right now");
        }
    }

    @RequestMapping("/YourPatients")
    @ResponseBody
    public ResponseResult YourPatients(HttpSession session){
        String username=String.valueOf(session.getAttribute("username"));
        if(infoMapper.selectIdentityByUsername(username).getIdentity()==2){
            return R.OK("ok");
        }

        List<friendList> friends=friendMapper.showFriends(username);
        List<info> res=new ArrayList<>();
        for(friendList f:friends){
            if(f.getSender().equals(username)){
                info tmp=infoMapper.selectNicknameAndUserNameByUsername(f.getReceiver());
                res.add(tmp);
            }else{
                info tmp=infoMapper.selectNicknameAndUserNameByUsername(f.getSender());
                res.add(tmp);
            }
        }
        return R.OK("ok", res);
    }

    @RequestMapping("/DoctorSend/{receiver}")
    @ResponseBody
    public String DoctorSend(@RequestParam(value = "uploadFiles", required = true) MultipartFile[] uploadFiles, HttpSession session, @PathVariable String receiver){
        String res="";
        if(uploadFiles==null){return "UPLOAD FAILED!";}
        //dir creating, dir name is username
        String username=String.valueOf(session.getAttribute("username"));
        String dir="C:/home/jg/fileServer/reports/"+username+"/"+receiver;
        new File(dir).mkdirs();

        for(int i=0; i<uploadFiles.length; i++){
            MultipartFile file=uploadFiles[i];
            String originalFilename=file.getOriginalFilename();
            String path=dir+"/"+originalFilename;
            File dest=new File(path);
            try{
                file.transferTo(dest);
                Reports report=new Reports();
                report.setDoctor(username);
                report.setFilename(originalFilename);
                report.setPath(path);
                report.setReceiver(receiver);
                reportsMapper.Upload(report);
                if(i==uploadFiles.length-1){
                    return "Upload Successfully";
                }
            }catch (IOException e){
                e.printStackTrace();
                return "Upload Failed";
            }
        }
        return res;
    }
}

package xyz.cglzwz.chatroom.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;


@RestController
public class UploadController {
    @PostMapping("/uploadVideos")
    @ResponseBody
    public String upload(@RequestParam(value = "uploadFiles", required = true) MultipartFile[] uploadFiles, HttpSession session){
        String res="";
        if(uploadFiles==null){return "UPLOAD FAILED!";}
        //dir creating, dir name is username
        String username=String.valueOf(session.getAttribute("username"));
        String dir="C:/home/jg/fileServer/videos/"+username;
        new File(dir).mkdirs();
        //
        for(int i=0; i<uploadFiles.length; i++){
            MultipartFile file=uploadFiles[i];
            String originalFilename=file.getOriginalFilename();
            //System.err.println(originalFilename);
            String path=dir+"/"+originalFilename;
            File dest=new File(path);
            try{
                file.transferTo(dest);
                if(i==uploadFiles.length-1){return "Upload Successfully";}
            }catch (IOException e){
                e.printStackTrace();
                return "Upload Failed";
            }
        }
        return res;
    }
}

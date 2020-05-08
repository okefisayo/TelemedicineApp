package xyz.cglzwz.chatroom.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class DownloadController {
    @RequestMapping("/fileList")
    @ResponseBody
    public List<String> fileList(HttpSession session){
        String username=String.valueOf(session.getAttribute("username"));
        String dir="C:/home/jg/fileServer/videos/"+username;
        boolean k=new File(dir).mkdirs();
        List<String> res=new ArrayList<>();
        File file=new File(dir);
        File[] fs=file.listFiles();
        for(File f: fs){
            res.add(f.getName());
        }
        return res;
    }
    @RequestMapping("/download/{name}")
    @ResponseBody
    public void download(@PathVariable String name, HttpServletResponse response, HttpSession session){
        String username=String.valueOf(session.getAttribute("username"));
        String path="C:/home/jg/fileServer/videos/"+username+"/"+name;
        File file=new File(path);
        //System.out.println("test: "+name);
        //file.exists()
        if(file.exists()){
            response.addHeader("Content-Disposition", "attachment;fileName=" + name);
            byte[] buffer=new byte[1024];
            FileInputStream fis=null;
            BufferedInputStream bis=null;
            try{
                fis=new FileInputStream(file);
                bis=new BufferedInputStream(fis);
                OutputStream outputStream=response.getOutputStream();
                int i=bis.read(buffer);
                while (i!=-1){
                    outputStream.write(buffer, 0, i);
                    i=bis.read(buffer);
                }
            }catch (Exception e){
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

    @RequestMapping("delete/{name}")
    @ResponseBody
    public void delete(@PathVariable String name, HttpSession session, HttpServletResponse response){
        String username=String.valueOf(session.getAttribute("username"));
        String path="C:/home/jg/fileServer/videos/"+username+"/"+name;
        File file=new File(path);
        if(file.exists()){
            if(file.delete()){
                try{
                    response.sendRedirect("http://localhost:8090/videos");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else{
                //error page 500
            }
        }
        //error page 404
    }

}

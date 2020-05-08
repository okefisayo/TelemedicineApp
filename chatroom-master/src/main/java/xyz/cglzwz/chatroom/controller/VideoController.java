package xyz.cglzwz.chatroom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import xyz.cglzwz.chatroom.handler.VideoResourceHttpRequestHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@Controller
public class VideoController {
    @Autowired
    private VideoResourceHttpRequestHandler handler;
    private final static File MP4_FILE = new File("C:/home/jg/fileServer/VideosTest/example.mp4");
    @GetMapping("/byterange")
    public void byterange(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute(handler.getAttrFile(), MP4_FILE);
        handler.handleRequest(request, response);
    }
}

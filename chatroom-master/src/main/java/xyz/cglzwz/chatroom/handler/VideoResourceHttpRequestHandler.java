package xyz.cglzwz.chatroom.handler;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@Component
public class VideoResourceHttpRequestHandler extends ResourceHttpRequestHandler {
    final static String ATTR_FILE = VideoResourceHttpRequestHandler.class.getName() + ".file";
    @Override
    protected Resource getResource(HttpServletRequest request) throws IOException {
        final File file = (File) request.getAttribute(ATTR_FILE);
        return new FileSystemResource(file);
    }
    public String getAttrFile(){
        return ATTR_FILE;
    }
}

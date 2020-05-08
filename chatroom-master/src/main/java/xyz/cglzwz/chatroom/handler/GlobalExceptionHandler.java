package xyz.cglzwz.chatroom.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
//exception handler for all
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler()
    @ResponseBody
    String handleException(Exception e){
        return "Exception Deal! " + e.getMessage();
    }
}

package xyz.cglzwz.chatroom.entity;

import java.io.Serializable;

public class R implements Serializable {
    public static final Integer OK = 1;
    public static final Integer FAIL = 0;


    /**
     * 成功的结果渲染
     * @param msg
     * @param data
     * @return
     */
    public static ResponseResult OK(String msg, Object data){
        return render(OK, msg, data);
    }
    public static ResponseResult OK(String msg){
        return render(OK, msg, null);
    }
    public static ResponseResult OK(Object data){
        return render(OK, "", data);
    }

    /**
     * 失败结果渲染
     * @param msg
     * @param data
     * @return
     */
    public static ResponseResult FAIL(String msg, Object data){
        return render(FAIL, msg, data);
    }
    public static ResponseResult FAIL(String msg){
        return render(FAIL, msg, null);
    }
    public static ResponseResult FAIL(Object data){
        return render(FAIL, "", data);
    }

    /**
     * 异常结果渲染
     * @param e
     * @return
     */
    public static ResponseResult exception(Exception e){
        return render(FAIL, e.getMessage(), null);
    }

    /**
     * 其他结果渲染
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public static ResponseResult render(Integer code, String msg, Object data){
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(code);
        responseResult.setMsg(msg);
        responseResult.setData(data);
        return responseResult;
    }
}

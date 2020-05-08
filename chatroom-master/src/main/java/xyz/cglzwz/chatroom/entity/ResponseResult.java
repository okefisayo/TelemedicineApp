package xyz.cglzwz.chatroom.entity;

public class ResponseResult<T> {
    private Integer code;
    private String msg;
    private T data;

    public Integer getCode() {
        return code;
    }

    public T getData() {
        return data;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}

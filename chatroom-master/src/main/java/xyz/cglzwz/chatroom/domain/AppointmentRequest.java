package xyz.cglzwz.chatroom.domain;

import lombok.Data;

@Data
public class AppointmentRequest {
    private String sender;
    private String receiver;
    private String nickname;
    private String description;
    private String date;
    private String location;
    private String hour;
    private String id;
}

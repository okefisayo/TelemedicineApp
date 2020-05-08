package xyz.cglzwz.chatroom.domain;

import lombok.Data;

@Data
public class Reports {
    private int id;
    private String doctor;
    private String receiver;
    private String path;
    private String filename;
}

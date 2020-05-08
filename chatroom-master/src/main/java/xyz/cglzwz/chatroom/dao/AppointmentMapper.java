package xyz.cglzwz.chatroom.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.cglzwz.chatroom.domain.Appointment;

import java.util.List;

@Mapper
public interface AppointmentMapper {
    boolean agreeRequest(@Param("param")Appointment appointment);
    List<Appointment> selectAllByUsername(@Param("param") String username);
}

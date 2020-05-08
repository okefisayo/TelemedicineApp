package xyz.cglzwz.chatroom.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.cglzwz.chatroom.domain.AppointmentRequest;

import java.util.List;

@Mapper
public interface AppointmentRequestMapper {
    boolean sendRequest(@Param("param")AppointmentRequest request);
    boolean deleteRequest(@Param("id") int id);
    List<AppointmentRequest> selectAll(@Param("receiver")String receiver);
    List<AppointmentRequest> selectAllBySenderAndReceiver(@Param("sender") String sender, @Param("receiver") String receiver);
    AppointmentRequest selectOneById(@Param("id") int id);
}

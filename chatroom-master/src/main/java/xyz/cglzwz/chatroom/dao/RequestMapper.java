package xyz.cglzwz.chatroom.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.cglzwz.chatroom.domain.requests;

import java.util.List;

@Mapper
public interface RequestMapper {
    boolean storeRequest(@Param("sender")String sender, @Param("receiver")String receiver);
    boolean deleteRequest(@Param("sender")String sender, @Param("receiver")String receiver);
    List<requests> haveSent(@Param("sender")String sender);
    List<requests> haveReceived(@Param("receiver")String receiver);

}

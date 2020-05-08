package xyz.cglzwz.chatroom.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.cglzwz.chatroom.domain.friendList;

import java.util.List;

@Mapper
public interface FriendMapper {
    boolean AcceptRequest(@Param("sender") String sender, @Param("receiver")String receiver);
    List<friendList> showFriends(@Param("username") String username);
    boolean deleteFriend(@Param("sender") String sender, @Param("receiver")String receiver);
}

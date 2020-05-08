package xyz.cglzwz.chatroom.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.cglzwz.chatroom.domain.info;

import java.util.List;

@Mapper
public interface  InfoMapper {
    boolean UpdateInfo(@Param("information")info information);
    void CreateProfile(@Param("username") String username, @Param("identity")int id, @Param("nickname")String nickname);
    info getInfoByUsername(@Param("username") String username);
    List<info> selectPatientByIdentity(@Param("identity")int identity);
    List<info> selectDoctorByIdentity(@Param("identity")int identity);
    List<info> selectNameAndDescriptionByNickname(@Param("nickname") String s);
    info selectIdentityByUsername(@Param("username") String username);
    info selectNicknameAndUserNameByUsername(@Param("username") String username);
    info selectNicknameByUsername(@Param("username")String username);
}

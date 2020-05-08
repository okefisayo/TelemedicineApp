package xyz.cglzwz.chatroom.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.cglzwz.chatroom.domain.Reports;

import java.util.List;

@Mapper
public interface ReportsMapper {
    boolean Upload(@Param("param")Reports reports);
    Reports receiveById(@Param("id")int id);
    List<Reports> selectAllByUsername(@Param("username") String username);
}

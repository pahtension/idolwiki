package com.example.idolwiki.model.auth;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;

@Mapper
public interface AuthMapper {

    @Select("select * from tb_admin where id = #{id}")
    Admin findById(String id);

    @Insert("INSERT INTO tb_admin(id, password, login_date) " +
            "     VALUES (#{id}, #{password}, #{loginDate})")
    void createAdmin(@Param("id") String id,
                     @Param("password") String password, @Param("loginDate") LocalDateTime loginDate);
}

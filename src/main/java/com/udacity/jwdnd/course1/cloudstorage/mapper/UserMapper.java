package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM USERS WHERE username = #{username}")
    User getUser(String username);

    @Insert("INSERT INTO USERS (username, password, encodedkey, firstname, lastname) VALUES(#{username}, #{password}, #{encodedkey}, #{firstName}, #{lastName})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int insert(User user);
}

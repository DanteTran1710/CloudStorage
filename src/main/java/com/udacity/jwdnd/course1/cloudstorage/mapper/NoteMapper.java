package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM NOTES WHERE userid = #{userid}")
    List<Note> getNotesByUserId(Integer userId);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES(#{notetitle}, #{notedescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insert(Note note);

    @Update("UPDATE NOTES SET notetitle = #{notetitle}, notedescription = #{notedescription} WHERE noteid = #{noteId}")
    int update(Note note);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteId}")
    int delete(Integer noteId);
}

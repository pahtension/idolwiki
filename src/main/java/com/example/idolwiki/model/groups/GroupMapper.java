package com.example.idolwiki.model.groups;

import com.example.idolwiki.model.groups.form.GroupResponseForm;
import com.example.idolwiki.model.groups.form.GroupSimpleForm;
import com.example.idolwiki.model.groups.form.GroupUpdateForm;
import com.example.idolwiki.model.groups.form.GroupViewForm;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface GroupMapper {

    @Insert("insert into tb_groups(name,content,img,youtube,reg_date) " +
            "values(#{name}, #{content}, #{img}, #{youtube}, #{regDate})")
    void createGroup(Group group);


    @Select("SELECT seq, name, img FROM tb_groups WHERE name LIKE CONCAT('%', #{search}, '%') LIMIT #{pageStart}, 6")
    List<GroupSimpleForm> groupsIndex(@Param("pageStart") int pageStart, @Param("search") String search);

    @Select(" select * from tb_groups where seq = #{seq}")
    GroupViewForm getGroup(Integer seq);
//    Optional<Group> getGroup(Integer seq);

    @Update("update tb_groups set " +
            "name = #{group.name}, " +
            "content = #{group.content}, " +
            "img = #{group.img}, " +
            "youtube = #{group.youtube}, " +
            "reg_date = #{group.regDate} " +
            " where seq = #{seq}")
    void updateGroup(@Param("group") Group group, @Param("seq") Integer seq);

    @Select("select seq from tb_groups order by seq desc limit 1;")
    int lastSeq();

    @Delete("delete from tb_groups where seq = #{seq}")
    void deleteGroup(Integer seq);
}

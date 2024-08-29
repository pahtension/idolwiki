package com.example.idolwiki.model.member;

import com.example.idolwiki.model.member.form.MemberCreateForm;
import com.example.idolwiki.model.member.form.MemberResponseForm;
import com.example.idolwiki.model.member.form.MemberViewForm;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Mapper E
 *MyBatis에서 사용되는 애노테이션.
 *해당 인터페이스가 SQL 매퍼 역할을 하며 MyBatis가 해당 인터페이스를 매퍼로 인식하도록 함.
 */

@Mapper
public interface MemberMapper {


    @Select("select name, img from tb_members where groups_seq = #{seq}")
    List<MemberViewForm> getMembers(Integer seq);

    @Insert("<script>" +
            "insert into tb_members (name, img, groups_seq)" +
            "values " +
            "<foreach collection='memberCreateFormList' item='item' separator=','> " +
            "( #{item.name}, #{item.img}, #{seq} ) " +
            "</foreach> " +
            "</script>")
    void createMember(@Param("memberCreateFormList") List<MemberCreateForm> memberCreateFormList, @Param("seq") int seq);

    @Delete("delete from tb_members where groups_seq = #{seq}")
    void deleteMember(Integer seq);
}

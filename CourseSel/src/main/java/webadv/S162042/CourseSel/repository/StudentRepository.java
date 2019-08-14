package webadv.S162042.CourseSel.repository;

import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import webadv.S162042.CourseSel.entity.Student;
import webadv.S162042.CourseSel.entity.can_course;
import webadv.S162042.CourseSel.entity.s_exp;

@Mapper
public interface StudentRepository {
    /*@Results(键值对)*/
    @Results(id="StudentMap", value={
            @Result(property = "id", column = "s_id",  jdbcType= JdbcType.INTEGER),
            @Result(property = "sno", column = "s_no"),
            @Result(property = "name", column = "s_name"),
            @Result(property = "sex", column = "s_sex"),
            @Result(property = "cno", column = "s_cno"),
            @Result(property = "college", column = "s_college"),
            @Result(property = "password", column = "s_password")
    })

    @Select("select * from student")
    public List<Student> findAll();

    @Select("select * from student where s_no=#{s_no}")
    @ResultMap(value="StudentMap")
    public Student findOne(@Param("s_no")String s_no);

    @Select("select * from student where s_no=#{s_no} and s_password=md5(#{s_password})")
    @ResultMap(value="StudentMap")
    public Student validStudent(@Param("s_no")String s_no, @Param("s_password")String s_password);

    /*模糊查询，concat字符串拼接*/
    @Select("select * from student where s_name like concat('%',#{s_name},'%')")
    @ResultMap(value="StudentMap")
    public List<Student> findByName(@Param("s_name")String s_name);

    @Select("select alr_ccid from alr_course where alr_id=#{sno}")
    public List<String> findcouview(@Param("sno")String sno);

    @Select("select * from can_course where cc_id=#{cc_id}")
    public can_course findcan_course(@Param("cc_id")String cc_id);

    @Select("select alr_grad from alr_course where alr_id=#{sno} and alr_ccid=#{cc_id}")
    public int findgrade(@Param("cc_id")String cc_id,@Param("sno")String sno);

    @Select("select * from can_course")
    public List<can_course> findallcou();

    @Select("select cc_type from can_course where cc_id=#{cc_id}")
    public String findtype(@Param("cc_id")int cc_id);

    @Select("select cc_type from can_course where cc_id=#{cc_id}")
    public String count(@Param("cc_id")int cc_id);

    @Select("select count(*) from alr_course,can_course where can_course.cc_id=alr_course.alr_ccid and can_course.cc_type = #{type} and alr_course.alr_id=#{sno}")
    public int findbytype(@Param("type")String type,@Param("sno")String sno);

    @Select("select css_cnum from coursesel_setting where css_type=#{type}")
    public int findlimit(@Param("type")String type);

    @Select("select count(*) from alr_course where alr_id=#{sno} and alr_ccid=#{cc_id}")
    public int findalr(@Param("sno")String sno,@Param("cc_id")int cc_id);

    @Insert("insert into alr_course(alr_id,alr_ccid) values(#{sno},#{cc_id})")
    public boolean addcou(@Param("sno")String sno,@Param("cc_id")int cc_id);


    @Insert("insert into evaluation(eva_sid,eva_cid,eva_level,eva_message) values(#{sno},#{cc_id},#{eva_level},#{eva_message})")
    public boolean addeva(@Param("sno")String sno,@Param("cc_id")int cc_id,@Param("eva_level")String eva_level,@Param("eva_message")String eva_message);

    @Select("select count(*) from evaluation where eva_sid=#{sno} and eva_cid=#{cc_id}")
    public int findeva(@Param("sno")String sno,@Param("cc_id")int cc_id);

    @Select("select can_course.* from can_course,alr_course where can_course.cc_id=alr_course.alr_ccid and alr_course.alr_id=#{sno}")
    public List<can_course> findtime(@Param("sno")String sno);
    
    @Select("select * from computer_room_time where c_id=#{cc_id}")
    public s_exp findexp(@Param("cc_id")int cc_id);
    
    @Insert("update can_course set cc_pnum=cc_pnum+1 where cc_id=#{cc_id}")
    public boolean addnum(@Param("cc_id")int cc_id);
}

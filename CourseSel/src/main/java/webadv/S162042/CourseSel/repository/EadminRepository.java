package webadv.S162042.CourseSel.repository;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import webadv.S162042.CourseSel.entity.*;
import webadv.S162042.CourseSel.entity.Audited;

import java.util.List;
@Mapper
public interface EadminRepository {

    /*@Results(键值对)*/
    @Results(id="EadminMap", value={
            @Result(property = "e_id", column = "e_id",  jdbcType= JdbcType.INTEGER),
            @Result(property = "e_no", column = "e_no"),
            @Result(property = "e_name", column = "e_name"),
            @Result(property = "e_sex", column = "e_sex"),
            @Result(property = "e_password", column = "e_password")
    })

    @Select("select * from eadmin")
    public List<Eadmin> findAll();


    @Select("select * from eadmin where e_no=#{e_no}")
    @ResultMap(value="EadminMap")
    public Eadmin findOne(@Param("e_no")String e_no);

    @Select("select * from eadmin where e_no=#{e_no} and e_password=md5(#{e_password})")
    @ResultMap(value="EadminMap")
    public Eadmin validEadmin(@Param("e_no")String e_no, @Param("e_password")String e_password);

    /*模糊查询，concat字符串拼接*/
    @Select("select * from t_teacher where teacher_name like concat('%',#{e_name},'%')")
    @ResultMap(value="EadminMap")
    public List<Eadmin> findByName(@Param("e_name")String e_name);

    @Results(id="AuditedMap", value={
            @Result(property = "a_id", column = "a_id",  jdbcType= JdbcType.VARCHAR),
            @Result(property = "a_cid", column = "a_cid"),
            @Result(property = "a_cname", column = "a_cname"),
            @Result(property = "a_tid", column = "a_tid"),
            @Result(property = "a_num", column = "a_num"),
            @Result(property = "a_times", column = "a_times"),
            @Result(property = "a_condition", column = "a_condition")
    })
    @Select("select * from audited where a_condition=0")
    public List<Audited> findAudAll();

    @Select("select * from audited where a_condition=0 and a_cid=#{a_cid}")
    public Audited findAudExp(@Param("a_cid") int a_cid);

    @Delete("delete from audited where a_id=#{a_id}")
    public void delAudOne(@Param("a_id") String a_id);

    @Update("update audited set a_condition=#{a_condition} where a_id=#{a_id}")
    public void updAud(@Param("a_condition") int a_condition,@Param("a_id") String a_id);

    @Select("select * from audited where a_id=#{a_id}")
    public Audited findAudOne(@Param("a_id") String a_id);

    @Insert("insert into can_course (cc_id,cc_name,cc_tname,cc_time,cc_place,cc_pnum,cc_type,cc_exp,cc_max) " +
            "values(#{cc_id},#{cc_name},#{cc_tname},#{cc_time},#{cc_place},#{cc_pnum},#{cc_type},#{cc_exp},#{cc_max})")
    public void insCC(@Param("cc_id") int cc_id,@Param("cc_name") String cc_name,
                      @Param("cc_tname") String cc_tname, @Param("cc_time") String cc_time,
                      @Param("cc_place") String cc_place, @Param("cc_pnum") int cc_pnum,
                      @Param("cc_type") String cc_type, @Param("cc_exp") String cc_exp,
                      @Param("cc_max") int cc_max
    );
    @Select("select * from coursedb where cd_id=#{cd_id}")
    public Coursedb findCDOne(@Param("cd_id") int cd_id);

    //查询（非）实验课的全部课程
    @Select("select * from audited where a_exp=#{cd_exp} and a_condition=0")
    public List<Audited> findCDAllExp_Cou(@Param("cd_exp") String cd_exp);

    //查询星期几的教室的课程
    @Select("select * from classroom where cr_m=#{cr_m}")
    public List<Class_room_time> findCRm(@Param("cr_m") String cr_m);
    //教室名查询的教室
    @Select("select * from classroom where cr_place=#{cr_place}")
    public Class_room findCRplace(@Param("cr_place") String cr_place);
    //查询星期几第几节课教室是否为空
    @Select("select * from classroom_time where cr_id=#{cr_id} and crt_m=#{crt_m} and crt_d=#{crt_d}")
    public Class_room findCCR_cr(@Param("cr_id") int cr_id,@Param("crt_m") String crt_m,@Param("crt_d") String crt_d);

    //|A101星期二第1节课|A103星期四第1节课|
    @Select("select * from classroom")
    public List<Class_room> findCRAll();

    //课程安排设置
    @Update("update course_setting set cs_m=#{cs_m} where cs_type=#{cs_type}")
    public void updCS(@Param("cs_m") String cs_m,@Param("cs_type") String cs_type);

    @Select("select * from course_setting where cs_type=#{cs_type}")
    public Course_setting findCStype(@Param("cs_type") String cs_type);

    //课程时间
    @Select("select * from classroom_time where cr_id=#{cr_id} and crt_m=#{crt_m}")
    public List<Class_room_time> findCRT(@Param("cr_id") String cr_id,@Param("crt_m") String crt_m);

    @Insert("insert into classroom_time(cr_id,c_id,crt_m,crt_d) values(#{cr_id},#{c_id},#{crt_m},#{crt_d})")
    public void insCRT(@Param("cr_id") String cr_id,@Param("c_id") String c_id,@Param("crt_m") String crt_m,@Param("crt_d") String crt_d);

    //查询教师申请所有
    @Select("select * from can_course_modify where ccm_type=0")
    public List<can_course_modify> findAllCCM();

    //教师申请处理-不同意;
    @Update("update can_course_modify set ccm_type=#{ccm_type} where ccm_id=#{ccm_id}")
    public void updCmm(@Param("ccm_type") int ccm_type,@Param("ccm_id") String ccm_id);

    //查询单个教师申请
    @Select("select * from can_course_modify where ccm_id=#{ccm_id}")
    public can_course_modify findCcmOne(@Param("ccm_id") String ccm_id);

    @Update("update can_course set cc_place=#{cc_place} where cc_id=#{cc_id}")
    public void updCcm_cc(@Param("cc_place") String cc_place,@Param("cc_id") int cc_id);

    //查询选课设置
    @Select("select * from coursesel_setting")
    public List<Coursesel_setting> findAllCSS();

    @Update("update coursesel_setting set css_cnum=#{css_cnum} where css_id=#{css_id}")
    public void updCSS(@Param("css_cnum") int css_cnum,@Param("css_id") int css_id);

    //查询教务人员
    @Select("select * from eadmin where e_no=#{e_no}")
    public Eadmin findEone(@Param("e_no") String e_no);
    
    @Select("select * from course_setting")
    public List<Course_setting> findAllCS();
}

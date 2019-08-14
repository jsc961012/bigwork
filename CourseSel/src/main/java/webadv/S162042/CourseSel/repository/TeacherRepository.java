package webadv.S162042.CourseSel.repository;

import org.apache.ibatis.annotations.*;
import webadv.S162042.CourseSel.entity.*;

import java.util.List;

@Mapper
public interface TeacherRepository {

    //查询所有教师信息
    @Select("select * from teacher")
    public List<Teacher> findAll();

    //按工号查询教师
    @Select("select * from teacher where t_no=#{t_no}")
    public Teacher findOne(@Param("t_no") String t_no);

    //登陆匹配
    @Select("select * from teacher where t_no=#{t_no} and t_password=md5(#{t_password})")
    public Teacher validTeacher(@Param("t_no") String t_no, @Param("t_password") String t_password);

    //模糊匹配教师
    @Select("select * from teacher where t_name like concat('%',#{t_name},'%')")
    public List<Teacher> findByName(@Param("t_name") String t_name);

    //按教师名查询教师开设的课程
    @Select("select * from can_course where cc_tname=#{cc_tname}")
    public List<Tea_can_course> findMyCourse(@Param("cc_tname") String cc_tname);

    //按星期查看某天的课程
    @Select("select * from can_course where cc_tname=#{cc_tname} and cc_place like concat('%',#{cc_place},'%');")
    public List<Tea_can_course> findMyCourse_week(@Param("cc_tname") String cc_tname, @Param("cc_place") String cc_place);

    //查看开设课程的所有课程类型
    @Select("select distinct cc_type from can_course;")
    public List<String> findMyCourseType();

    //按课程号查看可选课程
    @Select("select * from can_course where cc_id=#{cc_id}")
    public Tea_can_course findCourseDetial(@Param("cc_id") String cc_id);

    //按课程名和教师名查看可选课程
    @Select("select * from can_course where cc_name=#{cc_name} and cc_tname=#{cc_tname}")
    public Tea_can_course findCourseDetial_cn(@Param("cc_name") String cc_name, @Param("cc_tname") String cc_tname);

    //找到所有某位教师可以开设的课程
    @Select("select * from coursedb where cd_name not in (select cc_name from can_course where cc_tname=#{cc_tname}) and cd_name not in (select a_cname from audited where a_tid=#{a_tid})")
    public List<Tea_coursedb> findCoursedb(@Param("cc_tname") String cc_tname, @Param("a_tid") String a_tid);

    //申请开课
    @Insert("insert into audited values(#{a_id},#{a_cid},#{a_cname},#{a_tid},#{a_num},#{a_times},#{a_condition},#{a_exp})")
    public void insertApply(@Param("a_id") String a_id, @Param("a_cid") String a_cid, @Param("a_cname") String a_cname,
                            @Param("a_tid") String a_tid, @Param("a_num") String a_num, @Param("a_times") String a_times,
                            @Param("a_condition") int a_condition, @Param("a_exp") String a_exp);

    //按教师号查找待审核的课程
    @Select("select * from audited where a_tid=#{a_tid}")
    public List<Tea_audited> findAudited(@Param("a_tid") String a_tid);

    //生成学生成绩表
    @Select("select alr_course.*,student.s_name,student.s_cno,student.s_college,can_course.cc_name from student,alr_course,can_course where can_course.cc_tname=#{cc_tname} and alr_course.alr_id=student.s_no and alr_course.alr_ccid=can_course.cc_id;")
    public List<Tea_addgrade> FindStu(@Param("cc_tname") String cc_tname);

    //查找课程类型
    @Select("select distinct can_course.cc_name from student,alr_course,can_course where can_course.cc_tname=#{cc_tname} and alr_course.alr_id=student.s_no and alr_course.alr_ccid=can_course.cc_id;")
    public List<String> FindCouType(@Param("cc_tname") String cc_tname);

    //
    @Select("select alr_course.*,student.s_name,student.s_cno,student.s_college,can_course.cc_name from student,alr_course,can_course where can_course.cc_tname=#{cc_tname} and can_course.cc_name=#{cc_name} and alr_course.alr_id=student.s_no and alr_course.alr_ccid=can_course.cc_id;")
    public List<Tea_addgrade> FindStuCou(@Param("cc_tname") String cc_tname, @Param("cc_name") String cc_name);

    //录成绩
    @Update("update alr_course set alr_grad=#{alr_grad} where alr_id=#{alr_id} and alr_ccid=#{alr_ccid};")
    public void InsertGrade(@Param("alr_id") String alr_id, @Param("alr_ccid") String alr_ccid,
                            @Param("alr_grad") String alr_grad);

    //申请调课
    @Insert("insert into can_course_modify(cc_id,cc_name,cc_tname,cc_time,cc_place,cc_type,cc_exp,cc_max,ccm_type,ccm_operation,ccm_time,ccm_remarks) values(#{cc_id},#{cc_name},#{cc_tname},#{cc_time},#{cc_place},#{cc_type},#{cc_exp},#{cc_max},#{ccm_type},#{ccm_operation},#{ccm_time},#{ccm_remarks});")
    public void InsertMod(@Param("cc_id") int cc_id, @Param("cc_name") String cc_name,
                          @Param("cc_tname") String cc_tname, @Param("cc_time") String cc_time, @Param("cc_place") String cc_place,
                          @Param("cc_type") String cc_type, @Param("cc_exp") String cc_exp, @Param("cc_max") String cc_max,
                          @Param("ccm_type") String ccm_type, @Param("ccm_operation") String ccm_operation,
                          @Param("ccm_time") String ccm_time, @Param("ccm_remarks") String ccm_remarks);

    //获得教室
    @Select("select cr_place from classroom;")
    public List<String> findClass();

    //查看申请成功的课程
    @Select("select * from audited where (a_tid=#{a_tid} and a_condition=1 and a_exp='false') or (a_tid=#{a_tid} and a_condition=3 and a_exp='true')")
    public List<Tea_audited> findPassAudited(@Param("a_tid") String a_tid);

    //查看正在等待审核课程
    @Select("select * from audited where a_tid=#{a_tid} and a_condition=0")
    public List<Tea_audited> findWaitAudited(@Param("a_tid") String a_tid);


}

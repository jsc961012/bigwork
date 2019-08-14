package webadv.S162042.CourseSel.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

import webadv.S162042.CourseSel.entity.Jadmin;
import webadv.S162042.CourseSel.entity.SectionDetail;
import webadv.S162042.CourseSel.entity.Teacher;
import webadv.S162042.CourseSel.entity.Audited;
import webadv.S162042.CourseSel.entity.Computer_cou_schedual;
import webadv.S162042.CourseSel.entity.Computer_room;

@Mapper
public interface JadminRepository {
	@Results(id = "JadminMap", value = { @Result(property = "id", column = "j_id", jdbcType = JdbcType.INTEGER),
			@Result(property = "no", column = "j_no"), @Result(property = "name", column = "j_name"),
			@Result(property = "sex", column = "j_sex"), @Result(property = "password", column = "j_password") })

	@Select("select * from jadmin")
	public List<Jadmin> findAll();

	@Select("select * from jadmin where j_no=#{j_no}")
	@ResultMap(value = "JadminMap")
	public Jadmin findOne(@Param("j_no") String j_no);

	@Select("select * from jadmin where j_no=#{j_no} and j_password=md5(#{j_password})")
	@ResultMap(value = "JadminMap")
	public Jadmin validJadmin(@Param("j_no") String j_no, @Param("j_password") String j_password);

	/* 模糊查询，concat字符串拼接 */
	@Select("select * from jadmin where j_name like concat('%',#{j_name},'%')")
	@ResultMap(value = "JadminMap")
	public List<Jadmin> findByName(@Param("j_name") String j_name);

	/**
	 * 对机房操作
	 *
	 */
	/* 查询所有机房信息 */
	@Select("select * from computer_room")
	public List<Computer_room> findRoom();

	/* 按机房类型查询机房信息 */
	@Select("select distinct com_place from computer_room order by com_place asc;")
	public List<String> findRoomType();

	/* 插入机房信息 */
	@Insert("insert into computer_room (com_place,com_num,com_charge) values (#{com_place},#{com_num},#{com_charge})")
	public void insert(@Param("com_place") String com_place, @Param("com_num") Integer com_num,
			@Param("com_charge") String com_charge);

	/* 更新机房信息 */
	@Update("update computer_room set com_num=#{com_num},com_charge=#{com_charge} where com_place=#{com_place} ")
	public void update(@Param("com_place") String com_place, @Param("com_num") Integer com_num,
			@Param("com_charge") String com_charge);

	/* 删除机房操作 */
	@Delete("delete from computer_room where com_place=#{com_place}")
	public void delete(@Param("com_place") String com_place);

	//
	@Select("select distinct com_place from computer_room")
	public int findCourseCount();

	/**
	 * 对实验课程操作
	 *
	 */
	/* 显示已有课程表 */
	@Select("select * from computer_room_time")
	public List<Computer_cou_schedual> getSchedual();

	/* 找出机房地点 */
	@Select("select * from computer_room where com_id=#{com_id}")
	public Computer_room findCom_place(@Param("com_id") String id);

	/* 寻找待审核的课程信息 */
	@Select("select a_id,a_cid,a_cname,a_tid from audited where a_condition='2'")
	public List<Audited> findAudited();

	/* 找出待审核课程的教师名字 */
	@Select("select * from teacher where t_no=#{t_no}")
	public Teacher findTeaName(@Param("t_no") String t_no);

	/* 插入待审核的课程 */
	@Insert("insert into computer_room_time(cr_id,c_id,crt_cname,crt_m,crt_sec,crt_tid,crt_tname,crt_place) values (#{cr_id},#{c_id},#{crt_cname},#{crt_m},#{crt_sec},#{crt_tid},#{crt_tname},#{crt_place})")
	public void Addcourse(@Param("cr_id") int cr_id, @Param("c_id") String c_id, @Param("crt_cname") String crt_cname,
			@Param("crt_m") String crt_m, @Param("crt_sec") String crt_sec, @Param("crt_tid") String crt_tid,
			@Param("crt_tname") String crt_tname, @Param("crt_place") String crt_place);

	/* 显示待审核课程条数 */
	@Select("select count(*) from audited where a_condition='2'")
	public int AuditedNum();

	/* 查询星期几的课程节数数目 */
	@Select("select count(*) from computer_room_time where crt_m=#{crt_m}")
	public int findSectionNum(@Param("crt_m") String crt_m);

	/* 找出星期几具体有哪几节课 */
	@Select("select crt_sec from computer_room_time where crt_m=#{crt_m}")
	public List<SectionDetail> findDetail(@Param("crt_m") String crt_m);

	/* 更改待审核的条件 */
	@Update("update audited set a_condition='3' where a_id=#{a_id}")
	public void CorrectAudited(@Param("a_id") String a_id);

	/* 寻找待审核的课程信息 */
	@Select("select * from audited where a_cid=${a_cid}")
	public Audited findOneAudited(@Param("a_cid") String a_cid);

	/* 寻找待审核的课程信息 */
	@Select("select count(*) from computer_room;")
	public int findcountroom();

	// 添加到可选课程
	@Insert("insert into can_course (cc_id,cc_name,cc_tname,cc_time,cc_place,cc_pnum,cc_type,cc_exp,cc_max) "
			+ "values(#{cc_id},#{cc_name},#{cc_tname},#{cc_time},#{cc_place},#{cc_pnum},#{cc_type},#{cc_exp},#{cc_max})")
	public void insCC(@Param("cc_id") String cc_id, @Param("cc_name") String cc_name, @Param("cc_tname") String cc_tname,
			@Param("cc_time") String cc_time, @Param("cc_place") String cc_place, @Param("cc_pnum") int cc_pnum,
			@Param("cc_type") String cc_type, @Param("cc_exp") String cc_exp, @Param("cc_max") int cc_max);
	
	/* 寻找待审核的课程信息 */
	@Select("select cd_type from coursedb where cd_id=${cd_id}")
	public String findType(@Param("cd_id") String cd_id);
}

package webadv.S162042.CourseSel.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import webadv.S162042.CourseSel.entity.Teacher;

@Repository
public class TeacherRepository {	
	@Autowired
	private JdbcTemplate jdbcTemplate;	
	public List<Teacher> findAll(){
		return null;		
		// …		
	}
	
	public Teacher validTeacher(String no,String password) {
		String sql="select t_id as id,t_no as no,t_name as name,t_sex as sex,t_college as college,t_password as password from teacher where t_no=? and t_password=md5(?)";		
		RowMapper<Teacher> rowMapper=new BeanPropertyRowMapper<Teacher>(Teacher.class);
		List<Teacher> list = jdbcTemplate.query(sql, rowMapper,no,password);	
		if (list != null && list.size()>0) {
			System.out.println("*****"+list.get(0).getName());
			return list.get(0);
		}else
			return null;				
	}
}

package webadv.S162042.CourseSel.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import webadv.S162042.CourseSel.entity.Student;


@Repository
public class StudentRepository {	
	@Autowired
	private JdbcTemplate jdbcTemplate;	
	public List<Student> findAll(){	
		return null;
		// ...		
	}
	
	public Student validStudent(String sno,String password) {
		String sql="select s_id as id,s_no as sno,s_name as name,s_sex as sex,s_cno as cno,s_college as college from student where s_no=? and s_password=md5(?)";		
		RowMapper<Student> rowMapper=new BeanPropertyRowMapper<Student>(Student.class);
		List<Student> list = jdbcTemplate.query(sql, rowMapper,sno,password);	
		if (list != null && list.size()>0) {
			System.out.println("%%%%%"+list.get(0).getName());
			return list.get(0);
		}else
			return null;				
	}
}

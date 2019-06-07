package webadv.S162042.CourseSel.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import webadv.S162042.CourseSel.entity.Jadmin;

@Repository
public class JadminRepository {	
	@Autowired
	private JdbcTemplate jdbcTemplate;	
	public List<Jadmin> findAll(){
		return null;		
	}
	
	public Jadmin validJadmin(String no,String password) {
		String sql="select j_id as id,j_no as no,j_name as name,j_sex as sex,j_password as password from jadmin where j_no=? and j_password=md5(?)";		
		RowMapper<Jadmin> rowMapper=new BeanPropertyRowMapper<Jadmin>(Jadmin.class);
		List<Jadmin> list = jdbcTemplate.query(sql, rowMapper,no,password);	
		if (list != null && list.size()>0) {
			System.out.println(list.get(0).getName());
			return list.get(0);
		}else
			return null;				
	}
}

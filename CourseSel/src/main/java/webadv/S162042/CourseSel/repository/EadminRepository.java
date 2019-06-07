package webadv.S162042.CourseSel.repository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import webadv.S162042.CourseSel.entity.Eadmin;
import java.util.List;
@Repository
public class EadminRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public List<Eadmin> findAll(){
        return null;
    }

    public Eadmin validEadmin( String no,String password) {
        String sql="select e_id as e_id,e_no as e_no,e_name as e_name,e_sex as e_sex,e_password as e_password from eadmin where e_no=? and  e_password=md5(?)";
        RowMapper<Eadmin> rowMapper=new BeanPropertyRowMapper<Eadmin>(Eadmin.class);//代替原来 数据库取出数据一一对应的操作
        List<Eadmin> list =jdbcTemplate.query(sql, rowMapper,no,password);
        if (list != null && list.size()>0) {
            System.out.println(list.get(0).getE_name());
            return list.get(0);
        }else
            return null;
    }

}

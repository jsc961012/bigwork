package webadv.S162042.CourseSel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import webadv.S162042.CourseSel.entity.Student;
import webadv.S162042.CourseSel.entity.can_course;
import webadv.S162042.CourseSel.repository.StudentRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CourseSelApplicationTests {
@Autowired
private StudentRepository st;
private can_course c;
private Student s;
	@Test
	public void contextLoads() {
		st.addnum(101);
	}

}

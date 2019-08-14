package webadv.S162042.CourseSel;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("webadv.S162042.CourseSel.repository")
public class CourseSelApplication {

	public static void main(String[] args) {
		SpringApplication.run(CourseSelApplication.class, args);
	}
}

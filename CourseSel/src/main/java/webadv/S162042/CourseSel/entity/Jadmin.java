package webadv.S162042.CourseSel.entity;

public class Jadmin {
	private int id;
	private String no;
	private String name;
	private String sex;
	private String password;

	public Jadmin() {

	}

	public Jadmin(String no, String name, String sex, String password) {
		this.no = no;
		this.name = name;
		this.sex = sex;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}

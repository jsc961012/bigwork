package webadv.S162042.CourseSel.entity;

public class Teacher {
	private int t_id;
	private String t_no;
	private String t_name;
	private String t_sex;
	private String t_college;
	private String t_password;

	public Teacher() {

	}

	public Teacher(String name, String sex, String college, String password) {
		this.t_name = name;
		this.t_sex = sex;
		this.t_college = college;
		this.t_password = password;
	}

	public int getT_id() {
		return t_id;
	}

	public void setT_id(int t_id) {
		this.t_id = t_id;
	}

	public String getT_no() {
		return t_no;
	}

	public void setT_no(String t_no) {
		this.t_no = t_no;
	}

	public String getT_name() {
		return t_name;
	}

	public void setT_name(String t_name) {
		this.t_name = t_name;
	}

	public String getT_sex() {
		return t_sex;
	}

	public void setT_sex(String t_sex) {
		this.t_sex = t_sex;
	}

	public String getT_college() {
		return t_college;
	}

	public void setT_college(String t_college) {
		this.t_college = t_college;
	}

	public String getT_password() {
		return t_password;
	}

	public void setT_password(String t_password) {
		this.t_password = t_password;
	}

}

package webadv.S162042.CourseSel.entity;

public class Tea_addgrade {
	private int alr_id;// 学号
	private String alr_ccid;// 可选课程表中的编号
	private double alr_grad;// 成绩
	private String s_name;// 学生名
	private String cc_name;// 课程名
	private String s_cno;// 班级
	private String s_college;// 学院

	public int getAlr_id() {
		return alr_id;
	}

	public void setAlr_id(int alr_id) {
		this.alr_id = alr_id;
	}

	public String getAlr_ccid() {
		return alr_ccid;
	}

	public void setAlr_ccid(String alr_ccid) {
		this.alr_ccid = alr_ccid;
	}

	public double getAlr_grad() {
		return alr_grad;
	}

	public void setAlr_grad(double alr_grad) {
		this.alr_grad = alr_grad;
	}

	public String getS_name() {
		return s_name;
	}

	public void setS_name(String s_name) {
		this.s_name = s_name;
	}

	public String getCc_name() {
		return cc_name;
	}

	public void setCc_name(String cc_name) {
		this.cc_name = cc_name;
	}

	public String getS_cno() {
		return s_cno;
	}

	public void setS_cno(String s_cno) {
		this.s_cno = s_cno;
	}

	public String getS_college() {
		return s_college;
	}

	public void setS_college(String s_college) {
		this.s_college = s_college;
	}

}

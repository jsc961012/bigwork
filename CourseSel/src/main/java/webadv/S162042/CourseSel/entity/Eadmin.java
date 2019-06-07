package webadv.S162042.CourseSel.entity;

public class Eadmin {
    private int e_id;
    private String e_no;
    private String e_name;
    private String e_sex;
    private String e_password;
	public Eadmin() {
		super();
}	

    public Eadmin(String e_no, String e_name, String e_sex) {
		super();
		this.e_no = e_no;
		this.e_name = e_name;
		this.e_sex = e_sex;
	}

	public int getE_id() {
        return e_id;
    }

    public void setE_id(int e_id) {
        this.e_id = e_id;
    }

    public String getE_no() {
        return e_no;
    }

    public void setE_no(String e_no) {
        this.e_no = e_no;
    }

    public String getE_name() {
        return e_name;
    }

    public void setE_name(String e_name) {
        this.e_name = e_name;
    }

    public String getE_sex() {
        return e_sex;
    }

    public void setE_sex(String e_sex) {
        this.e_sex = e_sex;
    }

    public String getE_password() {
        return e_password;
    }

    public void setE_password(String e_password) {
        this.e_password = e_password;
    }
}

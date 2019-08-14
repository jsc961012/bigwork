package webadv.S162042.CourseSel.entity;

public class Course {
    private String c_id;
    private String c_name;
    private String c_tea;
    private String c_time;
    private String c_place;
    private int c_pnum;
    private String c_type;
    private int c_npnum;
    
    public int getC_npnum() {
		return c_npnum;
	}

	public void setC_npnum(int c_npnum) {
		this.c_npnum = c_npnum;
	}

	public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public String getC_tea() {
        return c_tea;
    }

    public void setC_tea(String c_tea) {
        this.c_tea = c_tea;
    }

    public String getC_time() {
        return c_time;
    }

    public void setC_time(String c_time) {
        this.c_time = c_time;
    }

    public String getC_place() {
        return c_place;
    }

    public void setC_place(String c_place) {
        this.c_place = c_place;
    }

    public int getC_pnum() {
        return c_pnum;
    }

    public void setC_pnum(int c_pnum) {
        this.c_pnum = c_pnum;
    }

    public String getC_type() {
        return c_type;
    }

    public void setC_type(String c_type) {
        this.c_type = c_type;
    }
}

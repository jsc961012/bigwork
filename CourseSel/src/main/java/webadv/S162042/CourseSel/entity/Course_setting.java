package webadv.S162042.CourseSel.entity;

public class Course_setting {
    private int cs_id;
    private String cs_type;
    private String cs_m;
    private String cs_d;

    public String getCs_m() {
        return cs_m;
    }

    public void setCs_m(String cs_m) {
        this.cs_m = cs_m;
    }

    public String getCs_d() {
        return cs_d;
    }

    public void setCs_d(String cs_d) {
        this.cs_d = cs_d;
    }

    public int getCs_id() {
        return cs_id;
    }

    public void setCs_id(int cs_id) {
        this.cs_id = cs_id;
    }

    public String getCs_type() {
        return cs_type;
    }

    public void setCs_type(String cs_type) {
        this.cs_type = cs_type;
    }
}

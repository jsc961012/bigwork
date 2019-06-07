package webadv.S162042.CourseSel.entity;

public class Teacher {
    private int id;
    private String no;
    private String name;
    private String sex;
    private String college;
    private String password;

    public Teacher(){

    }

    public Teacher(String name, String sex, String college, String password) {
        this.name = name;
        this.sex = sex;
        this.college = college;
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

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

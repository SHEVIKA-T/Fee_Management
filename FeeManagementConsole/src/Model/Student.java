package Model;

public class Student {
    private String stu_id;
    private String stu_name;
    private int course_id;
    private int course_year;
    private String stu_mail;
    private String stu_Phno;
    private String stu_dob;
    private String password;
   

    public Student(String stu_id, String stu_name, int course_id, int course_year, String stu_mail, String stu_Phno, String stu_dob,String password) {
        this.stu_id = stu_id;
        this.stu_name = stu_name;
        this.course_id = course_id;
        this.course_year = course_year;
        this.stu_mail = stu_mail;
        this.stu_Phno = stu_Phno;
        this.stu_dob = stu_dob;
        this.password=password;
    }

    
    public String getStu_id() {
        return stu_id;
    }

    public void setStu_id(String stu_id) {
        this.stu_id = stu_id;
    }

    public String getStu_name() {
        return stu_name;
    }

    public void setStu_name(String stu_name) {
        this.stu_name = stu_name;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public int getCourse_year() {
        return course_year;
    }

    public void setCourse_year(int course_year) {
        this.course_year = course_year;
    }

    public String getStu_mail() {
        return stu_mail;
    }

    public void setStu_mail(String stu_mail) {
        this.stu_mail = stu_mail;
    }

    public String getStu_Phno() {
        return stu_Phno;
    }

    public void setStu_Phno(String stu_Phno) {
        this.stu_Phno = stu_Phno;
    }

    public String getStu_dob() {
        return stu_dob;
    }

    public void setStu_dob(String stu_dob) {
        this.stu_dob = stu_dob;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String  passwordb) {
        this. password =  password;
    }

    public String toString() {
        return "Student{" +
                "stu_id=" + stu_id +
                ", stu_name='" + stu_name + '\'' +
                ", course_id=" + course_id +
                ", course_year=" + course_year +
                ", stu_mail='" + stu_mail + '\'' +
                ", stu_Phno='" + stu_Phno + '\'' +
                ", stu_dob='" + stu_dob + '\'' +
                '}';
    }
}


package Model;

public class Mentor {
    private String mentorId;
    private int mentorCourseId;
    private String mentorName;
    private String username;
    private String password;

    public Mentor(String mentorId, int mentorCourseId, String mentorName, String username, String password) {
        this.mentorId = mentorId;
        this.mentorCourseId = mentorCourseId;
        this.mentorName = mentorName;
        this.username = username;
        this.password = password;
    }
    public Mentor(String mentorId,int mentorCourseId,String mentorName, String username) {
    	this.mentorCourseId=mentorCourseId;
    	this.mentorName=
        this.mentorId = mentorId;
        this.username = username;
    }
    
	public String getMentorId() {
        return mentorId;
    }

    public void setMentorId(String mentorId) {
        this.mentorId = mentorId;
    }

    public int getMentorCourseId() {
        return mentorCourseId;
    }

    public void setMentorCourseId(int mentorCourseId) {
        this.mentorCourseId = mentorCourseId;
    }

    public String getMentorName() {
        return mentorName;
    }

    public void setMentorName(String mentorName) {
        this.mentorName = mentorName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString() {
        return "Mentor{" +
                "mentorId='" + mentorId + '\'' +
                ", mentorCourseId=" + mentorCourseId +
                ", mentorName='" + mentorName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

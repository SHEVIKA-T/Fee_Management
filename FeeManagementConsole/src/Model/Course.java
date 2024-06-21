package Model;

public class Course {
    private int courseId;
    private String courseName;
    private int courseFee;

    public Course(int courseId, String courseName, int courseFee) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseFee = courseFee;
    }

    // Getters and setters
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCourseFee() {
        return courseFee;
    }

    public void setCourseFee(int courseFee) {
        this.courseFee = courseFee;
    }

    public String toString() {
        return "Course [courseId=" + courseId + ", courseName=" + courseName + ", courseFee=" + courseFee + "]";
    }
}


package Model;

public class PreFeeReport {
    private String stuId;
    private String lastPaidFeeId;
    private int courseId;
    private int courseYear;
    private int totalFee;
    private String feeStatus;
    private int feeBalance;
    private String comments;

    public PreFeeReport(String stuId, String lastPaidFeeId, int courseId, int courseYear, int totalFee, String feeStatus, int feeBalance, String comments) {
        this.stuId = stuId;
        this.lastPaidFeeId = lastPaidFeeId;
        this.courseId = courseId;
        this.courseYear = courseYear;
        this.totalFee = totalFee;
        this.feeStatus = feeStatus;
        this.feeBalance = feeBalance;
        this.comments = comments;
    }
    public PreFeeReport(Payment payment) {
        this.stuId = payment.getStuId();
        this.lastPaidFeeId = payment.getLastPaidFeeId();
        this.courseId = payment.getCourseId();
        this.courseYear = payment.getCourseYear();
        this.totalFee = payment.getTotalFee();
        this.feeStatus = payment.getFeeStatus();
        this.feeBalance = payment.getFeeBalance();
        this.comments = payment.getComments();
    }

    public String getStuId() {
        return stuId;
    }

    public String getLastPaidFeeId() {
        return lastPaidFeeId;
    }

    public int getCourseId() {
        return courseId;
    }

    public int getCourseYear() {
        return courseYear;
    }

    public int getTotalFee() {
        return totalFee;
    }

    public String getFeeStatus() {
        return feeStatus;
    }

    public int getFeeBalance() {
        return feeBalance;
    }

    public String getComments() {
        return comments;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public void setLastPaidFeeId(String lastPaidFeeId) {
        this.lastPaidFeeId = lastPaidFeeId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public void setCourseYear(int courseYear) {
        this.courseYear = courseYear;
    }

    public void setTotalFee(int totalFee) {
        this.totalFee = totalFee;
    }

    public void setFeeStatus(String feeStatus) {
        this.feeStatus = feeStatus;
    }

    public void setFeeBalance(int feeBalance) {
        this.feeBalance = feeBalance;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String toString() {
        return "PreFeeReport{" +
                "stuId='" + stuId + '\'' +
                ", lastPaidFeeId='" + lastPaidFeeId + '\'' +
                ", courseId=" + courseId +
                ", courseYear=" + courseYear +
                ", totalFee=" + totalFee +
                ", feeStatus='" + feeStatus + '\'' +
                ", feeBalance=" + feeBalance +
                ", comments='" + comments + '\'' +
                '}';
    }
}


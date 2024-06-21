package Model;

public class Payment {
 private String stuId;
 private String lastPaidFeeId;
 private int courseId;
 private int courseYear;
 private int totalFee;
 private String feeStatus;
 private int feeBalance;
 private String comments;

 public Payment() {
	   
	}
 
 public Payment(String stuId, String lastPaidFeeId, int courseId, int courseYear, int totalFee, String feeStatus, int feeBalance, String comments) {
     this.stuId = stuId;
     this.lastPaidFeeId = lastPaidFeeId;
     this.courseId = courseId;
     this.courseYear = courseYear;
     this.totalFee=totalFee;
     this.feeStatus = feeStatus;
     this.feeBalance = feeBalance;
     this.comments = comments;
 }

 public String getStuId() {
     return stuId;
 }

 public void setStuId(String stuId) {
     this.stuId = stuId;
 }

 public String getLastPaidFeeId() {
     return lastPaidFeeId;
 }

 public void setLastPaidFeeId(String lastPaidFeeId) {
     this.lastPaidFeeId = lastPaidFeeId;
 }

 public int getCourseId() {
     return courseId;
 }

 public void setCourseId(int courseId) {
     this.courseId = courseId;
 }

 public int getCourseYear() {
     return courseYear;
 }

 public void setCourseYear(int courseYear) {
     this.courseYear = courseYear;
 }
 public int getTotalFee() {
     return totalFee;
 }

 public void setTotalFee(int totalFee) {
     this.totalFee = totalFee;
 }

 public String getFeeStatus() {
     return feeStatus;
 }

 public void setFeeStatus(String feeStatus) {
     this.feeStatus = feeStatus;
 }

 public int getFeeBalance() {
     return feeBalance;
 }

 public void setInitialFeeBalance(int courseFee) {
     this.feeBalance = courseFee;
 }

 public void updateFeeBalance(int newBalance) {
     this.feeBalance += newBalance;
 }

 public String getComments() {
     return comments;
 }

 public void setComments(String comments) {
     this.comments = comments;
 }

 public String toString() {
     return "Payment{" +
             "stuId='" + stuId + '\'' +
             ", lastPaidFeeId=" + lastPaidFeeId +
             ", courseId=" + courseId +
             ", courseYear=" + courseYear +
             ", feeStatus='" + feeStatus + '\'' +
             ", feeBalance=" + feeBalance +
             ", comments='" + comments + '\'' +
             '}';
 }
}


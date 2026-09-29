package model;

import java.io.Serializable;

// 投递记录数据模型
public class Delivery implements Serializable, Comparable<Delivery> {
    private static final long serialVersionUID = 1L;

    private String id;
    private String resumeId;
    private String jobId;
    private String studentId;
    private String companyId;
    private long deliverTime;
    private String status; // pending, viewed, invited, rejected

    public Delivery() {}

    public Delivery(String id, String resumeId, String jobId, String studentId,
                    String companyId, long deliverTime, String status) {
        this.id = id;
        this.resumeId = resumeId;
        this.jobId = jobId;
        this.studentId = studentId;
        this.companyId = companyId;
        this.deliverTime = deliverTime;
        this.status = status;
    }

    // 实现Comparable接口，用于TreeSet按投递时间排序
    @Override
    public int compareTo(Delivery other) {
        return Long.compare(this.deliverTime, other.deliverTime);
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getResumeId() { return resumeId; }
    public void setResumeId(String resumeId) { this.resumeId = resumeId; }
    public String getJobId() { return jobId; }
    public void setJobId(String jobId) { this.jobId = jobId; }
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public String getCompanyId() { return companyId; }
    public void setCompanyId(String companyId) { this.companyId = companyId; }
    public long getDeliverTime() { return deliverTime; }
    public void setDeliverTime(long deliverTime) { this.deliverTime = deliverTime; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "投递ID:" + id + " | 学生ID:" + studentId + " | 状态:" + status;
    }
}
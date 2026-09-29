package model;

import java.io.Serializable;

// 收藏数据模型
public class Favorite implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String studentId;
    private String jobId;
    private long collectTime;

    public Favorite() {}

    public Favorite(String id, String studentId, String jobId, long collectTime) {
        this.id = id;
        this.studentId = studentId;
        this.jobId = jobId;
        this.collectTime = collectTime;
    }

    // NOTE: 重写hashCode和equals用于HashSet去重
    @Override
    public int hashCode() {
        return (studentId + jobId).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Favorite other = (Favorite) obj;
        return studentId.equals(other.studentId) && jobId.equals(other.jobId);
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public String getJobId() { return jobId; }
    public void setJobId(String jobId) { this.jobId = jobId; }
    public long getCollectTime() { return collectTime; }
    public void setCollectTime(long collectTime) { this.collectTime = collectTime; }

    @Override
    public String toString() {
        return "职位ID:" + jobId + " | 收藏时间:" + collectTime;
    }
}
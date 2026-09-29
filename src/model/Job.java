package model;

import java.io.Serializable;

// 职位数据模型
public class Job implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String companyId;
    private String title;
    private String companyName;
    private int salary;
    private String location;
    private String requirement;
    private long publishTime;
    private String status; // open, closed

    public Job() {}

    public Job(String id, String companyId, String title, String companyName, int salary,
               String location, String requirement, long publishTime, String status) {
        this.id = id;
        this.companyId = companyId;
        this.title = title;
        this.companyName = companyName;
        this.salary = salary;
        this.location = location;
        this.requirement = requirement;
        this.publishTime = publishTime;
        this.status = status;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getCompanyId() { return companyId; }
    public void setCompanyId(String companyId) { this.companyId = companyId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public int getSalary() { return salary; }
    public void setSalary(int salary) { this.salary = salary; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getRequirement() { return requirement; }
    public void setRequirement(String requirement) { this.requirement = requirement; }
    public long getPublishTime() { return publishTime; }
    public void setPublishTime(long publishTime) { this.publishTime = publishTime; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "职位:" + title + " | 公司:" + companyName + " | 薪资:" + salary + " | 地点:" + location + " | 状态:" + status;
    }
}
package model;

import java.io.Serializable;

// 简历数据模型
public class Resume implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String studentId;
    private String name;
    private String education; // 大专,本科,硕士,博士
    private String major;
    private String skill;
    private String expectedPosition;
    private int expectedSalary;

    public Resume() {}

    public Resume(String id, String studentId, String name, String education, String major,
                  String skill, String expectedPosition, int expectedSalary) {
        this.id = id;
        this.studentId = studentId;
        this.name = name;
        this.education = education;
        this.major = major;
        this.skill = skill;
        this.expectedPosition = expectedPosition;
        this.expectedSalary = expectedSalary;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEducation() { return education; }
    public void setEducation(String education) { this.education = education; }
    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }
    public String getSkill() { return skill; }
    public void setSkill(String skill) { this.skill = skill; }
    public String getExpectedPosition() { return expectedPosition; }
    public void setExpectedPosition(String expectedPosition) { this.expectedPosition = expectedPosition; }
    public int getExpectedSalary() { return expectedSalary; }
    public void setExpectedSalary(int expectedSalary) { this.expectedSalary = expectedSalary; }

    @Override
    public String toString() {
        return "姓名:" + name + " | 学历:" + education + " | 专业:" + major + " | 期望职位:" + expectedPosition + " | 期望薪资:" + expectedSalary;
    }
}
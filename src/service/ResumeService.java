package service;

import model.Resume;
import storage.ResumeFile;
import utils.DataStructureUtils;
import java.io.IOException;
import java.util.*;

// 简历管理业务
public class ResumeService {
    private List<Resume> resumes;
    private ResumeFile resumeFile;

    public ResumeService() {
        resumeFile = new ResumeFile();
        loadData();
    }

    private void loadData() {
        try {
            resumes = resumeFile.load();
            if (resumes == null) resumes = new LinkedList<>();
        } catch (IOException e) {
            resumes = new LinkedList<>();
        }
    }

    private void saveData() {
        try {
            resumeFile.save(resumes);
        } catch (IOException e) {
            System.out.println("保存简历数据失败");
        }
    }

    // 发布简历
    public boolean addResume(String studentId, String name, String education, String major,
                             String skill, String expectedPosition, int expectedSalary) {
        String id = "R" + System.currentTimeMillis();
        Resume resume = new Resume(id, studentId, name, education, major, skill, expectedPosition, expectedSalary);
        resumes.add(resume);
        saveData();
        return true;
    }

    // 按学生ID查找简历
    public Resume findByStudentId(String studentId) {
        for (Resume r : resumes) {
            if (r.getStudentId().equals(studentId)) return r;
        }
        return null;
    }

    // 按期望职位查找
    public List<Resume> findByExpectedPosition(String position) {
        List<Resume> result = new ArrayList<>();
        for (Resume r : resumes) {
            if (r.getExpectedPosition().contains(position)) {
                result.add(r);
            }
        }
        return result;
    }

    // 按学历筛选
    public List<Resume> filterByEducation(String education) {
        List<Resume> result = new ArrayList<>();
        for (Resume r : resumes) {
            if (r.getEducation().equals(education)) {
                result.add(r);
            }
        }
        return result;
    }

    // 按期望薪资排序（归并排序）
    public List<Resume> sortByExpectedSalary() {
        List<Resume> sorted = new LinkedList<>(resumes);
        DataStructureUtils.mergeSortBySalary(sorted, 0, sorted.size() - 1);
        return sorted;
    }

    // 二分查找按薪资
    public int binarySearchBySalary(int targetSalary) {
        List<Resume> sorted = sortByExpectedSalary();
        return DataStructureUtils.binarySearchBySalary(sorted, targetSalary);
    }

    // 修改简历
    public boolean updateResume(String studentId, String expectedPosition, Integer expectedSalary) {
        Resume resume = findByStudentId(studentId);
        if (resume == null) return false;
        if (expectedPosition != null && !expectedPosition.isEmpty()) resume.setExpectedPosition(expectedPosition);
        if (expectedSalary != null) resume.setExpectedSalary(expectedSalary);
        saveData();
        return true;
    }
}
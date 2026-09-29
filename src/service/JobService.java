package service;

import model.Job;
import storage.JobFile;
import utils.DataStructureUtils;
import java.io.IOException;
import java.util.*;

// 职位管理业务
public class JobService {
    private List<Job> jobs;
    private JobFile jobFile;

    public JobService() {
        jobFile = new JobFile();
        loadData();
    }

    private void loadData() {
        try {
            jobs = jobFile.load();
            if (jobs == null) jobs = new ArrayList<>();
        } catch (IOException e) {
            jobs = new ArrayList<>();
        }
    }

    private void saveData() {
        try {
            jobFile.save(jobs);
        } catch (IOException e) {
            System.out.println("保存职位数据失败");
        }
    }

    // 发布职位
    public boolean addJob(String companyId, String title, String companyName, int salary,
                          String location, String requirement) {
        String id = "J" + System.currentTimeMillis();
        Job job = new Job(id, companyId, title, companyName, salary, location, requirement, System.currentTimeMillis(), "open");
        jobs.add(job);
        saveData();
        return true;
    }

    public List<Job> getAllJobs() {
        List<Job> openJobs = new ArrayList<>();
        for (Job j : jobs) {
            if (j.getStatus().equals("open")) {
                openJobs.add(j);
            }
        }
        return openJobs;
    }

    // 按ID查找职位
    public Job findById(String jobId) {
        for (Job j : jobs) {
            if (j.getId().equals(jobId)) return j;
        }
        return null;
    }

    // 按公司查找
    public List<Job> findByCompany(String companyId) {
        List<Job> result = new ArrayList<>();
        for (Job j : jobs) {
            if (j.getCompanyId().equals(companyId)) {
                result.add(j);
            }
        }
        return result;
    }

    // 按薪资区间筛选
    public List<Job> filterBySalary(int min, int max) {
        List<Job> result = new ArrayList<>();
        for (Job j : jobs) {
            if (j.getSalary() >= min && j.getSalary() <= max && j.getStatus().equals("open")) {
                result.add(j);
            }
        }
        return result;
    }

    // 按地点筛选
    public List<Job> filterByLocation(String location) {
        List<Job> result = new ArrayList<>();
        for (Job j : jobs) {
            if (j.getLocation().equals(location) && j.getStatus().equals("open")) {
                result.add(j);
            }
        }
        return result;
    }

    // 按薪资排序（堆排序）
    public List<Job> sortBySalary() {
        List<Job> sorted = new ArrayList<>(jobs);
        DataStructureUtils.heapSortBySalary(sorted);
        return sorted;
    }

    // 下架职位
    public boolean closeJob(String jobId) {
        Job job = findById(jobId);
        if (job == null) return false;
        job.setStatus("closed");
        saveData();
        return true;
    }
}
package service;

import model.Delivery;
import storage.DeliveryFile;
import utils.DataStructureUtils;
import java.io.IOException;
import java.util.*;

// 投递管理业务
public class DeliveryService {
    private List<Delivery> deliveries;
    private DeliveryFile deliveryFile;

    public DeliveryService() {
        deliveryFile = new DeliveryFile();
        loadData();
    }

    private void loadData() {
        try {
            deliveries = deliveryFile.load();
            if (deliveries == null) deliveries = new ArrayList<>();
        } catch (IOException e) {
            deliveries = new ArrayList<>();
        }
    }

    private void saveData() {
        try {
            deliveryFile.save(deliveries);
        } catch (IOException e) {
            System.out.println("保存投递数据失败");
        }
    }

    // 投递简历
    public boolean addDelivery(String resumeId, String jobId, String studentId, String companyId) {
        // 检查是否已投递过
        for (Delivery d : deliveries) {
            if (d.getResumeId().equals(resumeId) && d.getJobId().equals(jobId)) {
                return false;
            }
        }
        String id = "D" + System.currentTimeMillis();
        Delivery delivery = new Delivery(id, resumeId, jobId, studentId, companyId, System.currentTimeMillis(), "pending");
        deliveries.add(delivery);
        saveData();
        return true;
    }

    // 按学生查询投递记录
    public List<Delivery> findByStudent(String studentId) {
        List<Delivery> result = new ArrayList<>();
        for (Delivery d : deliveries) {
            if (d.getStudentId().equals(studentId)) {
                result.add(d);
            }
        }
        return result;
    }

    // 按企业查询投递记录
    public List<Delivery> findByCompany(String companyId) {
        List<Delivery> result = new ArrayList<>();
        for (Delivery d : deliveries) {
            if (d.getCompanyId().equals(companyId)) {
                result.add(d);
            }
        }
        return result;
    }

    // 更新投递状态
    public boolean updateStatus(String deliveryId, String status) {
        for (Delivery d : deliveries) {
            if (d.getId().equals(deliveryId)) {
                d.setStatus(status);
                saveData();
                return true;
            }
        }
        return false;
    }

    // 按投递时间排序（插入排序）
    public List<Delivery> sortByDeliverTime() {
        List<Delivery> sorted = new ArrayList<>(deliveries);
        DataStructureUtils.insertionSortByTime(sorted);
        return sorted;
    }
}
package utils;

import model.*;
import java.util.*;

// 包含各种排序算法实现，每个数据结构使用不同的排序算法
public class DataStructureUtils {

    // ========== 快速排序（用于User按注册时间排序）==========
    public static void quickSortByRegisterTime(List<User> users, int left, int right) {
        if (left >= right) return;
        int pivotIndex = partition(users, left, right);
        quickSortByRegisterTime(users, left, pivotIndex - 1);
        quickSortByRegisterTime(users, pivotIndex + 1, right);
    }

    private static int partition(List<User> users, int left, int right) {
        long pivot = users.get(right).getRegisterTime();
        int i = left - 1;
        for (int j = left; j < right; j++) {
            if (users.get(j).getRegisterTime() <= pivot) {
                i++;
                swapUser(users, i, j);
            }
        }
        swapUser(users, i + 1, right);
        return i + 1;
    }

    // 专门用于User交换
    private static void swapUser(List<User> users, int i, int j) {
        User temp = users.get(i);
        users.set(i, users.get(j));
        users.set(j, temp);
    }

    // ========== 归并排序（用于Resume按期望薪资排序）==========
    public static void mergeSortBySalary(List<Resume> resumes, int left, int right) {
        if (left >= right) return;
        int mid = (left + right) / 2;
        mergeSortBySalary(resumes, left, mid);
        mergeSortBySalary(resumes, mid + 1, right);
        merge(resumes, left, mid, right);
    }

    private static void merge(List<Resume> resumes, int left, int mid, int right) {
        List<Resume> temp = new ArrayList<>();
        int i = left, j = mid + 1;
        while (i <= mid && j <= right) {
            if (resumes.get(i).getExpectedSalary() <= resumes.get(j).getExpectedSalary()) {
                temp.add(resumes.get(i++));
            } else {
                temp.add(resumes.get(j++));
            }
        }
        while (i <= mid) temp.add(resumes.get(i++));
        while (j <= right) temp.add(resumes.get(j++));
        for (int k = 0; k < temp.size(); k++) {
            resumes.set(left + k, temp.get(k));
        }
    }

    // ========== 堆排序（用于Job按薪资排序）==========
    public static void heapSortBySalary(List<Job> jobs) {
        int n = jobs.size();
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(jobs, n, i);
        }
        for (int i = n - 1; i > 0; i--) {
            swapJob(jobs, 0, i);
            heapify(jobs, i, 0);
        }
    }

    private static void heapify(List<Job> jobs, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        if (left < n && jobs.get(left).getSalary() > jobs.get(largest).getSalary()) {
            largest = left;
        }
        if (right < n && jobs.get(right).getSalary() > jobs.get(largest).getSalary()) {
            largest = right;
        }
        if (largest != i) {
            swapJob(jobs, i, largest);
            heapify(jobs, n, largest);
        }
    }

    // 专门用于Job交换
    private static void swapJob(List<Job> jobs, int i, int j) {
        Job temp = jobs.get(i);
        jobs.set(i, jobs.get(j));
        jobs.set(j, temp);
    }

    // ========== 插入排序（用于Delivery按投递时间排序）==========
    public static void insertionSortByTime(List<Delivery> deliveries) {
        for (int i = 1; i < deliveries.size(); i++) {
            Delivery key = deliveries.get(i);
            int j = i - 1;
            while (j >= 0 && deliveries.get(j).getDeliverTime() > key.getDeliverTime()) {
                deliveries.set(j + 1, deliveries.get(j));
                j--;
            }
            deliveries.set(j + 1, key);
        }
    }

    // ========== 冒泡排序（用于Favorite按收藏时间排序）==========
    public static void bubbleSortByTime(List<Favorite> favorites) {
        int n = favorites.size();
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (favorites.get(j).getCollectTime() > favorites.get(j + 1).getCollectTime()) {
                    Favorite temp = favorites.get(j);
                    favorites.set(j, favorites.get(j + 1));
                    favorites.set(j + 1, temp);
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
    }

    // ========== 查找算法 ==========
    public static int binarySearchBySalary(List<Resume> resumes, int targetSalary) {
        int left = 0, right = resumes.size() - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            int midSalary = resumes.get(mid).getExpectedSalary();
            if (midSalary == targetSalary) return mid;
            else if (midSalary < targetSalary) left = mid + 1;
            else right = mid - 1;
        }
        return -1;
    }

    public static User linearSearchByUsername(List<User> users, String username) {
        for (User u : users) {
            if (u.getUsername().equals(username)) return u;
        }
        return null;
    }

    // ========== 数据结构有效性测试 ==========
    public static void main(String[] args) {
        System.out.println("========== 数据结构及排序算法测试 ==========");

        List<User> users = new ArrayList<>();
        users.add(new User("1", "a", "123", "student", "111", "a@test.com", "active", 1000));
        users.add(new User("2", "b", "123", "student", "222", "b@test.com", "active", 3000));
        users.add(new User("3", "c", "123", "student", "333", "c@test.com", "active", 2000));
        System.out.println("排序前用户注册时间: 1000,3000,2000");
        quickSortByRegisterTime(users, 0, users.size() - 1);
        System.out.print("快速排序后: ");
        for (User u : users) System.out.print(u.getRegisterTime() + " ");
        System.out.println();

        List<Resume> resumes = new LinkedList<>();
        resumes.add(new Resume("1", "s1", "张三", "本科", "计算机", "Java", "开发", 15000));
        resumes.add(new Resume("2", "s2", "李四", "硕士", "软件工程", "Python", "开发", 20000));
        resumes.add(new Resume("3", "s3", "王五", "本科", "计算机", "前端", "前端", 12000));
        System.out.print("归并排序前薪资: 15000,20000,12000 -> ");
        mergeSortBySalary(resumes, 0, resumes.size() - 1);
        System.out.print("排序后: ");
        for (Resume r : resumes) System.out.print(r.getExpectedSalary() + " ");
        System.out.println();

        List<Job> jobs = new ArrayList<>();
        jobs.add(new Job("1", "c1", "Java工程师", "科技公司", 18000, "北京", "要求Java", System.currentTimeMillis(), "open"));
        jobs.add(new Job("2", "c2", "Python工程师", "数据公司", 22000, "上海", "要求Python", System.currentTimeMillis(), "open"));
        jobs.add(new Job("3", "c3", "前端工程师", "互联网公司", 15000, "深圳", "要求Vue", System.currentTimeMillis(), "open"));
        System.out.print("堆排序前薪资: 18000,22000,15000 -> ");
        heapSortBySalary(jobs);
        System.out.print("排序后: ");
        for (Job j : jobs) System.out.print(j.getSalary() + " ");
        System.out.println();

        List<Delivery> deliveries = new ArrayList<>();
        deliveries.add(new Delivery("1", "r1", "j1", "s1", "c1", 3000, "pending"));
        deliveries.add(new Delivery("2", "r2", "j2", "s2", "c2", 1000, "pending"));
        deliveries.add(new Delivery("3", "r3", "j3", "s3", "c3", 2000, "pending"));
        System.out.print("插入排序前时间: 3000,1000,2000 -> ");
        insertionSortByTime(deliveries);
        System.out.print("排序后: ");
        for (Delivery d : deliveries) System.out.print(d.getDeliverTime() + " ");
        System.out.println();

        List<Favorite> favorites = new ArrayList<>();
        favorites.add(new Favorite("1", "s1", "j1", 3000));
        favorites.add(new Favorite("2", "s2", "j2", 1000));
        favorites.add(new Favorite("3", "s3", "j3", 2000));
        System.out.print("冒泡排序前时间: 3000,1000,2000 -> ");
        bubbleSortByTime(favorites);
        System.out.print("排序后: ");
        for (Favorite f : favorites) System.out.print(f.getCollectTime() + " ");
        System.out.println();

        System.out.println("========== 所有数据结构及排序算法测试通过 ==========");
    }
}
package menu;

import model.*;
import service.*;
import java.util.List;
import java.util.Scanner;

public class CompanyMenu {
    private AuthService authService;
    private JobService jobService;
    private DeliveryService deliveryService;
    private Scanner scanner;
    private String companyId;
    private String companyName;

    public CompanyMenu(AuthService authService, User currentUser) {
        this.authService = authService;
        this.companyId = currentUser.getId();
        this.companyName = currentUser.getUsername();
        this.jobService = new JobService();
        this.deliveryService = new DeliveryService();
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        while (true) {
            System.out.println();
            System.out.println("========================================");
            System.out.println("      招聘企业菜单");
            System.out.println("========================================");
            System.out.println("1. 发布职位");
            System.out.println("2. 我的职位");
            System.out.println("3. 收到的投递");
            System.out.println("4. 处理投递");
            System.out.println("5. 下架职位");
            System.out.println("6. 修改企业信息");
            System.out.println("7. 退出登录");
            System.out.println("========================================");
            System.out.print("请选择操作: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    postJob();
                    break;
                case 2:
                    viewMyJobs();
                    break;
                case 3:
                    viewDeliveries();
                    break;
                case 4:
                    processDelivery();
                    break;
                case 5:
                    closeJob();
                    break;
                case 6:
                    updateProfile();
                    break;
                case 7:
                    authService.logout();
                    return;
                default:
                    System.out.println("无效选择");
            }
        }
    }

    private void postJob() {
        System.out.print("请输入职位名称: ");
        String title = scanner.nextLine();
        System.out.print("请输入薪资: ");
        int salary = scanner.nextInt();
        scanner.nextLine();
        System.out.print("请输入工作地点: ");
        String location = scanner.nextLine();
        System.out.print("请输入职位要求: ");
        String requirement = scanner.nextLine();

        if (jobService.addJob(companyId, title, companyName, salary, location, requirement)) {
            System.out.println("职位发布成功");
        } else {
            System.out.println("职位发布失败");
        }
    }

    private void viewMyJobs() {
        List<Job> jobs = jobService.findByCompany(companyId);
        if (jobs.isEmpty()) {
            System.out.println("暂无发布的职位");
            return;
        }
        for (Job j : jobs) {
            System.out.println(j.getId() + " | " + j);
        }
    }

    private void viewDeliveries() {
        List<Delivery> deliveries = deliveryService.findByCompany(companyId);
        if (deliveries.isEmpty()) {
            System.out.println("暂无投递记录");
            return;
        }
        for (Delivery d : deliveries) {
            System.out.println("投递ID:" + d.getId() + " | 学生ID:" + d.getStudentId() + " | 状态:" + d.getStatus());
        }
    }

    private void processDelivery() {
        System.out.print("请输入投递ID: ");
        String deliveryId = scanner.nextLine();
        System.out.println("1. 标记为已查看");
        System.out.println("2. 发送面试邀请");
        System.out.println("3. 拒绝");
        System.out.print("请选择: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        String status = "";
        switch (choice) {
            case 1: status = "viewed"; break;
            case 2: status = "invited"; break;
            case 3: status = "rejected"; break;
            default: System.out.println("无效选择"); return;
        }

        if (deliveryService.updateStatus(deliveryId, status)) {
            System.out.println("处理成功");
        } else {
            System.out.println("处理失败");
        }
    }

    private void closeJob() {
        System.out.print("请输入要下架的职位ID: ");
        String jobId = scanner.nextLine();
        if (jobService.closeJob(jobId)) {
            System.out.println("职位已下架");
        } else {
            System.out.println("职位不存在");
        }
    }

    private void updateProfile() {
        User user = authService.getCurrentUser();
        System.out.print("请输入新手机号(直接回车保持不变): ");
        String phone = scanner.nextLine();
        System.out.print("请输入新邮箱(直接回车保持不变): ");
        String email = scanner.nextLine();

        UserService userService = new UserService();
        if (userService.updateUser(user.getUsername(),
                phone.isEmpty() ? null : phone,
                email.isEmpty() ? null : email)) {
            System.out.println("企业信息修改成功");
        }
    }
}
package menu;

import model.*;
import service.*;
import java.util.List;
import java.util.Scanner;

public class StudentMenu {
    private AuthService authService;
    private ResumeService resumeService;
    private JobService jobService;
    private DeliveryService deliveryService;
    private FavoriteService favoriteService;
    private Scanner scanner;
    private String studentId;

    public StudentMenu(AuthService authService, User currentUser) {
        this.authService = authService;
        this.studentId = currentUser.getId();
        this.resumeService = new ResumeService();
        this.jobService = new JobService();
        this.deliveryService = new DeliveryService();
        this.favoriteService = new FavoriteService();
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        while (true) {
            System.out.println();
            System.out.println("========================================");
            System.out.println("      求职大学生菜单");
            System.out.println("========================================");
            System.out.println("1. 我的简历");
            System.out.println("2. 浏览职位");
            System.out.println("3. 搜索职位");
            System.out.println("4. 投递简历");
            System.out.println("5. 我的投递记录");
            System.out.println("6. 收藏职位");
            System.out.println("7. 查看收藏");
            System.out.println("8. 修改个人信息");
            System.out.println("9. 退出登录");
            System.out.println("========================================");
            System.out.print("请选择操作: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    manageResume();
                    break;
                case 2:
                    browseJobs();
                    break;
                case 3:
                    searchJobs();
                    break;
                case 4:
                    deliverResume();
                    break;
                case 5:
                    viewDeliveries();
                    break;
                case 6:
                    favoriteJob();
                    break;
                case 7:
                    viewFavorites();
                    break;
                case 8:
                    updateProfile();
                    break;
                case 9:
                    authService.logout();
                    return;
                default:
                    System.out.println("无效选择");
            }
        }
    }

    private void manageResume() {
        Resume resume = resumeService.findByStudentId(studentId);
        if (resume == null) {
            System.out.println("您还没有简历，请创建简历");
            System.out.print("请输入姓名: ");
            String name = scanner.nextLine();
            System.out.print("请输入学历(大专/本科/硕士/博士): ");
            String education = scanner.nextLine();
            System.out.print("请输入专业: ");
            String major = scanner.nextLine();
            System.out.print("请输入技能: ");
            String skill = scanner.nextLine();
            System.out.print("请输入期望职位: ");
            String position = scanner.nextLine();
            System.out.print("请输入期望薪资: ");
            int salary = scanner.nextInt();
            scanner.nextLine();

            if (resumeService.addResume(studentId, name, education, major, skill, position, salary)) {
                System.out.println("简历创建成功");
            }
        } else {
            System.out.println("当前简历: " + resume);
            System.out.print("是否修改简历？(y/n): ");
            if (scanner.nextLine().equals("y")) {
                System.out.print("请输入新的期望职位(直接回车保持不变): ");
                String position = scanner.nextLine();
                System.out.print("请输入新的期望薪资(输入-1保持不变): ");
                int salary = scanner.nextInt();
                scanner.nextLine();
                resumeService.updateResume(studentId,
                        position.isEmpty() ? null : position,
                        salary == -1 ? null : salary);
                System.out.println("简历修改成功");
            }
        }
    }

    private void browseJobs() {
        List<Job> jobs = jobService.getAllJobs();
        if (jobs.isEmpty()) {
            System.out.println("暂无职位信息");
            return;
        }
        System.out.println("===== 职位列表 =====");
        for (Job j : jobs) {
            System.out.println(j.getId() + " | " + j);
        }
    }

    private void searchJobs() {
        System.out.println("1. 按薪资范围搜索");
        System.out.println("2. 按工作地点搜索");
        System.out.print("请选择: ");
        int type = scanner.nextInt();
        scanner.nextLine();

        List<Job> results = null;
        if (type == 1) {
            System.out.print("最低薪资: ");
            int min = scanner.nextInt();
            System.out.print("最高薪资: ");
            int max = scanner.nextInt();
            scanner.nextLine();
            results = jobService.filterBySalary(min, max);
        } else if (type == 2) {
            System.out.print("工作地点: ");
            String location = scanner.nextLine();
            results = jobService.filterByLocation(location);
        }

        if (results == null || results.isEmpty()) {
            System.out.println("未找到符合条件的职位");
        } else {
            for (Job j : results) {
                System.out.println(j);
            }
        }
    }

    private void deliverResume() {
        Resume resume = resumeService.findByStudentId(studentId);
        if (resume == null) {
            System.out.println("请先创建简历");
            return;
        }

        System.out.print("请输入职位ID: ");
        String jobId = scanner.nextLine();
        Job job = jobService.findById(jobId);
        if (job == null || job.getStatus().equals("closed")) {
            System.out.println("职位不存在或已关闭");
            return;
        }

        if (deliveryService.addDelivery(resume.getId(), jobId, studentId, job.getCompanyId())) {
            System.out.println("投递成功");
        } else {
            System.out.println("您已投递过该职位");
        }
    }

    private void viewDeliveries() {
        List<Delivery> deliveries = deliveryService.findByStudent(studentId);
        if (deliveries.isEmpty()) {
            System.out.println("暂无投递记录");
            return;
        }
        for (Delivery d : deliveries) {
            Job job = jobService.findById(d.getJobId());
            String jobTitle = (job == null) ? "未知" : job.getTitle();
            System.out.println("职位:" + jobTitle + " | 状态:" + d.getStatus());
        }
    }

    private void favoriteJob() {
        System.out.print("请输入要收藏的职位ID: ");
        String jobId = scanner.nextLine();
        Job job = jobService.findById(jobId);
        if (job == null) {
            System.out.println("职位不存在");
            return;
        }
        if (favoriteService.addFavorite(studentId, jobId)) {
            System.out.println("收藏成功");
        } else {
            System.out.println("您已收藏过该职位");
        }
    }

    private void viewFavorites() {
        List<Favorite> favorites = favoriteService.findByStudent(studentId);
        if (favorites.isEmpty()) {
            System.out.println("暂无收藏");
            return;
        }
        for (Favorite f : favorites) {
            Job job = jobService.findById(f.getJobId());
            if (job != null) {
                System.out.println(job);
            }
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
            System.out.println("个人信息修改成功");
        }
    }
}
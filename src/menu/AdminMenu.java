package menu;

import model.*;
import service.*;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {
    private AuthService authService;
    private UserService userService;
    private JobService jobService;
    private Scanner scanner;

    public AdminMenu(AuthService authService, User currentUser) {
        this.authService = authService;
        this.userService = new UserService();
        this.jobService = new JobService();
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        while (true) {
            System.out.println();
            System.out.println("========================================");
            System.out.println("      系统管理员菜单");
            System.out.println("========================================");
            System.out.println("1. 查看所有用户");
            System.out.println("2. 按角色查看用户");
            System.out.println("3. 按注册时间排序");
            System.out.println("4. 启用/禁用用户");
            System.out.println("5. 查看所有职位");
            System.out.println("6. 下架违规职位");
            System.out.println("7. 查看系统统计");
            System.out.println("8. 退出登录");
            System.out.println("========================================");
            System.out.print("请选择操作: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    viewAllUsers();
                    break;
                case 2:
                    viewUsersByRole();
                    break;
                case 3:
                    sortUsersByTime();
                    break;
                case 4:
                    toggleUserStatus();
                    break;
                case 5:
                    viewAllJobs();
                    break;
                case 6:
                    closeJob();
                    break;
                case 7:
                    viewStatistics();
                    break;
                case 8:
                    authService.logout();
                    return;
                default:
                    System.out.println("无效选择");
            }
        }
    }

    private void viewAllUsers() {
        List<User> users = userService.getAllUsers();
        for (User u : users) {
            System.out.println(u);
        }
    }

    private void viewUsersByRole() {
        System.out.println("1. 求职大学生");
        System.out.println("2. 招聘企业");
        System.out.println("3. 管理员");
        System.out.print("请选择: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        String role = "";
        if (choice == 1) role = "student";
        else if (choice == 2) role = "company";
        else if (choice == 3) role = "admin";
        else return;

        List<User> users = userService.findUsersByRole(role);
        for (User u : users) {
            System.out.println(u);
        }
    }

    private void sortUsersByTime() {
        List<User> sorted = userService.sortByRegisterTime();
        System.out.println("===== 按注册时间排序（从早到晚） =====");
        for (User u : sorted) {
            String timeStr = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(u.getRegisterTime()));
            System.out.println(u.getUsername() + " | " + u.getRole() + " | " + timeStr);
        }
    }

    private void toggleUserStatus() {
        System.out.print("请输入用户名: ");
        String username = scanner.nextLine();
        User user = userService.findUserByUsername(username);
        if (user == null) {
            System.out.println("用户不存在");
            return;
        }
        String newStatus = user.getStatus().equals("active") ? "disabled" : "active";
        if (userService.setUserStatus(username, newStatus)) {
            System.out.println("用户状态已修改为: " + newStatus);
        }
    }

    private void viewAllJobs() {
        List<Job> jobs = jobService.getAllJobs();
        for (Job j : jobs) {
            System.out.println(j);
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

    private void viewStatistics() {
        List<User> students = userService.findUsersByRole("student");
        List<User> companies = userService.findUsersByRole("company");
        List<Job> jobs = jobService.getAllJobs();

        System.out.println("===== 系统统计 =====");
        System.out.println("求职大学生数量: " + students.size());
        System.out.println("招聘企业数量: " + companies.size());
        System.out.println("职位总数: " + jobs.size());
    }
}
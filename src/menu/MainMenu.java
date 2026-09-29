package menu;

import service.AuthService;
import model.User;
import java.util.Scanner;

public class MainMenu {
    private final AuthService authService;
    private final Scanner scanner;

    public MainMenu() {
        authService = new AuthService();
        scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            System.out.println();
            System.out.println("========================================");
            System.out.println("      大学生求职系统");
            System.out.println("========================================");
            System.out.println("1. 登录");
            System.out.println("2. 注册");
            System.out.println("3. 退出系统");
            System.out.println("========================================");
            System.out.print("请选择操作: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // 消费换行符

            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                case 3:
                    System.out.println("感谢使用大学生求职系统，再见！");
                    return;
                default:
                    System.out.println("无效选择，请重新输入");
            }
        }
    }

    private void login() {
        System.out.print("请输入用户名: ");
        String username = scanner.nextLine();
        System.out.print("请输入密码: ");
        String password = scanner.nextLine();

        if (authService.login(username, password)) {
            User currentUser = authService.getCurrentUser();
            System.out.println("登录成功！欢迎 " + username);
            String role = currentUser.getRole();
            switch (role) {
                case "student":
                    StudentMenu studentMenu = new StudentMenu(authService, currentUser);
                    studentMenu.showMenu();
                    break;
                case "company":
                    CompanyMenu companyMenu = new CompanyMenu(authService, currentUser);
                    companyMenu.showMenu();
                    break;
                case "admin":
                    AdminMenu adminMenu = new AdminMenu(authService, currentUser);
                    adminMenu.showMenu();
                    break;
                default:
                    System.out.println("未知角色");
            }
        } else {
            System.out.println("用户名或密码错误");
        }
    }

    private void register() {
        System.out.println("===== 注册新账号 =====");
        System.out.print("请选择角色 (1.求职大学生 2.招聘企业 3.管理员): ");
        int roleChoice = scanner.nextInt();
        scanner.nextLine(); // 消费换行符

        String role;
        switch (roleChoice) {
            case 1:
                role = "student";
                break;
            case 2:
                role = "company";
                break;
            case 3:
                role = "admin";
                break;
            default:
                System.out.println("无效角色选择，注册失败");
                return;
        }

        System.out.print("请输入用户名: ");
        String username = scanner.nextLine();
        System.out.print("请输入密码: ");
        String password = scanner.nextLine();
        System.out.print("请输入手机号: ");
        String phone = scanner.nextLine();
        System.out.print("请输入邮箱: ");
        String email = scanner.nextLine();

        if (authService.register(username, password, role, phone, email)) {
            System.out.println("注册成功！请返回主菜单登录。");
        } else {
            System.out.println("用户名已存在，注册失败。");
        }
    }
}
package service;

import model.User;
import storage.UserFile;
import java.io.IOException;
import java.util.*;

public class AuthService {
    private List<User> users;
    private UserFile userFile;
    private User currentUser;

    public AuthService() {
        userFile = new UserFile();
        loadData();
    }

    private void loadData() {
        try {
            users = userFile.load();
            if (users == null) {
                users = new ArrayList<>();
            }
            // 如果用户数量太少（比如少于5个），则补充更多测试用户
            if (users.size() < 5) {
                addMoreTestUsers();
            }
        } catch (IOException e) {
            users = new ArrayList<>();
            addMoreTestUsers();
        }
    }

    // 补充更多测试用户（保留已有用户）
    private void addMoreTestUsers() {
        System.out.println("当前用户数量较少，正在补充测试数据...");
        long now = System.currentTimeMillis();
        int added = 0;

        // 补充学生（8个）
        String[] studentNames = {"zhangwei", "liujing", "huangming", "zhaoling", "zhoujie", "wuqian", "sunpeng", "linfeng"};
        for (int i = 0; i < studentNames.length; i++) {
            String username = studentNames[i];
            // 检查用户名是否已存在
            boolean exists = users.stream().anyMatch(u -> u.getUsername().equals(username));
            if (!exists) {
                String id = "S" + System.currentTimeMillis() + i;
                String phone = "138" + String.format("%08d", (int)(Math.random() * 100000000));
                String email = username + "@test.com";
                users.add(new User(id, username, "123", "student", phone, email, "active", now + i));
                added++;
            }
        }

        // 补充企业（6个）
        String[] companyNames = {"huawei", "zte", "netease", "sohu", "sina", "pdd"};
        for (int i = 0; i < companyNames.length; i++) {
            String username = companyNames[i] + "_company";
            boolean exists = users.stream().anyMatch(u -> u.getUsername().equals(username));
            if (!exists) {
                String id = "C" + System.currentTimeMillis() + i;
                String phone = "139" + String.format("%08d", (int)(Math.random() * 100000000));
                String email = username + "@company.com";
                users.add(new User(id, username, "123", "company", phone, email, "active", now + 100 + i));
                added++;
            }
        }

        // 再补充一个额外的管理员（可选）
        if (users.stream().noneMatch(u -> u.getUsername().equals("admin2"))) {
            users.add(new User("A" + System.currentTimeMillis(), "admin2", "123", "admin", "13700000000", "admin2@job.com", "active", now + 200));
            added++;
        }

        if (added > 0) {
            saveData();
            System.out.println("成功补充了 " + added + " 个测试用户。当前总用户数：" + users.size());
            System.out.println("新用户已追加，原有用户保持不变。");
        } else {
            System.out.println("用户数量已充足，无需补充。");
        }
    }

    private void saveData() {
        try {
            userFile.save(users);
        } catch (IOException e) {
            System.out.println("保存用户数据失败");
        }
    }

    // 注册、登录、getCurrentUser、logout 方法保持不变
    public boolean register(String username, String password, String role, String phone, String email) {
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return false;
            }
        }
        String id = "U" + System.currentTimeMillis();
        User newUser = new User(id, username, password, role, phone, email, "active", System.currentTimeMillis());
        users.add(newUser);
        saveData();
        return true;
    }

    public boolean login(String username, String password) {
        for (User u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                if (u.getStatus().equals("disabled")) {
                    System.out.println("账号已被禁用");
                    return false;
                }
                currentUser = u;
                return true;
            }
        }
        return false;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void logout() {
        currentUser = null;
    }
}
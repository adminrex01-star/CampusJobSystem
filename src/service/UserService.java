package service;

import model.User;
import storage.UserFile;
import utils.DataStructureUtils;
import java.io.IOException;
import java.util.*;

// 用户管理业务
public class UserService {
    private List<User> users;
    private UserFile userFile;

    public UserService() {
        userFile = new UserFile();
        loadData();
    }

    private void loadData() {
        try {
            users = userFile.load();
            if (users == null) users = new ArrayList<>();
        } catch (IOException e) {
            users = new ArrayList<>();
        }
    }

    private void saveData() {
        try {
            userFile.save(users);
        } catch (IOException e) {
            System.out.println("保存用户数据失败");
        }
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    // 按角色查找
    public List<User> findUsersByRole(String role) {
        List<User> result = new ArrayList<>();
        for (User u : users) {
            if (u.getRole().equals(role)) {
                result.add(u);
            }
        }
        return result;
    }

    // 按用户名线性查找
    public User findUserByUsername(String username) {
        return DataStructureUtils.linearSearchByUsername(users, username);
    }

    // 按注册时间排序（快速排序）
    public List<User> sortByRegisterTime() {
        List<User> sorted = new ArrayList<>(users);
        DataStructureUtils.quickSortByRegisterTime(sorted, 0, sorted.size() - 1);
        return sorted;
    }

    // 启用/禁用用户
    public boolean setUserStatus(String username, String status) {
        User user = findUserByUsername(username);
        if (user == null) return false;
        user.setStatus(status);
        saveData();
        return true;
    }

    // 修改用户信息
    public boolean updateUser(String username, String phone, String email) {
        User user = findUserByUsername(username);
        if (user == null) return false;
        if (phone != null && !phone.isEmpty()) user.setPhone(phone);
        if (email != null && !email.isEmpty()) user.setEmail(email);
        saveData();
        return true;
    }
}
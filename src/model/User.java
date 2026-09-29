package model;

import java.io.Serializable;

// 用户数据模型，实现Serializable支持文件存储
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String username;
    private String password;
    private String role; // student, company, admin
    private String phone;
    private String email;
    private String status; // active, disabled
    private long registerTime;

    public User() {}

    public User(String id, String username, String password, String role, String phone, String email, String status, long registerTime) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.phone = phone;
        this.email = email;
        this.status = status;
        this.registerTime = registerTime;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public long getRegisterTime() { return registerTime; }
    public void setRegisterTime(long registerTime) { this.registerTime = registerTime; }

    @Override
    public String toString() {
        return "用户ID:" + id + " | 用户名:" + username + " | 角色:" + role + " | 状态:" + status;
    }
}
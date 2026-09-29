package service;

import model.Favorite;
import storage.FavoriteFile;
import utils.DataStructureUtils;
import java.io.IOException;
import java.util.*;

// 收藏管理业务
public class FavoriteService {
    private List<Favorite> favorites;
    private FavoriteFile favoriteFile;

    public FavoriteService() {
        favoriteFile = new FavoriteFile();
        loadData();
    }

    private void loadData() {
        try {
            favorites = favoriteFile.load();
            if (favorites == null) favorites = new ArrayList<>();
        } catch (IOException e) {
            favorites = new ArrayList<>();
        }
    }

    private void saveData() {
        try {
            favoriteFile.save(favorites);
        } catch (IOException e) {
            System.out.println("保存收藏数据失败");
        }
    }

    // 添加收藏
    public boolean addFavorite(String studentId, String jobId) {
        // 检查是否已收藏
        for (Favorite f : favorites) {
            if (f.getStudentId().equals(studentId) && f.getJobId().equals(jobId)) {
                return false;
            }
        }
        String id = "F" + System.currentTimeMillis();
        Favorite favorite = new Favorite(id, studentId, jobId, System.currentTimeMillis());
        favorites.add(favorite);
        saveData();
        return true;
    }

    // 取消收藏
    public boolean removeFavorite(String studentId, String jobId) {
        Iterator<Favorite> it = favorites.iterator();
        while (it.hasNext()) {
            Favorite f = it.next();
            if (f.getStudentId().equals(studentId) && f.getJobId().equals(jobId)) {
                it.remove();
                saveData();
                return true;
            }
        }
        return false;
    }

    // 按学生查询收藏
    public List<Favorite> findByStudent(String studentId) {
        List<Favorite> result = new ArrayList<>();
        for (Favorite f : favorites) {
            if (f.getStudentId().equals(studentId)) {
                result.add(f);
            }
        }
        return result;
    }

    // 检查是否已收藏
    public boolean isFavorited(String studentId, String jobId) {
        for (Favorite f : favorites) {
            if (f.getStudentId().equals(studentId) && f.getJobId().equals(jobId)) {
                return true;
            }
        }
        return false;
    }

    // 按收藏时间排序（冒泡排序）
    public List<Favorite> sortByCollectTime() {
        List<Favorite> sorted = new ArrayList<>(favorites);
        DataStructureUtils.bubbleSortByTime(sorted);
        return sorted;
    }
}
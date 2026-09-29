package storage;

import java.io.*;
import java.util.*;

// 文件存储接口，定义统一的读写方法
public interface FileStorage<T> {
    void save(List<T> data) throws IOException;
    List<T> load() throws IOException;
}

// 通用文件操作工具类
class FileUtil {
    private static final String DATA_DIR = "data/";

    static {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) dir.mkdirs();
    }

    public static <T> void writeToFile(String fileName, List<T> data) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_DIR + fileName))) {
            oos.writeObject(data);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> readFromFile(String fileName) throws IOException, ClassNotFoundException {
        File file = new File(DATA_DIR + fileName);
        if (!file.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<T>) ois.readObject();
        }
    }
}
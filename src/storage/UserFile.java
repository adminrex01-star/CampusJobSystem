package storage;

import model.User;
import java.io.IOException;
import java.util.*;

public class UserFile implements FileStorage<User> {
    private static final String FILE_NAME = "users.dat";

    @Override
    public void save(List<User> users) throws IOException {
        FileUtil.writeToFile(FILE_NAME, users);
    }

    @Override
    public List<User> load() throws IOException {
        try {
            return FileUtil.readFromFile(FILE_NAME);
        } catch (ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }
}
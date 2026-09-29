package storage;

import model.Job;
import java.io.IOException;
import java.util.*;

public class JobFile implements FileStorage<Job> {
    private static final String FILE_NAME = "jobs.dat";

    @Override
    public void save(List<Job> jobs) throws IOException {
        FileUtil.writeToFile(FILE_NAME, jobs);
    }

    @Override
    public List<Job> load() throws IOException {
        try {
            return FileUtil.readFromFile(FILE_NAME);
        } catch (ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }
}
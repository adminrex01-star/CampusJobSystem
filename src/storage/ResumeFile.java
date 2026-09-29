package storage;

import model.Resume;
import java.io.IOException;
import java.util.*;

public class ResumeFile implements FileStorage<Resume> {
    private static final String FILE_NAME = "resumes.dat";

    @Override
    public void save(List<Resume> resumes) throws IOException {
        FileUtil.writeToFile(FILE_NAME, resumes);
    }

    @Override
    public List<Resume> load() throws IOException {
        try {
            return FileUtil.readFromFile(FILE_NAME);
        } catch (ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }
}
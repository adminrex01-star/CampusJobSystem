package storage;

import model.Favorite;
import java.io.IOException;
import java.util.*;

public class FavoriteFile implements FileStorage<Favorite> {
    private static final String FILE_NAME = "favorites.dat";

    @Override
    public void save(List<Favorite> favorites) throws IOException {
        FileUtil.writeToFile(FILE_NAME, favorites);
    }

    @Override
    public List<Favorite> load() throws IOException {
        try {
            return FileUtil.readFromFile(FILE_NAME);
        } catch (ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }
}
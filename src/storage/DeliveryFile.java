package storage;

import model.Delivery;
import java.io.IOException;
import java.util.*;

public class DeliveryFile implements FileStorage<Delivery> {
    private static final String FILE_NAME = "deliveries.dat";

    @Override
    public void save(List<Delivery> deliveries) throws IOException {
        FileUtil.writeToFile(FILE_NAME, deliveries);
    }

    @Override
    public List<Delivery> load() throws IOException {
        try {
            return FileUtil.readFromFile(FILE_NAME);
        } catch (ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }
}
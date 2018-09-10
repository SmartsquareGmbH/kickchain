package de.smartsquare.kickchain.filedb;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FileObjectStore<T> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${fileobjectstore.basedir}")
    private String basedir;

    private final String type;
    private final Class clazz;

    public FileObjectStore(String type, Class clazz) {
        this.type = type;
        this.clazz = clazz;
    }

    public void store(String id, T element) {
        try {
            objectMapper.writeValue(getFile(id), element);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Optional<T> read(String id) {
        return Optional.of(readObjectFromFile(getFile(id), clazz));
    }

    public List<T> readAll() {
        File[] files = getDirectory().listFiles((f, s) -> s.endsWith(".json"));
        return Arrays.asList(files).stream()
                .map(f -> readObjectFromFile(f, clazz))
                .collect(Collectors.toList());
    }

    private T readObjectFromFile(File f, Class clazz) {
        try {
            return (T) objectMapper.readValue(f, clazz);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private File getFile(String id) {
        String filename = String.format("%s/%s.json", getDirectory().getName(), id);
        return new File(filename);
    }

    private File getDirectory() {
        String dirname = String.format("%s/%s", basedir, type);
        return new File(dirname);
    }

}

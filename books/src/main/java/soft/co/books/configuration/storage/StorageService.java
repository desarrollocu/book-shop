package soft.co.books.configuration.storage;

import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class StorageService {

    private final Environment env;
    private final Path rootLocation;

    public StorageService(Environment env) {
        this.env = env;
        this.rootLocation = Paths.get(this.env.getProperty("storage.location"));
    }

    public void store(MultipartFile file, String id) {
        try {
            Path tem = rootLocation.resolve(id);
            if (Files.exists(tem))
                FileSystemUtils.deleteRecursively(tem.toFile());
            Files.createDirectory(tem);
            Files.copy(file.getInputStream(), tem.resolve(file.getOriginalFilename()));
        } catch (Exception e) {
            throw new RuntimeException("FAIL!");
        }
    }

    public Resource loadFile(String filename, String id) {
        try {
            Path file = rootLocation.resolve(id);
            Path temp = file.resolve(filename);
            Resource resource = new UrlResource(temp.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                return null;
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("FAIL!");
        }
    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    public void deleteById(String id) {
        Path tem = rootLocation.resolve(id);
        if (Files.exists(tem))
            FileSystemUtils.deleteRecursively(tem.toFile());
    }

    public void init() {
        try {
            if (!Files.exists(rootLocation))
                Files.createDirectory(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage!");
        }
    }
}

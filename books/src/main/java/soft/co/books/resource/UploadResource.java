package soft.co.books.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soft.co.books.configuration.storage.StorageService;

@RestController
@RequestMapping("/api")
public class UploadResource {

    @Autowired
    StorageService storageService;

    @GetMapping("/files/{filename:.+}/{id:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename, @PathVariable String id) {
        Resource file = storageService.loadFile(filename, id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }
}

package moe.bmwgraph.Controller;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import moe.bmwgraph.service.FileStorageService;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UploadsController {

    private final FileStorageService fileStorageService;

    public UploadsController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }


    @GetMapping("/uploads/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = fileStorageService.openFile(filename);
        return ResponseEntity.ok().body(file);
    }

    public record UploadedData(
        MultipartFile csvFile){
    }
    @PostMapping("/upload")
    private String storeAction(UploadedData uploadedData) {
        String filename = null;
        if (!uploadedData.csvFile.isEmpty()) {
            filename = fileStorageService.storeFile(uploadedData.csvFile);
        }
        return "redirect:/edit/" + filename;
    }

}

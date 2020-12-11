package clonecoder.springLover.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.ServletContext;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@RequiredArgsConstructor
@Service
public class StorageService {
    private final ServletContext servletContext;

    public void store(MultipartFile file, String keyPath, HttpServletRequest request) throws Exception {
        System.out.println("inside Store()! ::: <<<<<<");

        String dirPath = servletContext.getRealPath("/upload/");
        String finalPath = dirPath + keyPath + "/" + file.getOriginalFilename();
        new File(finalPath).mkdirs();
        file.transferTo(new File(finalPath));
    }
}

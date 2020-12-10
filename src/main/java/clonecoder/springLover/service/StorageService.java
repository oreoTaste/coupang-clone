package clonecoder.springLover.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@RequiredArgsConstructor
@Service
public class StorageService {

    public void store(MultipartFile file, String keyPath, HttpServletRequest request) throws Exception {
        File filePath = new File("C:\\uploads\\" + keyPath + "\\" + file.getOriginalFilename());
        filePath.mkdirs();
        file.transferTo(filePath);
    }
}

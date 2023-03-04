package kg.megacom.NatvProject.services.impl;

import kg.megacom.NatvProject.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadImage(String myPath, MultipartFile file){
        try {
            File path = new File(myPath + LocalDateTime.now().format(DateTimeFormatter
                    .ofPattern("yyyy-MM-dd-HH-mm-SS")) +
                    file.getOriginalFilename());
            path.createNewFile();
            FileOutputStream output = new FileOutputStream(path);
            output.write(file.getBytes());
            output.close();
            return path.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }
}

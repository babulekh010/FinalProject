package kg.megacom.NatvProject.services;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String uploadImage(String myPath, MultipartFile file);

}

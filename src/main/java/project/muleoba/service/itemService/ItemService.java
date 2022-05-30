package project.muleoba.service.itemService;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ItemService {

    void saveItem(String photo, String itemName, String category, String content);

    String filePath(List<MultipartFile> images) throws Exception;
}

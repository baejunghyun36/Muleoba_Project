package project.muleoba.service.itemService;

import org.springframework.web.multipart.MultipartFile;
import project.muleoba.vo.ItemVO;

import java.util.List;

public interface ItemService {

    void saveItem(String photo, String itemName, String category, String content);

    String filePath(List<MultipartFile> images) throws Exception;

    ItemVO detailItem(Long iID);

    List<ItemVO> itemList();

    List<ItemVO> itemCategoryList(String category);

    void deleteItem(Long iID);
}

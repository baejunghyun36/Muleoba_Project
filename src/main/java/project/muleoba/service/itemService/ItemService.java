package project.muleoba.service.itemService;

import org.springframework.web.multipart.MultipartFile;
import project.muleoba.vo.ItemVO;
import project.muleoba.domain.Item;

import java.util.List;

public interface ItemService {

    void saveItem(String photo, String itemName, String category, String content);

    void updateItem(Long iID, String photo, String itemName, String category, String content);

    String filePath(List<MultipartFile> images) throws Exception;

    Item findByIID(Long iID);

    ItemVO detailItem(Long iID);

    List<ItemVO> itemList(String address);

    List<ItemVO> itemCategoryList(String category, String address);

    void deleteItem(Long iID);

    List<ItemVO> itemMyList(Long uID, String address);

    List<ItemVO> itemSuccessList(Long uID, String address);

    List<ItemVO> myItemRequestIId(Long uID);

    List<ItemVO> requestItem(Long uID);

    List<ItemVO> searchItem(String searchString, Long uID);

    List<ItemVO> detailRequestList(Long iid);
}

package project.muleoba.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.muleoba.domain.Item;
import project.muleoba.domain.User;
import project.muleoba.form.itemForm;
import project.muleoba.service.itemService.ItemService;
import project.muleoba.vo.ItemVO;
import project.muleoba.service.userService.UserService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ItemService itemService;
    private final UserService userService;

    @PostMapping("/muleoba/uploadItem")
    public String uploadItem(@RequestPart(value = "files", required = false) List<MultipartFile> photo,
                             @RequestPart(value = "data", required = false) itemForm data) throws Exception{
        System.out.println("In controller");
        System.out.println(photo);
        for(MultipartFile p: photo){
            System.out.println(p.getOriginalFilename());
        }
        System.out.println(data.getItemName());
        System.out.println(data.getCategory());
        System.out.println(data.getContent());

        itemService.saveItem(itemService.filePath(photo), data.getItemName(), data.getCategory(), data.getContent());

        return "ok";
    }

    @GetMapping("/muleoba/getItem")
    public ItemVO getItem(@RequestParam("iID") String iID){
        Item item = itemService.findByIID(Long.parseLong(iID));
        ItemVO itemVO = new ItemVO();
        itemVO.setIID(Long.parseLong(iID));
        itemVO.setItem(item.getItem());
        itemVO.setCategory(item.getCategory());
        itemVO.setContent(item.getContent());
        itemVO.setPhoto(item.getPhoto());

        return itemVO;
    }

    @PostMapping("/muleoba/updateItem")
    public String updateItem(@RequestPart("photo") List<MultipartFile> photo, @RequestPart("data") itemForm data) throws Exception{
        System.out.println(data.getItemID());

        if(photo == null){
            itemService.updateItem(Long.parseLong(data.getItemID()), null, data.getItemName(), data.getCategory(), data.getContent());
        }
        else {
            itemService.updateItem(Long.parseLong(data.getItemID()), itemService.filePath(photo), data.getItemName(), data.getCategory(), data.getContent());
        }

        return "ok";
    }

    @PostMapping("/{iID}")//상세페이지
    public ItemVO detailItem(@PathVariable("iID") Long iID) {
        return itemService.detailItem(iID);
    }

    @GetMapping("/muleoba/mainList")//최신순 정렬 (기본)
    public List<ItemVO> itemList(Long uID, String category){
        log.info("여기{}", uID);

        User user = userService.findByuID(uID);
        String address = user.getAddress();

        if(category==null)return itemService.itemList(address);
        else return itemService.itemCategoryList(category, address);

    }


    @GetMapping("/muleoba/mylist")//최신순 정렬 (기본)
    public List<ItemVO> itemMyList(Long uID){
        User user = userService.findByuID(uID);
        return itemService.itemMyList(uID, user.getAddress());
    }

    @GetMapping("/muleoba/successlist")
    public List<ItemVO> itemSuccessList(Long uID){
        return itemService.itemSuccessList(uID, userService.findByuID(uID).getAddress());

    }

    @GetMapping("/muleoba/requestid")
    public List<ItemVO> requestid(Long uID){
        return itemService.myItemRequestIId(uID);
    }

    @GetMapping("/muleoba/requestitem")
    public List<ItemVO> requestItem(Long uID){
        return itemService.requestItem(uID);
    }

    @PostMapping("/muleoba/searchitem")
    public List<ItemVO> searchitem (@RequestBody Map<String, String> map){
        String searchString = map.get("searchString");
        String uID = map.get("uID");

        return itemService.searchItem(searchString, Long.parseLong(uID));


    }

    @PostMapping("/333")//삭제
    public void deleteItem(Long iID) {

        itemService.deleteItem(iID);
    }

    @GetMapping("/muleoba/detail/{iid}")
    public ItemVO detail(@PathVariable("iid") Long iid ){
        return itemService.detailItem(iid);
    }

    @GetMapping("/muleoba/detail/request/{iid}")
    public List<ItemVO> detailRequestList(@PathVariable("iid") Long iid){
        return itemService.detailRequestList(iid);
    }
}

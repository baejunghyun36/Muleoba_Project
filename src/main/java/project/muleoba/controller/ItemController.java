package project.muleoba.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import project.muleoba.domain.User;
import project.muleoba.vo.itemForm;
import project.muleoba.service.itemService.ItemService;
import project.muleoba.service.userService.UserService;
import project.muleoba.vo.ItemVO;

import java.util.List;

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


    @PostMapping("/333")//삭제
    public void deleteItem(Long iID){
        itemService.deleteItem(iID);
    }

}

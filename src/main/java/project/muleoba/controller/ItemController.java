package project.muleoba.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.muleoba.form.itemForm;
import project.muleoba.service.itemService.ItemService;
import project.muleoba.vo.ItemVO;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/uploadItem")
    public String uploadItem(@RequestPart("photo") List<MultipartFile> photo, @RequestPart("data") itemForm data) throws Exception{
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

    @GetMapping("/mainList")//최신순 정렬 (기본)
    public List<ItemVO> itemList(String category){
        if(category==null)return itemService.itemList();
        else return itemService.itemCategoryList(category);
    }

    @PostMapping("/333")//삭제
    public void deleteItem(Long iID){
        itemService.deleteItem(iID);
    }

}

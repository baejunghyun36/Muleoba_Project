package project.muleoba.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.muleoba.domain.Item;
import project.muleoba.form.itemForm;
import project.muleoba.service.itemService.ItemService;
import project.muleoba.vo.ItemVO;

import java.util.HashMap;
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

    @PostMapping("/getItem")
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

    @PostMapping("/updateItem")
    public String updateItem(@RequestPart("photo") List<MultipartFile> photo, @RequestPart("data") itemForm data) throws Exception{
        System.out.println(data.getIID());

        //이미지 안들어올때 처리해야함...
//        Item item = itemService.findByIID(Long.parseLong(data.getIID()));
//        item.setPhoto(itemService.filePath(photo));
//        item.setItem(data.getItemName());
//        item.setCategory(data.getCategory());
//        item.setContent(data.getContent());

        return "ok";
    }
}

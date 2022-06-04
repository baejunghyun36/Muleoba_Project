package project.muleoba.service.itemService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import project.muleoba.domain.Item;
import project.muleoba.domain.User;
import project.muleoba.repository.UserRepository;
import project.muleoba.repository.ItemRepository;

import javax.transaction.Transactional;
import java.io.File;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemServiceImpl implements ItemService{

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    public void saveItem(String photo, String itemName, String category, String content) {
        Item item = new Item();
        item.setItem(itemName);
        item.setCategory(category);
        item.setContent(content);
        item.setPhoto(photo);

        User user = userRepository.findByuID(1L);
        item.setUser(user);

        List<Item> items = user.getItems();
        items.add(item);
        user.setItems(items);

        itemRepository.save(item);
    }

    @Override
    public Item findByIID(Long iID){
        return itemRepository.findByiID(iID);
    }

    public String filePath(List<MultipartFile> images) throws Exception{

        String photoRoute = new String();

        if(!CollectionUtils.isEmpty(images)){ //이미지 파일이 존재할 경우
            //프로젝트 내의 static 폴더까지의 절대 경로
            String absolutePath = new File("").getAbsolutePath()+File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"static";

            for(MultipartFile image : images){
                String originalFileExtension = new String();
                String contentType = image.getContentType();

                if(ObjectUtils.isEmpty(contentType)){ //확장자가 없는 파일 -> 처리 x
                    break;
                }
                else{
                    if(contentType.contains("image/jpeg"))
                        originalFileExtension = ".jpg";
                    else if(contentType.contains("image/png"))
                        originalFileExtension = ".png";
                    else  // 다른 확장자일 경우 처리 x
                        break;
                }

                String newFileName = System.nanoTime()+originalFileExtension; //이미지 이름이 겹치지 않게 나노시간을 이름으로 사진 저장
                File file = new File(absolutePath+File.separator+"itemImage"+File.separator+newFileName);
                image.transferTo(file);
                file.setWritable(true);
                file.setReadable(true);

                if(photoRoute.length() == 0) { // 첫번째 이미지인 경우
                    photoRoute = File.separator + "itemImage" + File.separator + newFileName;
                }
                else{
                    photoRoute = photoRoute+" "+File.separator + "itemImage" + File.separator + newFileName;
                }
            }
        }

        return photoRoute;
    }
}

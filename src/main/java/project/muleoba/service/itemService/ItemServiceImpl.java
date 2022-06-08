package project.muleoba.service.itemService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import project.muleoba.domain.*;
import project.muleoba.repository.AlarmRepository;
import project.muleoba.repository.TransactionRepository;
import project.muleoba.repository.UserRepository;
import project.muleoba.repository.ItemRepository;
import project.muleoba.vo.ItemVO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ItemServiceImpl implements  ItemService{

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    private final TransactionRepository transactionRepository;
    private final AlarmRepository alarmRepository;
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void saveItem(String photo, String itemName, String category, String content, Long uuID) {
        Item item = new Item();
        item.setItem(itemName);
        item.setCategory(category);
        item.setContent(content);
        item.setPhoto(photo);
        //로그인 user로 변경해야 함
        User user = userRepository.findByuID(uuID);
        item.setUser(user);

        List<Item> items = user.getItems();
        items.add(item);
        user.setItems(items);

        itemRepository.save(item);
    }

    @Transactional
    @Override
    public void updateItem(Long iID, String photo, String itemName, String category, String content){
        Item item = itemRepository.findByiID(iID);
        if(photo != null) item.setPhoto(photo);
        item.setItem(itemName);
        item.setCategory(category);
        item.setContent(content);

        itemRepository.save(item);
    }

    @Override
    public Item findByIID(Long iID){
        return itemRepository.findByiID(iID);
    }


    @Override
    public String filePath(List<MultipartFile> images) throws Exception{

        String photoRoute = new String();

        if(!CollectionUtils.isEmpty(images)){ //이미지 파일이 존재할 경우
            //프로젝트 내의 static 폴더까지의 절대 경로
//            String absolutePath = new File("").getAbsolutePath()+File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"static";
            String absolutePath ="/Users/angiekim/Documents/multicampus/3rd project/workspace/Muleoba_Front/public";

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
//                File file = new File(absolutePath+File.separator+"itemImage"+File.separator+newFileName);
                File file = new File(absolutePath+File.separator+"img"+File.separator+newFileName);


                image.transferTo(file);
                file.setWritable(true);
                file.setReadable(true);



                String absolutePath2 = "/Users/angiekim/Documents/multicampus/3rd project/workspace/Muleoba_Front/public/img/";


              /*  String absolutePath2 = new File("C:\\Users\\bae10\\Desktop\\muleoba_front\\Muleoba_Front\\public\\img").getPath();*/

                String newFileName2 = newFileName;
                //log.info("여기 {}", absolutePath2+File.separator+"itemImage"+File.separator + newFileName2);
//                log.info("여기 {}", absolutePath2+newFileName2);
//                File file2 = new File(absolutePath2+ newFileName2);
//
//                MultipartFile image2 = image;
//                log.info("{}", file2);
//                image2.transferTo(file2);
//                file2.setWritable(true);
//                file2.setReadable(true);

            /*    if(photoRoute.length() == 0) { // 첫번째 이미지인 경우
                    photoRoute = File.separator + "itemImage" + File.separator + newFileName;
                }
                else{
                    photoRoute = photoRoute+" "+File.separator + "itemImage" + File.separator + newFileName;
                }*/
                if(photoRoute.length() == 0) { // 첫번째 이미지인 경우
                    photoRoute =  newFileName;
                }
                else{
                    photoRoute = photoRoute+" " + newFileName;
                }
            }
        }
        log.info("ggg {}", photoRoute);

        return photoRoute;
    }

    @Override
    public ItemVO detailItem(Long iID) {
        Item item = itemRepository.findByiID(iID);
        ItemVO itemVO = new ItemVO();
        itemVO.setItem(item.getItem());
        itemVO.setIID(item.getIID());
        itemVO.setCategory(item.getCategory());
        itemVO.setContent(item.getContent());
        itemVO.setPhoto(item.getPhoto());
        itemVO.setItemUid(item.getUser().getUID());
        itemVO.setNickName(item.getUser().getNickName());
        return itemVO;
    }

    @Override
    public List<ItemVO> itemList(String address) {
        List <Item> itemList = itemRepository.findAllOrder();
        List <ItemVO> itemVOList = new ArrayList<>();
        for (Item item : itemList) {
            entityManager.refresh(item);
            if(item.getUser().getAddress().equals(address)){
                ItemVO itemVO = new ItemVO();
                itemVO.setIID(item.getIID());
                itemVO.setItem(item.getItem());
                itemVO.setRequestNum(item.getRequestNum());
                itemVO.setCategory(item.getCategory());
                itemVO.setContent(item.getContent());
                itemVO.setAddress(address);
                itemVO.setItemUid(item.getUser().getUID());
                itemVO.setPhoto(item.getPhoto());
                itemVO.setUploadTime(item.getUploadTime());
                itemVO.setNickName(item.getUser().getNickName());
                itemVOList.add(itemVO);
            }

        }
        return itemVOList;
    }

    @Override
    public List<ItemVO> itemCategoryList(String category, String address) {
        List <Item> itemList = itemRepository.findAllOrder();
        List <ItemVO> itemVOList = new ArrayList<>();
        for (Item item : itemList) {
            entityManager.refresh(item);
            if(item.getCategory().equals(category)&&item.getUser().getAddress().equals(address)){
                ItemVO itemVO = new ItemVO();
                itemVO.setIID(item.getIID());
                itemVO.setItem(item.getItem());
                itemVO.setRequestNum(item.getRequestNum());
                itemVO.setCategory(item.getCategory());
                itemVO.setContent(item.getContent());
                itemVO.setItemUid(item.getUser().getUID());
                itemVO.setAddress(address);
                itemVO.setPhoto(item.getPhoto());
                itemVO.setNickName(item.getUser().getNickName());
                itemVO.setUploadTime(item.getUploadTime());
                itemVOList.add(itemVO);
            }
        }
        return itemVOList;
    }

    @Transactional
    @Override
    public void deleteItem(Long iID) {
        itemRepository.deleteById(iID);
    }


    @Override
    public List<ItemVO> itemMyList(Long uID, String address) {

        List<Item> itemList = userRepository.itemMyList(uID);
        List<ItemVO> itemVOList = new ArrayList<>();
        for(Item item : itemList){
            entityManager.refresh(item);
            ItemVO itemVO = new ItemVO();
            itemVO.setIID(item.getIID());
            itemVO.setItem(item.getItem());
            itemVO.setRequestNum(item.getRequestNum());
            itemVO.setCategory(item.getCategory());
            itemVO.setContent(item.getContent());
            itemVO.setAddress(address);
            itemVO.setPhoto(item.getPhoto());
            itemVO.setNickName(item.getUser().getNickName());
            itemVO.setUploadTime(item.getUploadTime());
            itemVOList.add(itemVO);
        }
        return itemVOList;
    }

    @Override
    public List<ItemVO> itemSuccessList(Long uID, String address) {

        List<Item> itemList = userRepository.itemSuccessList(uID);
        List<ItemVO> itemVOList = new ArrayList<>();
        for (Item item : itemList) {
            entityManager.refresh(item);
            if(item.getUser().getUID()==uID&&item.getStatus()==Status.Complete){
                ItemVO itemVO = new ItemVO();
                itemVO.setIID(item.getIID());
                itemVO.setItem(item.getItem());
                itemVO.setRequestNum(item.getRequestNum());
                itemVO.setCategory(item.getCategory());
                itemVO.setContent(item.getContent());
                itemVO.setAddress(address);
                itemVO.setPhoto(item.getPhoto());
                itemVO.setNickName(item.getUser().getNickName());
                itemVO.setUploadTime(item.getUploadTime());
                itemVOList.add(itemVO);
            }
        }
        return itemVOList;
    }

    @Override
    public List<ItemVO> myItemRequestIId(Long uID) {
       List <Transaction> transactionList = transactionRepository.findAllDateDesc(uID);
       List<ItemVO> itemVOList = new ArrayList<>();
        for (Transaction t : transactionList) {
            Item item = itemRepository.findByiID(t.getRequestIID());
            ItemVO itemVO = new ItemVO();
            itemVO.setIID(item.getIID());
            itemVO.setItem(item.getItem());
            itemVO.setRequestNum(item.getRequestNum());
            itemVO.setCategory(item.getCategory());
            itemVO.setContent(item.getContent());
            itemVO.setAddress(userRepository.findByuID(uID).getAddress());
            itemVO.setPhoto(item.getPhoto());
            itemVO.setNickName(item.getUser().getNickName());
            itemVO.setUploadTime(t.getRequestTime());
            itemVO.setRequestiID(t.getItem().getIID());
            itemVOList.add(itemVO);
        }
        return itemVOList;
    }

    @Override
    public List<ItemVO> requestItem(Long uID) {
        List <Transaction> transactionList = transactionRepository.findAllDateDesc(uID);
        List<ItemVO> itemVOList = new ArrayList<>();
        for (Transaction t : transactionList) {
            Item item = itemRepository.findByiID(t.getItem().getIID());
            ItemVO itemVO = new ItemVO();
            itemVO.setIID(item.getIID());
            itemVO.setItem(item.getItem());
            itemVO.setRequestNum(item.getRequestNum());
            itemVO.setCategory(item.getCategory());
            itemVO.setContent(item.getContent());
            itemVO.setStatus(item.getStatus());
            itemVO.setRequestiID(t.getRequestIID());
            itemVO.setAddress(item.getUser().getAddress());
            itemVO.setPhoto(item.getPhoto());
            itemVO.setNickName(item.getUser().getNickName());
            itemVO.setRequestiID(t.getRequestIID());
            itemVO.setUploadTime(t.getRequestTime());
            itemVOList.add(itemVO);
        }
        return itemVOList;
    }

    @Override
    public List<ItemVO> searchItem(String searchString, Long uID) {

        List <Item> itemList = itemRepository.findAllOrder();
        List <ItemVO> itemVOList = new ArrayList<>();
        String address = userRepository.findByuID(uID).getAddress();
        for (Item item : itemList) {
            entityManager.refresh(item);
            if(item.getUser().getAddress().equals(address)&&item.getItem().contains(searchString)){
                ItemVO itemVO = new ItemVO();
                itemVO.setIID(item.getIID());
                itemVO.setItem(item.getItem());
                itemVO.setRequestNum(item.getRequestNum());
                itemVO.setCategory(item.getCategory());
                itemVO.setContent(item.getContent());
                itemVO.setAddress(address);
                itemVO.setPhoto(item.getPhoto());
                itemVO.setUploadTime(item.getUploadTime());
                itemVO.setNickName(item.getUser().getNickName());
                itemVOList.add(itemVO);
            }
        }
        return itemVOList;
    }

    @Override
    public List<ItemVO> detailRequestList(Long iid) {
        List <Item> itemList = itemRepository.requestList(iid);
        List <ItemVO> itemVOList = new ArrayList<>();
        for (Item item : itemList) {
            entityManager.refresh(item);
           {
                ItemVO itemVO = new ItemVO();
                itemVO.setIID(item.getIID());
                itemVO.setItem(item.getItem());
                itemVO.setRequestNum(item.getRequestNum());
                itemVO.setCategory(item.getCategory());
                itemVO.setContent(item.getContent());
                itemVO.setAddress(item.getUser().getAddress());
                itemVO.setPhoto(item.getPhoto());
                itemVO.setUploadTime(item.getUploadTime());
                itemVO.setNickName(item.getUser().getNickName());
                itemVOList.add(itemVO);
            }
        }
        return itemVOList;
    }

    @Transactional
    @Override
    public void acceptComplete(Long iid, Long urliid) {
        Transaction t = transactionRepository.findAccept(iid,urliid);
        t.setStatus(Status.Complete);
        Alarm a = t.getAlarm();
        a.setDeleteStatus(true);
        alarmRepository.save(a);
        transactionRepository.save(t);
        Item item1 = itemRepository.findByiID(iid);
        Item item2 = itemRepository.findByiID(urliid);
        item1.setStatus(Status.Complete);
        item2.setStatus(Status.Complete);
        itemRepository.save(item1);
        itemRepository.save(item2);
    }
}

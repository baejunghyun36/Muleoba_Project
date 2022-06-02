package project.muleoba.vo;

import lombok.Getter;
import lombok.Setter;
import project.muleoba.domain.User;

@Getter
@Setter
public class UserVO {

    private Long uID;
    private String email;
    private String nickName;
    private String phoneNumber;
    private Long completeCnt;
    private String name;
    private String password;
    private String address;

    // Long uID, String email, String nickName, String phoneNumber, String name, String password, String address
    public UserVO(User user) {
        this.uID = user.getUID();
        this.email = user.getEmail();
        this.nickName = user.getNickName();
        this.phoneNumber = user.getPhoneNumber();
        this.completeCnt = user.getCompleteCnt();
        this.name = user.getName();
        this.password = user.getPassword();
        this.address = user.getAddress();
    }



}

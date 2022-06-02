package project.muleoba.domain;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uID;

    @NotNull
    private String email;

    @NotNull
    private String nickName;

    @NotNull
    private String phoneNumber;

    @NotNull
    private String name;

    @ColumnDefault("0")
    private Long completeCnt;

    @NotNull
    private String password;

    @NotNull
    private String address;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Item> items = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Alarm> alarms = new ArrayList<>();
}

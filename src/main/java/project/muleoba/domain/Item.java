package project.muleoba.domain;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long iID;

    @NotNull
    private String item;

    @NotNull
    private String category;

    private String content;

    @NotNull
    private String photo;

    private Long requestNum;

    @NotNull
    private LocalDateTime uploadTime;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="uID")
    private User user;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<Transaction> transactions = new ArrayList<>();
}

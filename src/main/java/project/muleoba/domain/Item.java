package project.muleoba.domain;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@DynamicInsert
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

    @ColumnDefault("0")
    private Long requestNum;

    @Column(updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime uploadTime;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'Normal'")
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="uID")
    private User user;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<Transaction> transactions = new ArrayList<>();
}

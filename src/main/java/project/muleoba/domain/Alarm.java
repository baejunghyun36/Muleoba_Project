package project.muleoba.domain;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long aID;

    @NotNull
    private LocalDateTime alarmTime;

    @NotNull
    private Boolean readStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="uID")
    private User user;

    @OneToOne(mappedBy = "alarm", fetch = FetchType.LAZY)
    private Transaction transaction;
}

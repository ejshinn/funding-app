package bitc.fullstack405.fun_spring.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId; // pk

    @Column(name = "user_pw", length = 45, nullable = false)
    private String userPw; // 비밀번호

    @Column(length = 30, nullable = false)
    private String name; // 이름

    @Column(length = 45, nullable = false, unique = true)
    private String email; // 이메일

    @Column(length = 300, nullable = false)
    private String address; // 주소

    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<ProjectEntity> projectList = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<SupportEntity> supportList = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<FavoriteEntity> favoriteList = new ArrayList<>();
}
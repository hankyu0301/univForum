package janghankyu.univforum.domain.student;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Student {

    @Id
    @Column(name = "student_id")
    private Long id;
    private String email;
    private String password;
    private String name;
    private String nickname;
    private String department;
    private String major;

    @Builder
    public Student(String email, String password, String name, String nickname, String department, String major) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.department = department;
        this.major = major;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

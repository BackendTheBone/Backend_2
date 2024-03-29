package com.nps.coco.domain.member.entity;

import com.nps.coco.domain.member.dto.SignUpDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Builder // setter 메서드 대신 빌더를 통해 매개변수 전달 -> 매개변수 많은 경우 코드 가독성 높여줌
@AllArgsConstructor(access = AccessLevel.PRIVATE) // 모든 필드를 매개변수로 받는 생성자
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 매개변수가 없는 생성자 -> protected: 외부에서 직접 생성자에 접근 못함. 주로 JPA에서 엔티티 객체를 생성할때 사용
@SQLDelete(sql = "UPDATE Member SET status = 'Y' WHERE member_id = ?")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Member{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @NotNull()
    private String email;

    @NotNull()
    private String password;

    @NotNull()
    private String name;

    @NotNull()
    @CreatedDate // 엔티티 생성될때 날짜+시간 자동
    @Column(name = "created_at", updatable = false) // 업데이트 안되도록 막기
    private LocalDateTime createdAt;

    @NotNull()
    @LastModifiedDate // 엔티티 업데이트될때 날짜+시간 자동
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @NotNull()
    @Column(columnDefinition = "VARCHAR(1)")
    private String status = "N";

    public Member(SignUpDto signUpDto) {
        this.email = signUpDto.getEmail();
        this.name = signUpDto.getName();
        this.password = signUpDto.getPassword();
    }

    public void update(String password, String name) {
        this.password = password;
        this.name = name;
    }
}
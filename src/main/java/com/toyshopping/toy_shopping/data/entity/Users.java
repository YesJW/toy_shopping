package com.toyshopping.toy_shopping.data.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table
public class Users implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "아이디를 입력해주세요.")
    @Column(nullable = false, unique = true)
    private String uid;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "((?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\\\W)(?=\\\\S+$).{8,16}\"\n",message = "비밀번호는 8~16자 대,소문자 + 숫자와 특수기호로 구성해야 합니다.")
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Pattern(regexp = "[a-zA-Z가-힣]{2,10}", message = "2 ~ 10자리의 한글, 영어 이름을 사용하세요.")
    private String name;

    @Pattern(regexp = "[0-9]{11}", message = "11자리의 전화번호를 입력해주세요.")
    @Column(nullable = false)
    private String phone;


    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public String getUsername() {
        return this.uid;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonLocked(){
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isEnabled() {
        return true;
    }

}

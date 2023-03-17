package com.toyshopping.toy_shopping.data.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString(exclude = "name")
@EqualsAndHashCode(callSuper = false)
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long userNo;

    @Column
    private String userId;

    @Column
    private String userPw;

    @Column
    private String userName;

    @Column
    private String userAuth;

}

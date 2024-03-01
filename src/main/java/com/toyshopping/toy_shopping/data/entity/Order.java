package com.toyshopping.toy_shopping.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate time;

    @OneToMany(mappedBy = "order")
    private List<OrderProduct> orderProducts;

    @PrePersist
    public void createdAt() {
        this.time = LocalDate.now();
    }
}

package com.toyshopping.toy_shopping.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDate time;

    @OneToMany(mappedBy = "orders")
    private List<OrderProduct> orderProducts;

    @PrePersist
    public void createdAt() {
        this.time = LocalDate.now();
    }

    public static Orders createdOrder(Users users) {
        Orders orders = new Orders();
        orders.createdAt();
        orders.setUsers(users);

        return orders;
    }
}

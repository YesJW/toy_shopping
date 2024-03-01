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

    public static Order createdOrder(Users users) {
        Order order = new Order();
        order.createdAt();
        order.setUsers(users);

        return order;
    }
}

package com.toyshopping.toy_shopping.data.dto;

import com.toyshopping.toy_shopping.data.entity.OrderProduct;
import com.toyshopping.toy_shopping.data.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {

    private Long id;

    private Users users;

    private LocalDate time;

    private List<OrderProduct> orderProducts;

}

package com.toyshopping.toy_shopping.controller;

import com.toyshopping.toy_shopping.data.dto.ProductResponseDto;
import com.toyshopping.toy_shopping.data.dto.ShoppingCartDto;
import com.toyshopping.toy_shopping.data.dto.ShoppingCartResponseDto;
import com.toyshopping.toy_shopping.data.entity.User;
import com.toyshopping.toy_shopping.service.ShoppingCartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.hibernate.tool.schema.SchemaToolingLogging.LOGGER;

@RestController
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }


    @GetMapping(value = "/cart")
    public ModelAndView cartPage() {
        LOGGER.info("[cartPageGet] ShoppingCartController get 메서드 호출됨.");
        ModelAndView mav = new ModelAndView("shoppingCartPage.html");
        return mav;
    }

    @PostMapping(value = "/addCart")
    public ResponseEntity<ShoppingCartResponseDto> addCartProduct(@RequestParam("pnum") Long pNum, @RequestParam("stock") int stock, @RequestParam("uno") Long uNo,
                                                                  @RequestParam("price") int price, @RequestParam("name") String pName) {
        ShoppingCartDto shoppingCartDto = new ShoppingCartDto();
        shoppingCartDto.setStock(stock);
        shoppingCartDto.setPNum(pNum);
        shoppingCartDto.setUser_no(uNo);

        ShoppingCartResponseDto shoppingCartResponseDto = shoppingCartService.addShoppingCartProduct(shoppingCartDto);
        shoppingCartResponseDto.setPName(pName);
        shoppingCartResponseDto.setPrice(price);
        return ResponseEntity.status(HttpStatus.OK).body(shoppingCartResponseDto);
    }

    @GetMapping(value = "/getCart")
    public ResponseEntity<List<ShoppingCartResponseDto>> getCart() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<ShoppingCartResponseDto> shoppingCartProducts = shoppingCartService.getShoppingCartProduct(user.getId());

        return ResponseEntity.status(HttpStatus.OK).body(shoppingCartProducts);
    }
}

package com.toyshopping.toy_shopping.controller;

import com.toyshopping.toy_shopping.data.dto.ShoppingCartDto;
import com.toyshopping.toy_shopping.data.dto.ShoppingCartResponseDto;
import com.toyshopping.toy_shopping.data.entity.Users;
import com.toyshopping.toy_shopping.service.ShoppingCartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.hibernate.tool.schema.SchemaToolingLogging.LOGGER;

@Controller
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }


    @GetMapping(value = "/cart")
    public String cartPage() {
        LOGGER.info("[cartPageGet] ShoppingCartController get 메서드 호출됨.");
        return "/shoppingCartPage";
    }

    @PostMapping(value = "/addCart")
    @ResponseBody
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
    @ResponseBody
    public ResponseEntity<List<ShoppingCartResponseDto>> getCart() {
        List<ShoppingCartResponseDto> shoppingCartProducts = shoppingCartService.getShoppingCartProduct();

        return ResponseEntity.status(HttpStatus.OK).body(shoppingCartProducts);
    }

    @DeleteMapping(value = "/removeFromCart")
    @ResponseBody
    public ResponseEntity<String> removeFromCart(@RequestParam("cnum") Long id) throws Exception{
        shoppingCartService.deleteShoppingCartProduct(id);
        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 삭제되었습니다.");

    }
}

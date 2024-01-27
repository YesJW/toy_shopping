package com.toyshopping.toy_shopping.service;

import com.toyshopping.toy_shopping.data.dto.ShoppingCartDto;
import com.toyshopping.toy_shopping.data.dto.ShoppingCartResponseDto;
import com.toyshopping.toy_shopping.data.entity.Product;
import com.toyshopping.toy_shopping.data.entity.ShoppingCart;
import com.toyshopping.toy_shopping.data.entity.Users;
import com.toyshopping.toy_shopping.repository.ProductRepository;
import com.toyshopping.toy_shopping.repository.ShoppingCartRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service

public class ShoppingCartServiceImpl implements ShoppingCartService{

    private final ShoppingCartRepository shoppingCartRepository;

    private final ProductRepository productRepository;

    private Logger LOGGER = LoggerFactory.getLogger(ShoppingCartServiceImpl.class);
    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository, ProductRepository productRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productRepository = productRepository;
    }

    @Override
    public ShoppingCartResponseDto addShoppingCartProduct(ShoppingCartDto shoppingCartDto) {
        LOGGER.info("[addShoppingCratProduct] 쇼핑카트에 제품 추가");
        Users users = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Product product = productRepository.getById(shoppingCartDto.getPNum());
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUno(users);
        shoppingCart.setPNum(product);
        shoppingCart.setStock(shoppingCartDto.getStock());
        ShoppingCart saveCart = shoppingCartRepository.save(shoppingCart);

        ShoppingCartResponseDto shoppingCartResponseDto = new ShoppingCartResponseDto();
        shoppingCartResponseDto.setStock(saveCart.getStock());
        shoppingCartResponseDto.setCNum(saveCart.getCNum());
        shoppingCartResponseDto.setPNum(saveCart.getCNum());
        shoppingCartResponseDto.setUser_no(saveCart.getUno().getId());
        return shoppingCartResponseDto;
    }

    @Override
    public List<ShoppingCartResponseDto> getShoppingCartProduct(Long user_no) {
        List<ShoppingCart> shoppingCarts = shoppingCartRepository.findAllByUno_id(user_no);
        List<ShoppingCartResponseDto> shoppingCartDtos= new ArrayList<>();
        for (ShoppingCart shoppingCart : shoppingCarts) {
            ShoppingCartResponseDto shoppingCartResponseDto = new ShoppingCartResponseDto();
            shoppingCartResponseDto.setCNum(shoppingCart.getCNum());
            shoppingCartResponseDto.setPNum(shoppingCart.getPNum().getNumb());
            shoppingCartResponseDto.setUser_no(shoppingCart.getUno().getId());
            shoppingCartResponseDto.setStock(shoppingCart.getStock());
            shoppingCartResponseDto.setPrice(shoppingCart.getStock() * shoppingCart.getPNum().getPrice());
            shoppingCartResponseDto.setPName(shoppingCart.getPNum().getName());
            shoppingCartDtos.add(shoppingCartResponseDto);

        }

        return shoppingCartDtos;
    }

    @Override
    public ShoppingCartResponseDto changeCartProduct(Long cNum, int stock) {
        ShoppingCart foundCart = shoppingCartRepository.findById(cNum).get();
        foundCart.setStock(stock);

        ShoppingCart changeCart = shoppingCartRepository.save(foundCart);

        ShoppingCartResponseDto shoppingCartResponseDto = new ShoppingCartResponseDto();
        shoppingCartResponseDto.setCNum(changeCart.getCNum());
        shoppingCartResponseDto.setPNum(changeCart.getPNum().getNumb());
        shoppingCartResponseDto.setUser_no(changeCart.getUno().getId());
        shoppingCartResponseDto.setStock(changeCart.getStock());

        return shoppingCartResponseDto;
    }

    @Override
    public void deleteShoppingCartProduct(Long number) throws Exception {
        shoppingCartRepository.deleteById(number);

    }
}

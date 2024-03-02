package com.toyshopping.toy_shopping.service;

import com.toyshopping.toy_shopping.data.dto.CartProductDto;
import com.toyshopping.toy_shopping.data.dto.ShoppingCartDto;
import com.toyshopping.toy_shopping.data.dto.ShoppingCartResponseDto;
import com.toyshopping.toy_shopping.data.entity.CartProduct;
import com.toyshopping.toy_shopping.data.entity.Product;
import com.toyshopping.toy_shopping.data.entity.ShoppingCart;
import com.toyshopping.toy_shopping.data.entity.Users;
import com.toyshopping.toy_shopping.repository.CartProductRepository;
import com.toyshopping.toy_shopping.repository.ProductRepository;
import com.toyshopping.toy_shopping.repository.ShoppingCartRepository;
import com.toyshopping.toy_shopping.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class ShoppingCartServiceImpl implements ShoppingCartService{

    private ShoppingCartRepository shoppingCartRepository;

    private ProductRepository productRepository;
    private UserRepository userRepository;
    private CartProductRepository cartProductRepository;
    private Logger LOGGER = LoggerFactory.getLogger(ShoppingCartServiceImpl.class);

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository, ProductRepository productRepository, UserRepository userRepository, CartProductRepository cartProductRepository) {
        this.cartProductRepository = cartProductRepository;
        this.userRepository = userRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.productRepository = productRepository;
    }

    @Override
    public ShoppingCartResponseDto addShoppingCartProduct(ShoppingCartDto shoppingCartDto) {
        LOGGER.info("[addShoppingCartProduct] 쇼핑카트에 제품 추가");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users users = userRepository.getByUid(authentication.getName());
        ShoppingCart shoppingCart = shoppingCartRepository.getByUno_id(users.getId());

        if (shoppingCart == null) {
            shoppingCart = ShoppingCart.builder()
                    .count(0)
                    .uno(users)
                    .build();
            shoppingCartRepository.save(shoppingCart);
        }

        Product product = productRepository.getById(shoppingCartDto.getPNum());

        CartProduct cartProduct = cartProductRepository.findByCart_cNumAndProduct_numb(shoppingCart.getCNum(), product.getNumb());

        if (cartProduct == null) {
            cartProduct = CartProduct.builder()
                    .cart(shoppingCart)
                    .count(shoppingCartDto.getStock())
                    .product(product)
                    .build();

            cartProductRepository.save(cartProduct);
        }

        else{
            CartProduct changeCartProduct = cartProduct;
            changeCartProduct.setCount(shoppingCartDto.getStock());
            cartProductRepository.save(changeCartProduct);
        }
        List<CartProductDto> cartProductDtos = new ArrayList<>();
        for (CartProduct cartProducts : shoppingCart.getPNum()) {
            CartProductDto cartProductDto = new CartProductDto();
            cartProductDto.setId(cartProducts.getId());
            cartProductDto.setCount(cartProducts.getCount());
            cartProductDtos.add(cartProductDto);
        }


        ShoppingCartResponseDto shoppingCartResponseDto = new ShoppingCartResponseDto();
        shoppingCartResponseDto.setStock(shoppingCart.getCount());
        shoppingCartResponseDto.setCNum(shoppingCart.getCNum());
        shoppingCartResponseDto.setPNum(cartProductDtos);
        shoppingCartResponseDto.setUser_no(shoppingCart.getUno().getId());
        return shoppingCartResponseDto;
    }

    @Override
    public ShoppingCartResponseDto getShoppingCartProduct() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users users = userRepository.getByUid(authentication.getName());


        Optional<ShoppingCart> shoppingCart = Optional.ofNullable(shoppingCartRepository.getByUno_id(users.getId()));
        if (shoppingCart.isPresent()) {
            ShoppingCart cart = shoppingCart.get();
            List<CartProductDto> cartProductDtos = new ArrayList<>();
            for (CartProduct cartProducts : cart.getPNum()) {
                CartProductDto cartProductDto = new CartProductDto();
                cartProductDto.setId(cartProducts.getId());
                cartProductDto.setProductName(cartProducts.getProduct().getName());
                cartProductDto.setCount(cartProducts.getCount());
                cartProductDto.setPrice(cartProducts.getProduct().getPrice());
                cartProductDto.setProductId(cartProducts.getProduct().getNumb());
                cartProductDtos.add(cartProductDto);
            }

            ShoppingCartResponseDto shoppingCartDtos = ShoppingCartResponseDto.builder()
                    .pNum(cartProductDtos)
                    .stock(cart.getCount())
                    .user_no(cart.getUno().getId())
                    .cNum(cart.getCNum())
                    .build();

            return shoppingCartDtos;
        }
        else {
            return null;
        }
    }

    @Override
    public ShoppingCartResponseDto changeCartProduct(Long cNum, int count) {
        ShoppingCart foundCart = shoppingCartRepository.findById(cNum).get();
        foundCart.setCount(count);

        ShoppingCart changeCart = shoppingCartRepository.save(foundCart);

        List<CartProductDto> cartProductDtos = new ArrayList<>();
        for (CartProduct cartProducts : changeCart.getPNum()) {
            CartProductDto cartProductDto = new CartProductDto();
            cartProductDto.setId(cartProducts.getId());
            cartProductDto.setCount(cartProducts.getCount());
            cartProductDtos.add(cartProductDto);
        }


        ShoppingCartResponseDto shoppingCartResponseDto = new ShoppingCartResponseDto();
        shoppingCartResponseDto.setCNum(changeCart.getCNum());
        shoppingCartResponseDto.setPNum(cartProductDtos);
        shoppingCartResponseDto.setUser_no(changeCart.getUno().getId());
        shoppingCartResponseDto.setStock(changeCart.getCount());

        return shoppingCartResponseDto;
    }

    @Override
    public void deleteShoppingCartProduct(Long number) throws Exception {
        cartProductRepository.deleteById(number);

    }
}

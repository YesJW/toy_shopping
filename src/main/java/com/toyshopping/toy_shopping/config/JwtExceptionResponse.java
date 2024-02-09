package com.toyshopping.toy_shopping.config;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class JwtExceptionResponse {

    String throwableMessage;

    HttpStatus httpstatus;


}

package com.example.kakaoshop.cart;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class CartRestControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser
    // 전체 상품 목록 조회
    public void findAll_test() throws Exception {

        // when
        ResultActions resultActions = mvc.perform(
                get("/carts")
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.totalPrice").value(104500));
        resultActions.andExpect(jsonPath("$.response.products[0].id").value(1));
        resultActions.andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response.products[0].cartItems[0].id").value(4));
        resultActions.andExpect(jsonPath("$.response.products[0].cartItems[0].option.id").value(1));
        resultActions.andExpect(jsonPath("$.response.products[0].cartItems[0].option.optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        resultActions.andExpect(jsonPath("$.response.products[0].cartItems[0].option.price").value(10000));
        resultActions.andExpect(jsonPath("$.response.products[0].cartItems[0].quantity").value(5));
        resultActions.andExpect(jsonPath("$.response.products[0].cartItems[0].price").value(50000));

    }

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("카트 추가 테스트")
    @Test
    public void cartsAdd_test() throws Exception {

        Map<Integer, Integer> input = new HashMap<>();
        input.put(1,5);
        input.put(2,5);

        ResultActions resultActions = mvc.perform(
                post("/cars/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andDo(print());

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

    }

    @DisplayName("결제 테스트")
    @Test
    public void cartsUpdate_test() throws Exception {

        Map<Integer, Integer> input = new HashMap<>();
        input.put(4,10);
        input.put(5,10);

        ResultActions resultActions = mvc.perform(
                post("/cars/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect((status().isOk()))
                .andDo(print());

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

    }
}

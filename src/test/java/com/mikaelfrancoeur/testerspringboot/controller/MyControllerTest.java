package com.mikaelfrancoeur.testerspringboot.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import lombok.SneakyThrows;

@MockBean(UserService.class)
@WebMvcTest(controllers = MyController.class)
class MyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    void whenRequestPayloadIsInvalid_thenBadRequest() {
        mockMvc.perform(put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"un@deux\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("email is not valid"))
                .andExpect(content().contentType("text/plain;charset=UTF-8"));
    }

}

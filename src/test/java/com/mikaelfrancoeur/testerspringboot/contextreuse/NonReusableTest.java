package com.mikaelfrancoeur.testerspringboot.contextreuse;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.test.context.EmbeddedKafka;

@EmbeddedKafka
@SpringBootTest
class NonReusableTest {

    @MockBean
    private MyBean myBean;

    @Test
    void test() {
        System.out.println("This is the non-reusable test");
    }

    static class MyBean {

    }
}

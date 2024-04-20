package com.mikaelfrancoeur.testerspringboot.json;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.core.io.Resource;

import lombok.SneakyThrows;

@JsonTest
class HouseTest implements WithAssertions {

    @Autowired
    private JacksonTester<House> jacksonTester;

    @Value("classpath:testdata/expected-house.json")
    private Resource houseJson;

    @Test
    @SneakyThrows
    void test() {
        House house = new House(1L, true);
        JsonContent<House> jsonContent = jacksonTester.write(house);

        assertThat(jsonContent).isEqualToJson(houseJson, JSONCompareMode.STRICT);
    }

}

package com.mikaelfrancoeur.testerspringboot.json;

import java.util.List;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.ObjectContent;
import org.springframework.core.io.Resource;

import lombok.SneakyThrows;

@JsonTest
class PersonTest implements WithAssertions {

    @Autowired
    private JacksonTester<SimplePerson> simpleJacksonTester;

    @Autowired
    private JacksonTester<ComplicatedPerson> complicatedJacksonTester;

    @Value("classpath:testdata/inbound-person-event.json")
    private Resource inboundPersonEvent;

    @Test
    @SneakyThrows
    void simplePerson() {
        ObjectContent<SimplePerson> objectContent = simpleJacksonTester.read(inboundPersonEvent);
        assertThat(objectContent).isEqualTo(new SimplePerson("John Doe", 42, "123 Main St, Springfield, IL, 62701"));
    }

    @Test
    @SneakyThrows
    void complicatedPerson() {
        ObjectContent<ComplicatedPerson> objectContent = complicatedJacksonTester.read(inboundPersonEvent);
        assertThat(objectContent).isEqualTo(new ComplicatedPerson(
                List.of(new Person(new Name("John", "Doe"), new Attributes(42),
                        new Address("123 Main St", "Springfield", "IL", "62701")))));
    }

}

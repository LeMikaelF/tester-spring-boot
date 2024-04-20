package com.mikaelfrancoeur.testerspringboot.json;

import java.util.List;

record ComplicatedPerson(
        List<Person> person
) {
}

record Person(
        Name name,
        Attributes attributes,
        Address address
) {
}

record Name(String first, String last) {
}

record Attributes(Integer age) {
}

record Address(
        String street,
        String city,
        String state,
        String zip
) {
}

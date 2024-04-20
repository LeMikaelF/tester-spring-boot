package com.mikaelfrancoeur.testerspringboot.json;

import java.util.List;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotEmpty;
import lombok.NonNull;

record SimplePerson(
        String fullName,
        Integer age,
        @Nullable String address
) {

    @JsonCreator
    private SimplePerson(@JsonProperty("person") @NotEmpty List<PersonJSON> persons) {
        this(persons.get(0).name, persons.get(0).attributes, persons.get(0).address);
    }

    public SimplePerson(@NonNull PersonJSON.Name name, @NonNull PersonJSON.Attributes attributes, @NonNull PersonJSON.AddressJSON address) {
        this(name.formatted(), attributes.age(), address.formatted());
    }

    record PersonJSON(
            Name name,
            Attributes attributes,
            AddressJSON address
    ) {
        record Name(String first, String last) {
            private String formatted() {
                return String.format("%s %s", first, last);
            }
        }

        record Attributes(Integer age) {
        }

        record AddressJSON(
                String street,
                String city,
                String state,
                String zip
        ) {
            private String formatted() {
                return isAnyFieldNull(this) ?
                        null :
                        String.format("%s, %s, %s, %s", street, city, state, zip);
            }

            private static boolean isAnyFieldNull(@Nullable AddressJSON address) {
                return address == null || address.street() == null || address.city() == null || address.state() == null || address.zip() == null;
            }
        }
    }
}

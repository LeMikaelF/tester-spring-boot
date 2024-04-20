package com.mikaelfrancoeur.testerspringboot.json;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

record House(
        @JsonIgnore Long ownerId,
        @JsonIgnore boolean hasGarage
) {

    @JsonSerialize
    @SuppressWarnings("unused")
    private List<Entity> getEntities() {
        return List.of(new Entity("house", new Entity.Owner(ownerId), hasGarage ? "withGarage" : "withoutGarage"));
    }

    record Entity(String category, Owner owner, String subcategory) {
        record Owner(Long id) {
        }
    }
}

package com.example.sisave.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum Poketype {
    NORMAL("NORMAL"),
    GHOST("GHOST"),
    FIGHTING("FIGHTING"),
    DARK("DARK"),
    DRAGON("DRAGON"),
    FAIRY("FAIRY"),
    BUG("BUG"),
    ICE("ICE"),
    FIRE("FIRE"),
    WATER("WATER"),
    GRASS("GRASS"),
    POISON("POISON"),
    PSYCHIC("PSYCHIC"),
    ELECTRIC("ELECTRIC"),
    FLYING("FLYING"),
    STEEL("STEEL");

    private final String typeName;


    public static Poketype of(String typeName) {
        return Stream.of(values()).filter(el -> el.getTypeName().equals(typeName)).findFirst().orElse(null);
    }

}

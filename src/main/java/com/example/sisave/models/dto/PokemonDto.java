package com.example.sisave.models.dto;

import com.example.sisave.models.PokemonModel;
import com.example.sisave.models.enums.Poketype;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PokemonDto implements DtoHandler<PokemonDto, PokemonModel> {

    @JsonProperty("pokeid")
    private Long pokeId;
    @JsonProperty("pokename")
    private String pokename;
    @JsonProperty("price")
    private Double price;
    @JsonProperty("types")
    private List<String> poketypes;
    @JsonProperty("level")
    private Integer level;
    @JsonProperty("createdBy")
    private String createdBy;
    @JsonProperty("pixKey")
    private String pixKey;
    @Override
    public PokemonDto wrap(PokemonModel pokemonModel) {
        return new PokemonDto(
                pokemonModel.getPokemonId(),
                pokemonModel.getName(),
                pokemonModel.getPrice().doubleValue(),
                List.of(pokemonModel.getFirstType().toString(),
                        pokemonModel.getSecondType() != null? pokemonModel.getSecondType().toString(): ""),
                pokemonModel.getLevel(),
                pokemonModel.getCreatedBy(),
                pokemonModel.getPixKey()
        );
    }

    @Override
    public PokemonModel unwrap() {
        PokemonModel model = new PokemonModel();
        model.setPokemonId(pokeId);
        model.setName(pokename);
        model.setFirstType(Poketype.of(poketypes.get(0)));
        model.setSecondType(Poketype.of(poketypes.get(1).isEmpty() ? null: poketypes.get(1)));
        model.setPrice(BigDecimal.valueOf(price));
        model.setLevel(level);
        model.setCreatedBy(createdBy);
        model.setPixKey(pixKey);
        return model;
    }
}

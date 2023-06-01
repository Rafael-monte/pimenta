package com.example.sisave.models.dto;

import com.example.sisave.models.SisavEntryModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EntryDto implements DtoHandler<EntryDto, SisavEntryModel> {

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    @JsonProperty("token")
    private String token;

    @Override
    public EntryDto wrap(SisavEntryModel sisavEntryModel) {
        return EntryDto.builder()
                .username(sisavEntryModel.getEmail())
                .password(sisavEntryModel.getPassword())
                .build();
    }

    public EntryDto wrap(SisavEntryModel sisavEntryModel, String middlewareIntegrationToken) {
        return EntryDto.builder()
                .username(sisavEntryModel.getEmail())
                .password(sisavEntryModel.getPassword())
                .token(middlewareIntegrationToken)
                .build();
    }

    @Override
    public SisavEntryModel unwrap() {
        SisavEntryModel model = new SisavEntryModel();
        model.setEmail(this.username);
        model.setPassword(this.password);
        return model;
    }
}

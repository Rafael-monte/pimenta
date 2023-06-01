package com.example.sisave.services;

import com.example.sisave.exceptions.CommunicationFailException;
import com.example.sisave.models.SisavEntryModel;
import com.example.sisave.models.dto.EntryDto;
import com.example.sisave.models.dto.NotesDto;
import com.example.sisave.models.exception.SimpleErrorMessageModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class MiddlewareService {
    @Autowired
    private SecurityTokenService securityTokenService;

    @Value("${middleware.url}")
    private String middlewareUrl;


    public List<NotesDto> getNotesFromMiddleware(SisavEntryModel entryModel) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        HttpMethod method = HttpMethod.POST;
        EntryDto dto = new EntryDto().wrap(entryModel);
        dto.setToken(this.securityTokenService.getToken());
        try {
            ResponseEntity<List<NotesDto>> response = restTemplate.exchange(
                    this.middlewareUrl, method,
                    new HttpEntity<EntryDto>(dto),
                    new ParameterizedTypeReference<List<NotesDto>>() {
                    });
            return response.getBody();
        } catch(HttpClientErrorException hcee) {
            this.handleResponse(hcee);
        }
        catch(ResourceAccessException rae) {
            throw new CommunicationFailException("The middleware service is currently down. Try again later");
        }
        return List.of();
    }



    private void handleResponse(HttpClientErrorException errorResponse) throws CommunicationFailException, JsonProcessingException {
        String errorAsString = errorResponse.getResponseBodyAsString();
        SimpleErrorMessageModel errorModel = new ObjectMapper().readValue(errorAsString, SimpleErrorMessageModel.class);
        throw new CommunicationFailException(errorModel.getCause());
    }
}

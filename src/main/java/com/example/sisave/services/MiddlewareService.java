package com.example.sisave.services;

import com.example.sisave.models.SisavEntryModel;
import com.example.sisave.models.dto.EntryDto;
import com.example.sisave.models.dto.NotesDto;
import com.example.sisave.repositories.SecurityTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class MiddlewareService {
    @Autowired
    private SecurityTokenService securityTokenService;

    @Value("${middleware.url}")
    private String middlewareUrl;


    public List<NotesDto> getNotes(SisavEntryModel entryModel) {
        RestTemplate restTemplate = new RestTemplate();
        HttpMethod method = HttpMethod.POST;
        EntryDto dto = new EntryDto().wrap(entryModel);
        dto.setToken(this.securityTokenService.getToken());

        ResponseEntity<List<NotesDto>> response = restTemplate.exchange(
                this.middlewareUrl, method,
                new HttpEntity<EntryDto>(dto),
                new ParameterizedTypeReference<List<NotesDto>>() {});
        return response.getBody();
    }
}

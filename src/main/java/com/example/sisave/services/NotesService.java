package com.example.sisave.services;

import com.example.sisave.exceptions.BadRequestBodyException;
import com.example.sisave.exceptions.EntriesNotConfiguredException;
import com.example.sisave.models.SisavEntryModel;
import com.example.sisave.models.Usuario;
import com.example.sisave.models.dto.NotesDto;
import com.example.sisave.repositories.SisavEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotesService {

    @Autowired
    private AuthService authService;

    @Autowired
    private MiddlewareService middlewareService;

    @Autowired
    private SisavEntryRepository entryRepository;

    public List<NotesDto> getNotesFromOldSystem(Usuario person, Integer noteIndex) throws EntriesNotConfiguredException {
        List<SisavEntryModel> entries = this.entryRepository.findByUsuarioUserId(person.getUserId());
        if (entries.isEmpty()) {
            throw new EntriesNotConfiguredException("There are no entries configured");
        }

        SisavEntryModel selectedEntry = null;
        try {
            selectedEntry = entries.get(noteIndex);
        } catch(IndexOutOfBoundsException ioobe) {
            throw new BadRequestBodyException(
                    String.format("Bad index: [%d]", noteIndex)
            );
        }

        return this.middlewareService.getNotes(selectedEntry);
    }



    public AuthService getAuth() {
        return authService;
    }


}

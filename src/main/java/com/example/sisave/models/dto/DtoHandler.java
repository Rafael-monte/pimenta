package com.example.sisave.models.dto;

public interface DtoHandler<TDto, TModel>{
    TDto wrap(TModel model);
    TModel unwrap();
}

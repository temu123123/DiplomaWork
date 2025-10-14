package com.diploma.student.service;

import java.util.List;
import java.util.UUID;

public interface BaseService<TRequest, TResponse> {

    TResponse create(TRequest request);

    TResponse update(UUID id, TRequest request);

    TResponse getById(UUID id);

    List<TResponse> getAll();

    void delete(UUID id);
}

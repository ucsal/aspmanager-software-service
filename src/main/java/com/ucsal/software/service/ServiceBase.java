package com.ucsal.software.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface ServiceBase<Long,
        CreateSoftwareRequest, UpdateSoftwareRequest, SoftwareResponse> {
    @Transactional
    SoftwareResponse criar(CreateSoftwareRequest createSoftwareRequest);

    Page<SoftwareResponse> buscarTodos(Pageable filtros);

    SoftwareResponse buscar(Long id);

    @Transactional
    SoftwareResponse atualizar(Long id, UpdateSoftwareRequest updateSoftwareRequest);

    @Transactional
    void deletar(Long id);
}

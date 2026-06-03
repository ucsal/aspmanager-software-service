package com.ucsal.software.service;


import com.ucsal.software.dto.request.CreateSoftwareRequest;
import com.ucsal.software.dto.request.UpdateSoftwareRequest;
import com.ucsal.software.dto.request.UpdateSolicitacaoSoftwareRequest;
import com.ucsal.software.dto.response.SoftwareResponse;
import com.ucsal.software.dto.response.SolicitacaoSoftwareResponse;
import com.ucsal.software.mapper.SoftwareMapper;
import com.ucsal.software.mapper.SolicitacaoSoftwareMapper;
import com.ucsal.software.model.*;
import com.ucsal.software.repository.SoftwareRepository;
import com.ucsal.software.repository.SolicitacaoSoftwareRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.Builder;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;

@Service
@Transactional(readOnly = true)
public class SoftwareService implements ServiceBase<Long,
        CreateSoftwareRequest, UpdateSoftwareRequest, SoftwareResponse> {

    private final SoftwareRepository softwares;
    private final SolicitacaoSoftwareRepository solicitacoesSoftware;
    private final SoftwareMapper softwareMapper;
    private final SolicitacaoSoftwareMapper solicitacaoSoftwareMapper;


    public SoftwareService(SoftwareRepository softwares,
                           SolicitacaoSoftwareRepository solicitacoesSoftware,
                           SoftwareMapper softwareMapper,
                           SolicitacaoSoftwareMapper solicitacaoSoftwareMapper) {
        this.softwares = softwares;
        this.solicitacoesSoftware = solicitacoesSoftware;
        this.softwareMapper = softwareMapper;
        this.solicitacaoSoftwareMapper = solicitacaoSoftwareMapper;
    }

    @Transactional
    @Override
    public SoftwareResponse criar(CreateSoftwareRequest createSoftwareRequest) {
        Software software = softwareMapper.toEntity(createSoftwareRequest);
        software.setDataCadastro(LocalDate.now());


        Software salvo = softwares.save(software);
        return toResponseComVinculos(salvo);
    }

    @Override
    public Page<SoftwareResponse> buscarTodos(Pageable filtros) {
        return softwares.findAll(filtros).map(this::toResponseComVinculos);
    }

    @Override
    public SoftwareResponse buscar(Long id) {
        Software software = softwares.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Software não encontrado!"));
        return toResponseComVinculos(software);
    }

    @Transactional
    @Override
    public SoftwareResponse atualizar(Long id, UpdateSoftwareRequest updateSoftwareRequest) {
        Software software = softwares.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Software não encontrado!"));

        softwareMapper.updateEntity(updateSoftwareRequest, software);
        return toResponseComVinculos(software);
    }

    @Transactional
    @Override
    public void deletar(Long id) {
        Software software = softwares.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Software não encontrado!"));

        boolean possuiHistorico = softwares.existsVinculoEmEspacos(id)
                || solicitacoesSoftware.existsBySoftwareCriado_Id(id);

        if (possuiHistorico) {
            software.setStatusRegistro(StatusRegistro.INATIVO);
            return;
        }

        softwares.delete(software);
    }

    @Transactional
    public com.ucsal.software.dto.response.SolicitacaoSoftwareResponse criarSolicitacao(com.ucsal.software.dto.request.@Valid CreateSolicitacaoSoftwareRequest request) {

        SolicitacaoSoftware solicitacao = solicitacaoSoftwareMapper.toEntity(request);
        solicitacao.setDataSolicitacao(LocalDate.now());
        solicitacao.setTipoSolicitacaoSoftware(TipoSolicitacaoSoftware.ATIVACAO);
        solicitacao.setStatusSolicitacao(StatusSolicitacao.PENDENTE);

        return solicitacaoSoftwareMapper.toResponse(solicitacoesSoftware.save(solicitacao));
    }

    public Page<SolicitacaoSoftwareResponse> buscarSolicitacoes(Pageable filtros) {
        return solicitacoesSoftware.findAll(filtros).map(solicitacaoSoftwareMapper::toResponse);
    }



    public SolicitacaoSoftwareResponse buscarSolicitacao(Long id) {
        SolicitacaoSoftware solicitacao = buscarSolicitacaoPorId(id);
        return solicitacaoSoftwareMapper.toResponse(solicitacao);
    }

    @Transactional
    public SolicitacaoSoftwareResponse atualizarSolicitacao(Long id, UpdateSolicitacaoSoftwareRequest request) {
        SolicitacaoSoftware solicitacao = buscarSolicitacaoPorId(id);

        if (solicitacao.getStatusSolicitacao() != StatusSolicitacao.PENDENTE) {
            throw new IllegalStateException("A solicitação já foi analisada.");
        }

        if (request.statusSolicitacao() == StatusSolicitacao.PENDENTE) {
            throw new IllegalArgumentException("Não é permitido definir o status como PENDENTE na análise.");
        }

        solicitacaoSoftwareMapper.updateEntity(request, solicitacao);

        if (request.statusSolicitacao() == StatusSolicitacao.APROVADO) {
            Software softwareCriado = softwares.findByNomeIgnoreCaseAndVersaoIgnoreCase(
                    solicitacao.getNome(), solicitacao.getVersao()
            ).orElseGet(() -> criarSoftwareAPartirDaSolicitacao(solicitacao));

            solicitacao.setSoftwareCriado(softwareCriado);
        }

        return solicitacaoSoftwareMapper.toResponse(solicitacao);
    }

    @Transactional
    public void deletarSolicitacao(Long id) {
        if (!solicitacoesSoftware.existsById(id)) {
            throw new EntityNotFoundException("Solicitação de software não encontrada!");
        }

        solicitacoesSoftware.deleteById(id);
    }

    private SolicitacaoSoftware buscarSolicitacaoPorId(Long id) {
        return solicitacoesSoftware.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Solicitação de software não encontrada!"));
    }

    private Software criarSoftwareAPartirDaSolicitacao(SolicitacaoSoftware solicitacao) {

        Software software = Software.builder()
                .nome(solicitacao.getNome())
                .versao(solicitacao.getVersao())
                .urlDownload(solicitacao.getUrlDownload())
                .tipoLicenca(solicitacao.getTipoLicenca())
                .objetivoUso(solicitacao.getObjetivoUso())
                .build();

        software.setDataCadastro(LocalDate.now());
        return softwares.save(software);
    }

    private SoftwareResponse toResponseComVinculos(Software software) {
        return new SoftwareResponse(
                software.getId(),
                software.getNome(),
                software.getVersao(),
                software.getUrlDownload(),
                software.getTipoLicenca(),
                software.getObjetivoUso(),
                software.getDataCadastro(),
                software.getStatusRegistro()
        );
    }

    public @Nullable Page<SolicitacaoSoftwareResponse> buscarMinhasSolicitacoes(Pageable filtros) {

        return null;
    }
}

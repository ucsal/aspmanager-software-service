package com.ucsal.software.mapper;


import com.ucsal.software.dto.request.CreateSoftwareRequest;
import com.ucsal.software.dto.request.CreateSolicitacaoSoftwareRequest;
import com.ucsal.software.dto.request.UpdateSolicitacaoSoftwareRequest;
import com.ucsal.software.dto.response.SolicitacaoSoftwareResponse;
import com.ucsal.software.model.Software;
import com.ucsal.software.model.SolicitacaoSoftware;
import org.mapstruct.*;

import java.util.List;


@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SolicitacaoSoftwareMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataSolicitacao", ignore = true)
    @Mapping(target = "tipoSolicitacaoSoftware", ignore = true)
    @Mapping(target = "statusSolicitacao", ignore = true)
    @Mapping(target = "nome", source = "software.nome")
    @Mapping(target = "versao", source = "software.versao")
    @Mapping(target = "urlDownload", source = "software.urlDownload")
    @Mapping(target = "tipoLicenca", source = "software.tipoLicenca")
    @Mapping(target = "objetivoUso", source = "software.objetivoUso")
    @Mapping(target = "disciplinasSolicitadas", source = "software.idDisciplinas", qualifiedByName = "disciplinasFromIds")
    @Mapping(target = "softwareCriado", ignore = true)
    SolicitacaoSoftware toEntity(CreateSolicitacaoSoftwareRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataSolicitacao", ignore = true)
    @Mapping(target = "tipoSolicitacaoSoftware", ignore = true)
    @Mapping(target = "professor", ignore = true)
    @Mapping(target = "nome", ignore = true)
    @Mapping(target = "versao", ignore = true)
    @Mapping(target = "urlDownload", ignore = true)
    @Mapping(target = "tipoLicenca", ignore = true)
    @Mapping(target = "objetivoUso", ignore = true)
    @Mapping(target = "disciplinasSolicitadas", ignore = true)
    @Mapping(target = "softwareCriado", ignore = true)
    void updateEntity(UpdateSolicitacaoSoftwareRequest request, @MappingTarget SolicitacaoSoftware solicitacaoSoftware);

    @Mapping(target = "software", source = ".", qualifiedByName = "softwareRequestFromSolicitacao")
    @Mapping(target = "idProfessor", source = "professor", qualifiedByName = "professorToId")
    @Mapping(target = "idSoftwareCriado", source = "softwareCriado", qualifiedByName = "softwareToId")
    SolicitacaoSoftwareResponse toResponse(SolicitacaoSoftware solicitacaoSoftware);



    @Named("softwareRequestFromSolicitacao")
    default CreateSoftwareRequest softwareRequestFromSolicitacao(SolicitacaoSoftware solicitacao) {
        if (solicitacao == null) {
            return null;
        }

        return CreateSoftwareRequest.builder()
                .nome(solicitacao.getNome())
                .versao(solicitacao.getVersao())
                .urlDownload(solicitacao.getUrlDownload())
                .tipoLicenca(solicitacao.getTipoLicenca())
                .objetivoUso(solicitacao.getObjetivoUso())
                .build();
    }

    @Named("disciplinasFromIds")
    default List<Long> disciplinasFromIds(List<Long> ids) {
        return ids;
    }

    @Named("softwareToId")
    default Long softwareToId(Software software) {
        return software == null ? null : software.getId();
    }

    @Named("professorToId")
    default Long professorToId(Long professor) {
        return professor;
    }



}

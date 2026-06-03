package com.ucsal.software.mapper;

import com.ucsal.software.dto.request.CreateSoftwareRequest;
import com.ucsal.software.dto.request.UpdateSoftwareRequest;
import com.ucsal.software.dto.response.SoftwareResponse;
import com.ucsal.software.model.Software;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SoftwareMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCadastro", ignore = true)
    @Mapping(target = "statusRegistro", ignore = true)
    Software toEntity(CreateSoftwareRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCadastro", ignore = true)
    @Mapping(target = "statusRegistro", ignore = true)
    void updateEntity(UpdateSoftwareRequest request, @MappingTarget Software software);

    SoftwareResponse toResponse(Software software);
}
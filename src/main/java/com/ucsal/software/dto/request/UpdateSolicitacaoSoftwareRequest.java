package com.ucsal.software.dto.request;

import com.ucsal.software.model.StatusSolicitacao;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;


@Schema(description = "Payload para análise de solicitação de software")
public record UpdateSolicitacaoSoftwareRequest(
        @Schema(description = "Status final da solicitação", example = "APROVADO")
        @NotNull(message = "Status da Solicitação não pode ser nulo!")
        StatusSolicitacao statusSolicitacao
) {
}

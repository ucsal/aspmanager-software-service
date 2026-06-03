package com.ucsal.software.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Tipo de operação solicitada para software")
public enum TipoSolicitacaoSoftware {
    ATIVACAO,
    DESATIVACAO,
    ATUALIZACAO,
    EXCLUSAO
}

package com.ucsal.software.controller;

import com.ucsal.software.dto.request.CreateSoftwareRequest;
import com.ucsal.software.dto.request.CreateSolicitacaoSoftwareRequest;
import com.ucsal.software.dto.request.UpdateSoftwareRequest;
import com.ucsal.software.dto.request.UpdateSolicitacaoSoftwareRequest;
import com.ucsal.software.dto.response.ErroApiResponse;
import com.ucsal.software.dto.response.SoftwareResponse;
import com.ucsal.software.dto.response.SolicitacaoSoftwareResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public interface ISoftwareController {
    URI location(SoftwareResponse software, UriComponentsBuilder uriBuilder);

    @PostMapping
    @Operation(operationId = "createSoftware", summary = "Criar um novo software", description = "Cadastra um software diretamente no sistema (Fluxo Administrativo).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Software criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para criação do software", content = @Content(schema = @Schema(implementation = ErroApiResponse.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content(schema = @Schema(implementation = ErroApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content(schema = @Schema(implementation = ErroApiResponse.class)))
    })
    ResponseEntity<SoftwareResponse> criar(
            @Valid @RequestBody CreateSoftwareRequest request,
            UriComponentsBuilder uriBuilder);

    @GetMapping
    @Operation(operationId = "listSoftwares", summary = "Listar todos os softwares", description = "Retorna uma lista paginada de todos os softwares cadastrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content(schema = @Schema(implementation = ErroApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content(schema = @Schema(implementation = ErroApiResponse.class)))
    })
    ResponseEntity<Page<SoftwareResponse>> buscarTodos(@ParameterObject Pageable filtros);

    @GetMapping("/{id}")
    @Operation(operationId = "getSoftwareById", summary = "Buscar software por ID", description = "Retorna os detalhes completos de um software específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Software encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content(schema = @Schema(implementation = ErroApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content(schema = @Schema(implementation = ErroApiResponse.class))),
            @ApiResponse(responseCode = "404", description = "Software não encontrado", content = @Content(schema = @Schema(implementation = ErroApiResponse.class)))
    })
    ResponseEntity<SoftwareResponse> buscar(
            @Parameter(description = "ID do software", example = "1") @PathVariable Long id);

    @PutMapping("/{id}")
    @Operation(operationId = "updateSoftware", summary = "Atualizar dados do software", description = "Atualiza as informações de um software existente pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Software atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos para atualização", content = @Content(schema = @Schema(implementation = ErroApiResponse.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content(schema = @Schema(implementation = ErroApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content(schema = @Schema(implementation = ErroApiResponse.class))),
            @ApiResponse(responseCode = "404", description = "Software não encontrado", content = @Content(schema = @Schema(implementation = ErroApiResponse.class)))
    })
    ResponseEntity<SoftwareResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody UpdateSoftwareRequest request);

    @DeleteMapping("/{id}")
    @Operation(operationId = "deleteSoftware", summary = "Excluir ou inativar software", description = "Remove o software do banco de dados ou altera seu status para INATIVO caso possua vínculos ativos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Software removido ou inativado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content(schema = @Schema(implementation = ErroApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content(schema = @Schema(implementation = ErroApiResponse.class))),
            @ApiResponse(responseCode = "404", description = "Software não encontrado", content = @Content(schema = @Schema(implementation = ErroApiResponse.class)))
    })
    ResponseEntity<Void> deletar(
            @Parameter(description = "ID do software", example = "1") @PathVariable Long id);

    @PostMapping("/solicitacoes")
    @Operation(operationId = "createSoftwareSolicitacao", summary = "Criar solicitação de software", description = "Permite ao professor solicitar cadastro/ativação de software para análise administrativa.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Solicitação criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para criação da solicitação", content = @Content(schema = @Schema(implementation = ErroApiResponse.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content(schema = @Schema(implementation = ErroApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content(schema = @Schema(implementation = ErroApiResponse.class))),
            @ApiResponse(responseCode = "404", description = "Professor ou disciplina não encontrada", content = @Content(schema = @Schema(implementation = ErroApiResponse.class)))
    })
    ResponseEntity<SolicitacaoSoftwareResponse> criarSolicitacao(
            @Parameter(description = "ID do usuário autenticado enviado pelo gateway", example = "12")
            @RequestHeader("X-User-Id") Long professorId,
            @Valid @RequestBody CreateSolicitacaoSoftwareRequest request,
            UriComponentsBuilder uriBuilder);

    @GetMapping("/solicitacoes")
    @Operation(operationId = "listSoftwareSolicitacoes", summary = "Listar solicitações de software", description = "Retorna lista paginada de solicitações de software.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content(schema = @Schema(implementation = ErroApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content(schema = @Schema(implementation = ErroApiResponse.class)))
    })
    ResponseEntity<Page<SolicitacaoSoftwareResponse>> buscarSolicitacoes(@ParameterObject Pageable filtros);

    @GetMapping("/solicitacoes/minhas")
    @Operation(operationId = "listMinhasSoftwareSolicitacoes", summary = "Listar minhas solicitações de software", description = "Retorna lista paginada das solicitações do professor autenticado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content(schema = @Schema(implementation = ErroApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content(schema = @Schema(implementation = ErroApiResponse.class))),
            @ApiResponse(responseCode = "404", description = "Professor autenticado não encontrado", content = @Content(schema = @Schema(implementation = ErroApiResponse.class))),
            @ApiResponse(responseCode = "409", description = "Usuário autenticado inválido para esta operação", content = @Content(schema = @Schema(implementation = ErroApiResponse.class)))
    })
    ResponseEntity<Page<SolicitacaoSoftwareResponse>> buscarMinhasSolicitacoes(
            @Parameter(description = "ID do usuário autenticado enviado pelo gateway", example = "12")
            @RequestHeader("X-User-Id") Long professorId,
            @ParameterObject Pageable filtros);

    @GetMapping("/solicitacoes/{id}")
    @Operation(operationId = "getSoftwareSolicitacaoById", summary = "Buscar solicitação de software por ID", description = "Retorna os detalhes de uma solicitação de software específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Solicitação encontrada"),
            @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content(schema = @Schema(implementation = ErroApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content(schema = @Schema(implementation = ErroApiResponse.class))),
            @ApiResponse(responseCode = "404", description = "Solicitação não encontrada", content = @Content(schema = @Schema(implementation = ErroApiResponse.class)))
    })
    ResponseEntity<SolicitacaoSoftwareResponse> buscarSolicitacao(
            @Parameter(description = "ID da solicitação", example = "1") @PathVariable Long id);

    @PatchMapping("/solicitacoes/{id}")
    @Operation(operationId = "reviewSoftwareSolicitacaoById", summary = "Analisar solicitação de software", description = "Permite aprovar ou reprovar uma solicitação pendente. Quando aprovada, o software pode ser criado automaticamente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Solicitação analisada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Status inválido para análise", content = @Content(schema = @Schema(implementation = ErroApiResponse.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content(schema = @Schema(implementation = ErroApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content(schema = @Schema(implementation = ErroApiResponse.class))),
            @ApiResponse(responseCode = "404", description = "Solicitação não encontrada", content = @Content(schema = @Schema(implementation = ErroApiResponse.class))),
            @ApiResponse(responseCode = "409", description = "Solicitação já analisada", content = @Content(schema = @Schema(implementation = ErroApiResponse.class)))
    })
    ResponseEntity<SolicitacaoSoftwareResponse> atualizarSolicitacao(@PathVariable Long id,
                                                                     @Valid @RequestBody UpdateSolicitacaoSoftwareRequest request);

    @DeleteMapping("/solicitacoes/{id}")
    @Operation(operationId = "deleteSoftwareSolicitacaoById", summary = "Excluir solicitação de software", description = "Exclui uma solicitação de software por identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Solicitação excluída com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content(schema = @Schema(implementation = ErroApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content(schema = @Schema(implementation = ErroApiResponse.class))),
            @ApiResponse(responseCode = "404", description = "Solicitação não encontrada", content = @Content(schema = @Schema(implementation = ErroApiResponse.class)))
    })
    ResponseEntity<Void> deletarSolicitacao(
            @Parameter(description = "ID da solicitação", example = "1") @PathVariable Long id);
}

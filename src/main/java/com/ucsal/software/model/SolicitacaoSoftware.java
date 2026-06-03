package com.ucsal.software.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import java.util.List;


@Builder
@Data
@Entity
@Table(name = "solicitacoes_softwares")
@AllArgsConstructor
@NoArgsConstructor
public class SolicitacaoSoftware {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "data_solicitacao", nullable = false)
    private LocalDate dataSolicitacao;
    @Column(name = "tipo_soliticacao", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoSolicitacaoSoftware tipoSolicitacaoSoftware;
    @Column(name = "status_solicitado", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusSolicitacao statusSolicitacao;

    @Column(name = "nome_software", nullable = false)
    private String nome;

    @Column(name = "versao_software", nullable = false)
    private String versao;

    @Column(name = "url_download", nullable = false)
    private String urlDownload;

    @Column(name = "tipo_licenca", nullable = false)
    private String tipoLicenca;

    @Column(name = "objetivo_uso", nullable = false)
    private String objetivoUso;
    @Column(name = "id_professor")
    private Long professor;

    @ElementCollection
    @CollectionTable(name = "solicitacao_disciplinas", joinColumns = @JoinColumn(name = "solicitacao_id"))
    @Column(name = "disciplina_id")
    private List<Long> disciplinasSolicitadas;

    @OneToOne
    @JoinColumn(name = "software_criado_id")
    private Software softwareCriado;
}



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
@Table(name = "softwares")
@AllArgsConstructor
@NoArgsConstructor
public class Software {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nome;
    private String versao;
    @Column(name = "url_download")
    private String urlDownload;
    @Column(name = "tipo_licenca")
    private String tipoLicenca;
    @Column(name = "objetivo_uso", nullable = false)
    private String objetivoUso;
    @Column(name = "data_cadastro")
    private LocalDate dataCadastro;
    @Column(name = "status_registro", nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private StatusRegistro statusRegistro = StatusRegistro.ATIVO;
    @ElementCollection
    @CollectionTable(name = "softwares_disciplinas", joinColumns = @JoinColumn(name = "software_id"))
    @Column(name = "disciplina_id")
    private List<Long> disciplinaIds;



}

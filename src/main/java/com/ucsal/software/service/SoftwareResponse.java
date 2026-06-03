package com.ucsal.software.service;

import java.time.LocalDateTime;

public class SoftwareResponse {
        private Long id;
        private String nome;
        private String versao;
        private String urlDownload;
        private String tipoLicenca;
        private String objetivoUso;
        private LocalDateTime dataCadastro;
        private String statusRegistro;


        public SoftwareResponse(Long id, String nome, String versao, String urlDownload,
                                String tipoLicenca, String objetivoUso, LocalDateTime dataCadastro,
                                String statusRegistro) {
            this.id = id;
            this.nome = nome;
            this.versao = versao;
            this.urlDownload = urlDownload;
            this.tipoLicenca = tipoLicenca;
            this.objetivoUso = objetivoUso;
            this.dataCadastro = dataCadastro;
            this.statusRegistro = statusRegistro;
        }


        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getNome() { return nome; }
        public void setNome(String nome) { this.nome = nome; }

        public String getVersao() { return versao; }
        public void setVersao(String versao) { this.versao = versao; }

        public String getUrlDownload() { return urlDownload; }
        public void setUrlDownload(String urlDownload) { this.urlDownload = urlDownload; }

        public String getTipoLicenca() { return tipoLicenca; }
        public void setTipoLicenca(String tipoLicenca) { this.tipoLicenca = tipoLicenca; }

        public String getObjetivoUso() { return objetivoUso; }
        public void setObjetivoUso(String objetivoUso) { this.objetivoUso = objetivoUso; }

        public LocalDateTime getDataCadastro() { return dataCadastro; }
        public void setDataCadastro(LocalDateTime dataCadastro) { this.dataCadastro = dataCadastro; }

        public String getStatusRegistro() { return statusRegistro; }
        public void setStatusRegistro(String statusRegistro) { this.statusRegistro = statusRegistro; }
}

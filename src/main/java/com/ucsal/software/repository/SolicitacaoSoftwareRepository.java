package com.ucsal.software.repository;

import com.ucsal.software.model.SolicitacaoSoftware;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolicitacaoSoftwareRepository extends JpaRepository<SolicitacaoSoftware, Long> {
	Page<SolicitacaoSoftware> findByProfessor(Long professorId, Pageable pageable);

	boolean existsBySoftwareCriado_Id(Long id);
}

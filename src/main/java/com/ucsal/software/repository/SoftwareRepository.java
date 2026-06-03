package com.ucsal.software.repository;

import com.ucsal.software.model.Software;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SoftwareRepository extends JpaRepository<Software, Long> {
	Optional<Software> findByNomeIgnoreCaseAndVersaoIgnoreCase(String nome, String versao);

	@Query(value = "select count(*) > 0 from espaco_softwares where id_software = :softwareId", nativeQuery = true)
	boolean existsVinculoEmEspacos(@Param("softwareId") Long softwareId);

	void delete(com.ucsal.software.model.Software software);
}

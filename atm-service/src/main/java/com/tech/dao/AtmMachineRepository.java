package com.tech.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tech.model.entities.AtmMachine;

@Repository
public interface AtmMachineRepository extends JpaRepository<AtmMachine, String> {

	@Query(nativeQuery = true, value = "SELECT SUM(value) from atm_machine")
	Long availableCash();
}

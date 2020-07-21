package com.webservice.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webservice.model.Persons;

/**
 * @author Hafiz
 * @version 0.01
 */
public interface PersonRepository extends JpaRepository<Persons, UUID> {

	Persons findByUsername(String username);
	Persons deleteByUsername(String firstName) throws Exception;
}

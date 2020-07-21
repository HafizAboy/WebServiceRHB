package com.webservice.service;

import java.util.List;

import com.webservice.model.Persons;

/**
 * @author Hafiz
 * @version 0.01
 */
public interface PersonService {

    List<Persons> findAllPersons();
    Persons findByUsername(String username);
	Persons savePerson(Persons person) throws Exception;
	Persons updatePerson(Persons person) throws Exception;
	Persons deleteByUsername(String username) throws Exception;
	void deletePerson(Persons person) throws Exception;
}

package com.webservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webservice.model.Persons;
import com.webservice.repository.PersonRepository;

/**
 * @author Hafiz
 * @version 0.01
 */
@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
	private PersonRepository personRepo;
	
	@Override
	public Persons savePerson(Persons person) throws Exception {
		// TODO Auto-generated method stub
		return personRepo.save(person);
	}

	@Override
	public List<Persons> findAllPersons() {
		// TODO Auto-generated method stub
		return (List<Persons>)personRepo.findAll();
	}

	@Override
	public Persons findByUsername(String username) {
		// TODO Auto-generated method stub
		return personRepo.findByUsername(username);
	}

	@Override
	public Persons updatePerson(Persons person) throws Exception {
		// TODO Auto-generated method stub
		return savePerson(person);
	}

	@Override
	public Persons deleteByUsername(String username) throws Exception {
		// TODO Auto-generated method stub
		return personRepo.deleteByUsername(username);
	}

	@Override
	public void deletePerson(Persons person) throws Exception {
		// TODO Auto-generated method stub
		personRepo.delete(person);
	}
}

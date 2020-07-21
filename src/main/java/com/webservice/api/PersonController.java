package com.webservice.api;

import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.webservice.constants.ErrorEnum;
import com.webservice.exceptions.WebserviceException;
import com.webservice.model.Persons;
import com.webservice.service.PersonService;

import io.swagger.annotations.ApiOperation;

/**
 * @author Aboy
 * @version 0.01
 */
@CrossOrigin
@RestController
@RequestMapping("/persons")
public class PersonController {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PersonService personService;

	/**
	 * Fetch a list of persons
	 * @return a list of persons
	 * @throws Exception 
	 */
	@RequestMapping(path="", method = RequestMethod.GET, produces = "application/json")
	@ApiOperation(value = "Fetch all persons")
	public ResponseEntity<?> persons() throws Exception {
		List<Persons> persons = (List<Persons>) personService.findAllPersons();

		return new ResponseEntity<>(persons, HttpStatus.OK);
	}

	/**
	 * Finds a person by <code>username</code>
	 * 
	 * @param username person's username
	 * 
	 * @return the {@link Persons} object
	 * @throws Exception 
	 */
	@RequestMapping(path = "/getPersonByUsername/{username}", 
			method = RequestMethod.GET)
	@ApiOperation(value = "Fetch a person")
	public ResponseEntity<?> person(@PathVariable String username) throws Exception {
		Persons person = new Persons();
		person = personService.findByUsername(username);
		
		return new ResponseEntity<>(person, HttpStatus.OK);
	}

	/**
	 * Add a person
	 * 
	 * @param person
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(path = "",
			method = RequestMethod.POST,
			consumes =  MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Create a new person")
	public ResponseEntity<Persons> addPerson(@RequestBody Persons person) throws Exception {

		Persons savedPerson = new Persons();

		// validate the input
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<Persons>> violations = validator.validate(person);

		logger.error("size of violations : " + violations.size());

		for (ConstraintViolation<Persons> constraintViolation : violations) {
			logger.error("constraintViolation: field \"" + constraintViolation.getPropertyPath() + "\"," + constraintViolation.getMessage());
			throw new WebserviceException(ErrorEnum.REQUIRED_ELEMENT_MISSING, constraintViolation.getMessage());
		}
		// validation - End

		if(violations.size() == 0) {
			logger.info("Validation Success!");

			try {
				logger.info("Saving Person");
				savedPerson =  personService.savePerson(person);
				logger.info("Person creation completed");
			}catch (Exception e) {
				logger.error("Error:- Unable to create person");
				throw new WebserviceException(ErrorEnum.SAVING_UNSUCCESSFUL, ErrorEnum.SAVING_UNSUCCESSFUL.getDescription());
			}
		}
		return new ResponseEntity<Persons>(savedPerson, HttpStatus.CREATED);
	}

	/**
	 * Updates the person
	 * 
	 * @param person
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(path = "/{username}",
			method = RequestMethod.PUT)
	@ApiOperation(value = "Update a person")
	public ResponseEntity<Persons> updatePerson(@PathVariable String username, @RequestBody Persons person) throws Exception {

		Persons savedPerson = new Persons();
		Persons personFindByUsername = personService.findByUsername(username);
		// validate the input
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<Persons>> violations = validator.validate(person);

		logger.error("size of violations : " + violations.size());

		for (ConstraintViolation<Persons> constraintViolation : violations) {
			logger.error("constraintViolation: field \"" + constraintViolation.getPropertyPath() + "\"," + constraintViolation.getMessage());
			throw new WebserviceException(ErrorEnum.REQUIRED_ELEMENT_MISSING, constraintViolation.getMessage());
		}
		// validation - End

		if(violations.size() == 0) {
			logger.info("Validation Success!");

			personFindByUsername.setFirstName(person.getFirstName());
			personFindByUsername.setLastName(person.getLastName());
			personFindByUsername.setEmail(person.getEmail());

			try {
				logger.info("Update Person");
				savedPerson = personService.updatePerson(personFindByUsername);
				logger.info("Person updated");
			}catch (Exception e) {
				logger.error("Error:- Unable to update person");
				throw new WebserviceException(ErrorEnum.SAVING_UNSUCCESSFUL, ErrorEnum.SAVING_UNSUCCESSFUL.getDescription());
			}
		}
		
		return new ResponseEntity<Persons>(savedPerson, HttpStatus.OK);
	}


	/**
	 * Deletes person identified with <code>username</code>
	 * @param username
	 * @throws Exception 
	 */
	@RequestMapping(path = "/{username}", 
			method = RequestMethod.DELETE)
	@ApiOperation(value = "Delete a person")
	public ResponseEntity<?> deletePerson(@PathVariable String username) throws Exception {

		Persons personFindByUsername = personService.findByUsername(username);
		try {
			logger.info("Delete Person");
			personService.deletePerson(personFindByUsername);
			logger.info("Person Deleted");
		}catch (Exception e) {
			logger.error("Error:- Unable to delete person");
			throw new WebserviceException(ErrorEnum.DELETION_UNSUCCESSFUL, ErrorEnum.DELETION_UNSUCCESSFUL.getDescription());
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
}

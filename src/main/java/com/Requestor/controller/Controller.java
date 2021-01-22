package com.Requestor.controller;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.Requestor.exception.NoContentException;
import com.Requestor.model.Donor;
import com.Requestor.model.Requestor;
import com.Requestor.service.Service;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class Controller {

	@Autowired
	Service service;

	@GetMapping(value = "/getrequestors")
	public ResponseEntity<List<Requestor>> getAllRequestors() {
		log.info("Fetching all requestors");
		return new ResponseEntity<List<Requestor>>(service.getAllRequestors(), HttpStatus.OK);
	}

	@GetMapping(value = "/getrequestorbyid/{id}")
	public ResponseEntity<Requestor> getRequestorById(@PathVariable("id") int id) throws NoContentException {
		log.info("Fetching requestor by id");
		return new ResponseEntity<Requestor>(service.getRequestorById(id), HttpStatus.OK);
	}

	@GetMapping(value = "/getrequestorbyname/{patientName}")
	public ResponseEntity<List<Requestor>> getRequestorByName(@PathVariable("patientName") String patientName)
			throws NoContentException {
		try {
			log.info("Fetching requestor by id");
			return new ResponseEntity<List<Requestor>>(service.getRequestorByName(patientName), HttpStatus.OK);
		} catch (NoContentException ex) {
			throw new NoContentException("Data not found");
		}
	}

	@GetMapping(value = "/getrequestorbybloodgroup/{bloodGroup}")
	public ResponseEntity<List<Requestor>> getRequestorByBloodGroup(@PathVariable("bloodGroup") String bloodGroup)
			throws NoContentException {
		try {
			log.info("Fetching requestor by blood group");
			return new ResponseEntity<List<Requestor>>(service.getRequestorByBloodGroup(bloodGroup), HttpStatus.OK);
		} catch (NoContentException ex) {
			throw new NoContentException("Data not found");
		}
	}

	@GetMapping(value = "/approverequestor/{id}")
	public ResponseEntity<Requestor> approveRequestor(@PathVariable("id") int id) throws NoContentException {
		log.info("Requestor approved");
		return new ResponseEntity<Requestor>(service.approveRequestor(id), HttpStatus.OK);
	}

	@PostMapping(value = "/authenticaterequestor")
	public ResponseEntity<Requestor> authenticateRequestor(@RequestBody Requestor requestor)
			throws NoSuchElementException {
		log.debug("Authenticating requestor");
		return new ResponseEntity<Requestor>(service.authenticateRequestor(requestor), HttpStatus.OK);
	}

	@PostMapping(value = "/createrequestor")
	public ResponseEntity<Requestor> createRequestor(@RequestBody Requestor requestor)
			throws SQLIntegrityConstraintViolationException {
		try {
			log.info("Requestor added");
			return new ResponseEntity<Requestor>(service.createRequestor(requestor), HttpStatus.CREATED);
		} catch (SQLIntegrityConstraintViolationException ex) {
			throw new SQLIntegrityConstraintViolationException();
		}
	}

	@PutMapping(value = "/updaterequestor/{id}")
	public ResponseEntity<Requestor> updateRequestor(int id, @RequestBody Requestor requestor) {
		log.info("Updated requestor");
		return new ResponseEntity<Requestor>(service.updateRequestor(id, requestor), HttpStatus.CREATED);
	}

	@DeleteMapping(value = "/deleterequestor/{id}")
	public boolean deleteRequestor(@PathVariable("id") int id) {
		log.info("Requestor deleted");
		return service.deleteRequestor(id);
	}

	@GetMapping(value = "/getdonorbybloodgroup/{bloodGroup}")
	public ResponseEntity<List<Donor>> getDonorsByBloodGroup(@PathVariable("bloodGroup") String bloodGroup) {
		log.info("Fetching donor by blood group");
		return new ResponseEntity<List<Donor>>(service.getDonorsByBloodGroup(bloodGroup), HttpStatus.OK);
	}

}

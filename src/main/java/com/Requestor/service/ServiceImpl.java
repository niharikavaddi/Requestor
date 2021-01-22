package com.Requestor.service;

import java.net.URI;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.Requestor.exception.NoContentException;
import com.Requestor.model.Donor;
import com.Requestor.model.Requestor;
import com.Requestor.repository.Repository;

@org.springframework.stereotype.Service("service")
public class ServiceImpl implements Service {

	@Autowired
	Repository repository;

	@Autowired
	RestTemplate restTemplate;

	@Override
	public List<Requestor> getAllRequestors() {
		return repository.findAll();
	}

	@Override
	public Requestor getRequestorById(int id) throws NoContentException {
		Optional<Requestor> requestor = repository.findById(id);
		if (requestor.isEmpty()) {
			throw new NoContentException("Data not found");
		} else {
			return requestor.get();
		}
	}

	@Override
	public List<Requestor> getRequestorByName(String patientName) throws NoContentException {
		Optional<List<Requestor>> requestor = repository.findByPatientName(patientName);
		if (requestor.isEmpty()) {
			throw new NoContentException("Data not found");
		} else {
			return requestor.get();
		}
	}

	@Override
	public List<Requestor> getRequestorByBloodGroup(String bloodGroup) throws NoContentException {
		Optional<List<Requestor>> requestor = repository.findByBloodGroup(bloodGroup);
		if (requestor.isEmpty()) {
			throw new NoContentException("Data not found");
		} else {
			return requestor.get();
		}
	}

	@Override
	public Requestor approveRequestor(int id) {
		Optional<Requestor> requestor = repository.findById(id);
		if (requestor.isEmpty()) {
			return null;
		} else {
			Requestor newRequestor = requestor.get();
			newRequestor.setApprovalStatus("approved");
			return repository.save(newRequestor);
		}
	}

	@Override
	public Requestor updateRequestor(int id, Requestor requestor) {
		Optional<Requestor> updateRequestor = repository.findById(id);
		if (updateRequestor.isEmpty()) {
			return repository.save(updateRequestor.get());
		} else {
			Requestor newRequestor = updateRequestor.get();
			newRequestor = requestor;
			return repository.save(newRequestor);
		}
	}

	@Override
	public boolean deleteRequestor(int id) {
		repository.deleteById(id);
		return true;
	}

	@Override
	public List<Donor> getDonorsByBloodGroup(String bloodGroup) {
		String uri = "http://localhost:9090/getdonorbybloodgroup/" + bloodGroup;
		ResponseEntity<List<Donor>> donors = restTemplate.exchange(uri, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Donor>>() {
				});
		donors.getBody().stream().forEach(a -> System.out.println(a.getDonorName()));
		return donors.getBody();
	}

	@Override
	public Requestor createRequestor(Requestor requestor) throws SQLIntegrityConstraintViolationException {
		return repository.save(requestor);
	}

	@Override
	public Requestor authenticateRequestor(Requestor requestor) throws NoSuchElementException {
		List<Requestor> requestors = repository.findAll();
		return requestors.stream().filter(check -> check.getUserName().equals(requestor.getUserName()))
				.filter(check -> check.getPassword().equals(requestor.getPassword())).findFirst().get();
	}
}

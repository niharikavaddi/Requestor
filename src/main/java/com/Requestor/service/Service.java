package com.Requestor.service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.NoSuchElementException;

import com.Requestor.exception.NoContentException;
import com.Requestor.model.Donor;
import com.Requestor.model.Requestor;

public interface Service {
	public List<Requestor> getAllRequestors();

	public Requestor getRequestorById(int id) throws NoContentException;

	public List<Requestor> getRequestorByName(String patientName) throws NoContentException;

	public List<Requestor> getRequestorByBloodGroup(String bloodGroup) throws NoContentException;

	public Requestor approveRequestor(int id);

	public Requestor updateRequestor(int id, Requestor requestor);

	public boolean deleteRequestor(int id);

	public Requestor createRequestor(Requestor requestor) throws SQLIntegrityConstraintViolationException;

	public List<Donor> getDonorsByBloodGroup(String bloodGroup);

	public Requestor authenticateRequestor(Requestor requestor) throws NoSuchElementException;

}

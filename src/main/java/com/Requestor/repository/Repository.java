package com.Requestor.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Requestor.model.Requestor;

public interface Repository extends JpaRepository<Requestor, Integer> {

	public Optional<List<Requestor>> findByPatientName(String patientName);

	public Optional<List<Requestor>> findByBloodGroup(String bloodGroup);

}

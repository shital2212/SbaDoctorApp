package com.doctor.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.doctor.model.Doctor;
import com.doctor.repository.DoctorRepository;

@RestController
@RequestMapping("/api/doctors")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8000"})
public class DoctorController {

	@Autowired
	private DoctorRepository doctorRepository;

	@PostMapping
	public ResponseEntity<Doctor> createDoctor(@RequestBody Doctor doctor) {
		// Write code to create a new Doctor and save to the database, return the
		// savedDoctor along with ResponseEntity with HTTP status CREATED on successful
		// creation.

		Doctor saveDoctor = doctorRepository.save(doctor);
		return new ResponseEntity<>(saveDoctor, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Doctor> getDoctorById(@PathVariable Long id) {
		// Write code to get a single doctor from the database
		// if found return ResponseEntity with the doctor object along with HTTP status
		// OK
		// if not found return ResponseEntity with empty object with HTTP status
		// NOT_FOUND
		Optional<Doctor> doctor = doctorRepository.findById(id);

		if (doctor.isPresent()) {
			return new ResponseEntity<>(doctor.get(), HttpStatus.OK);
		} else {

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Doctor> updateDoctor(@PathVariable Long id, @RequestBody Doctor updatedDoctor) {
		// Write code to update a doctor object using given doctor id
		// if doctor with given id is not found return ResponseEntity with empty object
		// with HTTP status NOT_FOUND
		// if found return ResponseEntity with updated doctor object along with HTTP
		// status OK
		Optional<Doctor> existingDoctor = doctorRepository.findById(id);
		if (existingDoctor.isPresent()) {
			Doctor doctor = existingDoctor.get();
			doctor.setName(updatedDoctor.getName());
			doctor.setSpecialty(updatedDoctor.getSpecialty());
			doctor.setHospitalAffiliation(updatedDoctor.getHospitalAffiliation());
			doctor.setContactPhone(updatedDoctor.getContactPhone());
			doctorRepository.save(doctor);
			return new ResponseEntity<>(doctor, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
		// Write code to delete a doctor object using given doctor id
		// if doctor with given id is not found return Void ResponseEntity with HTTP
		// status NOT_FOUND
		// if found return a Void ResponseEntity along with HTTP status NO_CONTENT
		Optional<Doctor> existingDoctor = doctorRepository.findById(id);
		if (existingDoctor.isPresent()) {
			doctorRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping
	public ResponseEntity<List<Doctor>> getAllDoctors() {
		// Write code to get all doctors data from the database and return them as a
		// ResponseEntity along with HTTP status OK
		List<Doctor> doctors = doctorRepository.findAll();
		return new ResponseEntity<>(doctors, HttpStatus.OK);
	}

	@GetMapping("/searchByName")
	public ResponseEntity<List<Doctor>> searchDoctorsByName(@RequestParam("name") String name) {
		// Write code to get doctors data by name from the database and return them as a
		// ResponseEntity along with HTTP status OK
		List<Doctor> doctors = doctorRepository.findByNameContaining(name);
		return new ResponseEntity<>(doctors, HttpStatus.OK);
	}

}

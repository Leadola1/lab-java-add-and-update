package com.ironhack.week9_lab.Services;

import com.ironhack.week9_lab.Dto.PatientDto;
import com.ironhack.week9_lab.enums.Status;
import com.ironhack.week9_lab.model.Employee;
import com.ironhack.week9_lab.model.Patient;
import com.ironhack.week9_lab.repository.EmployeeRepository;
import com.ironhack.week9_lab.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientService {
    private final PatientRepository patientRepository;
    private final EmployeeRepository employeeRepository;

    public List<Patient> getAllPatients() {
        log.info("Getting all patients");
        return patientRepository.findAll();
    }

    public Optional<Patient> getPatientById(Long id) {
        return patientRepository.findById(id);
    }

    public List<Patient> getPatientByDatOfBirthRange(LocalDate from, LocalDate to) {
        return patientRepository.findByDateOfBirthBetween(from, to);
    }

    public List<Patient> getPatientsByAdmittingDoctorDepartment(String admittingDoctorDepartment) {
        return patientRepository.findByAdmittedBy_Department(admittingDoctorDepartment);
    }

    public List<Patient> getPatientsByDoctorStatus(Status doctorStatus) {
        return patientRepository.findByAdmittedBy_Status(doctorStatus);
    }


    public Patient addPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public Patient updatePatient(Long id, PatientDto patientDto) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        if (patientDto.getName() != null) patient.setName(patientDto.getName());
        if (patientDto.getDateOfBirth() != null) patient.setDateOfBirth(patientDto.getDateOfBirth());
        if (patientDto.getAdmittedBy() != null) {
            Employee employee = employeeRepository.findById(patientDto.getAdmittedBy())
                    .orElseThrow(() -> new RuntimeException("Employee not found"));
            patient.setAdmittedBy(employee);
        }
        return patientRepository.save(patient);
    }
}

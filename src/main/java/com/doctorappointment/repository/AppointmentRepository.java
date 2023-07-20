package com.doctorappointment.repository;

import com.doctorappointment.model.Appointment;
import com.doctorappointment.model.Doctor;
import com.doctorappointment.model.Patient;
import com.doctorappointment.output.AppointmentsAsDoctorOutput;
import com.doctorappointment.output.AppointmentsAsPatientOutput;
import com.doctorappointment.output.AppointmentsTakenOutput;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {


    //region Doctor
    @Query("SELECT count(a) FROM Appointment a " +
            "where a.doctor.id = :#{#doctor.id} " +
            "and cast(a.startDateTime as date) = cast(:localDate as date)")
    int getAppointmentCountByDate(@Param("doctor") Doctor doctor, @Param("localDate") LocalDate localDate);


    @Query("SELECT new com.doctorappointment.output.AppointmentsAsDoctorOutput(a.uuid , a.startDateTime, a.finishDateTime,a.registerDateTime,a.takenDateTime, a.doctor,a.takenPatient,a.deleted) " +
            "FROM Appointment a left join a.takenPatient " +
            "where cast(a.doctor.uuid as string) = :doctorUuid")
    Collection<AppointmentsAsDoctorOutput> findAppointmentAsDoctorByDoctor(@Param("doctorUuid") String doctorUuid);

    @Query("SELECT new com.doctorappointment.output.AppointmentsAsDoctorOutput(a.uuid , a.startDateTime, a.finishDateTime,a.registerDateTime,a.takenDateTime, a.doctor,a.takenPatient,a.deleted) " +
            "FROM Appointment a left join a.takenPatient " +
            "where cast(a.doctor.uuid as string) = :doctorUuid " +
            "and cast(a.startDateTime as date) BETWEEN cast(:startDate as date) and cast(:finishDate as date)")
    Collection<AppointmentsAsDoctorOutput> findAppointmentAsDoctorByDoctorAndDate(@Param("doctorUuid") String doctorUuid, @Param("startDate") LocalDate startDate, @Param("finishDate") LocalDate finishDate);


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Collection<Appointment> findAppointmentByUuid(UUID uuid);

    //endregion Patient

    //region Patient

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT a from Appointment a where a.id= ?1")
    Optional<Appointment> findAppointmentById(Long aLong);

    @Query("SELECT new com.doctorappointment.output.AppointmentsAsPatientOutput(a.uuid , a.startDateTime, a.finishDateTime, a.doctor) FROM Appointment a " +
            "where a.takenPatient is null " +
            "and cast(a.startDateTime as date) <= cast(:localDate as date) " +
            "and cast(:localDate as date) <= cast(a.finishDateTime as date) ")
    Collection<AppointmentsAsPatientOutput> findAppointmentByDate(@Param("localDate") LocalDate localDate);

    @Query("SELECT new com.doctorappointment.output.AppointmentsAsPatientOutput(a.uuid , a.startDateTime, a.finishDateTime, a.doctor) FROM Appointment a " +
            "where a.takenPatient is null " +
            "and cast(a.startDateTime as date) <= cast(:localDate as date) " +
            "and cast(:localDate as date) <= cast(a.finishDateTime as date) " +
            "and cast(a.doctor.uuid as string) = :doctorUuid")
    Collection<AppointmentsAsPatientOutput> findAppointmentByDateAndDoctor(@Param("localDate") LocalDate localDate, @Param("doctorUuid") String doctorUuid);

    @Query("SELECT new com.doctorappointment.output.AppointmentsTakenOutput (a.uuid , a.startDateTime, a.finishDateTime, a.doctor, a.takenDateTime) " +
            "FROM Appointment a " +
            "where a.takenPatient.id = :#{#patient.id}")
    Collection<AppointmentsTakenOutput> findTakenAppointments(@Param("patient") Patient patient);

    //endregion Patient
}

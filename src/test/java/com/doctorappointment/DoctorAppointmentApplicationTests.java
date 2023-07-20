package com.doctorappointment;

import com.doctorappointment.input.AppointmentTakeInput;
import com.doctorappointment.service.AppointmentServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DoctorAppointmentApplicationTests {

    @Autowired
    AppointmentServices appointmentServices;

    @Test
    void appointmentTakeTest() {


        System.out.println("appointmentTakeTest Started");
        Thread thread1 = getAppointmentTakeThread("Thread1", "Zahra", "Nasiri", "090198765432","43d5146e-9ced-45e1-b0a1-f9308b4a3708");
        Thread thread2 = getAppointmentTakeThread("Thread2", "Nolofar", "Irani", "093598765432","43d5146e-9ced-45e1-b0a1-f9308b4a3708");

        thread1.start();
        thread2.start();
    }

    @Test
    void deleteAppointmentTest() {

        System.out.println("deleteAppointmentTest Started");
        Thread thread1 = getAppointmentTakeThread("Thread1", "Zahra", "Nasiri", "090198765432", "43d5146e-9ced-45e1-b0a1-f9308b4a3708");
        Thread thread2 = deleteAppointmentThread("Thread2", "43d5146e-9ced-45e1-b0a1-f9308b4a3708");

        thread1.start();
        thread2.start();
    }

    private Thread getAppointmentTakeThread(String threadName, String name, String family, String mobile, String appointmentUuid) {

        Thread thread = new Thread(() -> {
            try {
                System.out.println(threadName + " Started");
                AppointmentTakeInput appointmentTakeInput = new AppointmentTakeInput();
                appointmentTakeInput.setAppointmentUuid(appointmentUuid);
                appointmentTakeInput.setName(name);
                appointmentTakeInput.setFamily(family);
                appointmentTakeInput.setMobile(mobile);
                appointmentServices.takeAppointmentsAsPatient(appointmentTakeInput);
                System.out.println(threadName + " Successfully Finished");
            } catch (Exception e) {
                System.out.println(threadName + " Exception:");
                System.out.println(e.getMessage());
            }
        });
        return thread;
    }

    private Thread deleteAppointmentThread(String threadName, String appointmentUuid) {

        Thread thread = new Thread(() -> {
            try {
                System.out.println(threadName + " Started");

                this.appointmentServices.deleteAppointmentByUuid(appointmentUuid);

                System.out.println(threadName + " Successfully Finished");
            } catch (Exception e) {
                System.out.println(threadName + " Exception:");
                System.out.println(e.getMessage());
            }
        });
        return thread;
    }


    @Test
    void contextLoads() {
    }

}

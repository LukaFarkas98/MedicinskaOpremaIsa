package LukaFarkas.MedOpremaBackend.mapper;

import LukaFarkas.MedOpremaBackend.dto.AppointmentDto;
import LukaFarkas.MedOpremaBackend.entity.Appointment;
import LukaFarkas.MedOpremaBackend.entity.Company;
import LukaFarkas.MedOpremaBackend.entity.Equipment;

public class AppointmentMapper {

    public static AppointmentDto toDto(Appointment appointment){
        if(appointment == null)
            return null;

        return new AppointmentDto(
                appointment.getAppointment_id(),
                appointment.getEquipment().getEquipment_id(),
                appointment.getCompany().getCompany_id(),
                appointment.getTimeSlots()
        );
    }

    public static Appointment toEntity(AppointmentDto appointmentDto, Equipment equipment, Company company){
        if (appointmentDto == null)
            return null;
        Appointment appointment = new Appointment();
        appointment.setAppointment_id(appointmentDto.getAppointmentId());
        appointment.setEquipment(equipment);
        appointment.setCompany(company);
        appointment.setTimeSlots(appointmentDto.getTimeSlots());

        return appointment;
    }
}

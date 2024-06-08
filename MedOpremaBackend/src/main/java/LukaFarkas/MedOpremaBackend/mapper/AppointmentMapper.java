package LukaFarkas.MedOpremaBackend.mapper;

import LukaFarkas.MedOpremaBackend.dto.AppointmentDto;
import LukaFarkas.MedOpremaBackend.entity.*;

public class AppointmentMapper {

    public static AppointmentDto toDto(Appointment appointment){
        if(appointment == null)
            return null;

        return new AppointmentDto(
                appointment.getAppointment_id(),
                appointment.getEquipment().getEquipment_id(),
                appointment.getCompany().getCompany_id(),
                appointment.getUser().getId(),
                appointment.getTimeSlot().getId()
        );
    }

    public static Appointment toEntity(AppointmentDto appointmentDto, Equipment equipment, Company company, User user,  TimeSlot timeSlot){
        return new Appointment(
                appointmentDto.getAppointmentId(),
                equipment,
                company,
                user,
                timeSlot
        );
    }
}

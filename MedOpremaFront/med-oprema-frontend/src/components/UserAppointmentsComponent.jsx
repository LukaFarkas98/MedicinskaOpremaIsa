import React, { useEffect, useState, useContext } from 'react';
import axios from 'axios';
import { AuthContext } from '../authentication/AuthProvider';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const UserAppointmentsComponent = () => {
    const [appointments, setAppointments] = useState([]);
    const { auth } = useContext(AuthContext);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        const fetchAppointments = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/api/appointments/user/${auth.user.id}`, {
                    headers: { Authorization: `Bearer ${auth.token}` }
                });
                const appointmentData = await Promise.all(response.data.map(async (appointment) => {
                    const timeslotResponse = await axios.get(`http://localhost:8080/api/timeslots/${appointment.timeSlotId}`, {
                        headers: { Authorization: `Bearer ${auth.token}` }
                    });
                    const equipmentResponse = await axios.get(`http://localhost:8080/api/appointments/by-appointment/${appointment.appointmentId}`, {
                        headers: { Authorization: `Bearer ${auth.token}` }
                    });
                    const companyResponse = await axios.get(`http://localhost:8080/api/companies/${equipmentResponse.data.companyId}`, {
                        headers: { Authorization: `Bearer ${auth.token}` }
                    });
                    return {
                        ...appointment,
                        startTime: timeslotResponse.data.startTime,
                        endTime: timeslotResponse.data.endTime,
                        equipment: equipmentResponse.data,
                        company: companyResponse.data,
                        quantity: appointment.quantity // Assuming the quantity is part of the appointment data
                    };
                }));
                setAppointments(appointmentData);
            } catch (error) {
                console.error('Error fetching appointments:', error);
            }
        };

        fetchAppointments();
    }, [auth]);

    const handleCancel = async (appointmentId) => {
        try {
            setLoading(true);
            await axios.delete(`http://localhost:8080/api/appointments/${appointmentId}`, {
                headers: { Authorization: `Bearer ${auth.token}` },
                params: { userId: auth.user.id }
            });
            setLoading(false);
            setAppointments(appointments.filter(appointment => appointment.appointmentId !== appointmentId));
            toast.success('Appointment canceled successfully!');
        } catch (error) {
            setLoading(false);
            console.error('Error canceling appointment:', error);
            toast.error('Failed to cancel appointment. Please try again.');
        }
    };

    return (
        <div className="mt-4">
            <h2>Your Appointments</h2>
            <ul className="list-group">
                {appointments.length > 0 ? (
                    appointments.map((appointment) => (
                        <li key={appointment.appointmentId} className="list-group-item d-flex justify-content-between align-items-center">
                            <div>
                                <h5>Appointment ID: {appointment.appointmentId}</h5>
                                <p>Company Name: {appointment.company.companyName}</p>
                                <p>Equipment Name: {appointment.equipment.equipmentName}</p>
                                <p>Equipment Type: {appointment.equipment.equipmentType}</p>
                                <p>Quantity: {appointment.quantity}</p>
                                <p>Time: {appointment.startTime} - {appointment.endTime}</p>
                            </div>
                            <button onClick={() => handleCancel(appointment.appointmentId)} className="btn btn-danger" disabled={loading}>
                                Cancel Appointment
                            </button>
                        </li>
                    ))
                ) : (
                    <p>You have no scheduled appointments.</p>
                )}
            </ul>
            <ToastContainer />
        </div>
    );
};

export default UserAppointmentsComponent;

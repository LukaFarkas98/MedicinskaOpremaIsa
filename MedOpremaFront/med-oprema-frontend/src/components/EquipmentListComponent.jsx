import React, { useEffect, useState, useContext } from 'react';
import axios from 'axios';
import { AuthContext } from '../authentication/AuthProvider';
import AppointmentFormComponent from './AppointmentFormComponent';

const EquipmentListComponent = ({ companyId }) => {
    const [equipment, setEquipment] = useState([]);
    const { auth } = useContext(AuthContext);
    const [loading, setLoading] = useState(false);
    const [selectedEquipment, setSelectedEquipment] = useState(null);
    const [timeslots, setTimeslots] = useState([]);
    const [isFetching, setIsFetching] = useState(false);

    useEffect(() => {
        const fetchEquipment = async () => {
            try {
                setLoading(true);
                const response = await axios.get(`http://localhost:8080/api/companies/${companyId}/equipment`);
                setEquipment(response.data);
            } catch (error) {
                console.error('Error fetching equipment:', error);
            } finally {
                setLoading(false);
            }
        };

        fetchEquipment();
    }, [companyId]);

    const handleSchedule = async (equipmentId) => {
        if (isFetching) return; // Prevent multiple requests
        setIsFetching(true);
        try {
            const response = await axios.get(`http://localhost:8080/api/available/${equipmentId}`);
            const availableTimeslots = response.data.filter(slot => !slot.booked);
            setTimeslots(availableTimeslots);
            setSelectedEquipment(equipmentId);
        } catch (error) {
            console.error('Error fetching timeslots:', error);
        } finally {
            setIsFetching(false);
        }
    };

    const handleAppointmentScheduled = async (equipmentId, userId, timeslotId) => {
        try {
            setLoading(true);
            // await axios.post(
            //     'http://localhost:8080/api/appointments',
            //     { equipmentId, userId, timeslotId },
            //     { headers: { Authorization: `Bearer ${auth.token}` } }
            // );
            alert('Appointment scheduled successfully');
        } catch (error) {
            console.error('Error scheduling appointment:', error);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="mt-4">
            <h2>Equipment List</h2>
            {loading ? (
                <p>Loading...</p>
            ) : (
                <ul className="list-group mb-4">
                    {equipment.map((item) => (
                        <li
                            key={item.equipmentId}
                            className={`list-group-item d-flex justify-content-between align-items-center ${selectedEquipment === item.equipmentId ? 'list-group-item-info' : ''}`}
                            onClick={() => handleSchedule(item.equipmentId)}
                        >
                            {item.equipmentName}
                            {auth.isAuthenticated && (
                                <button className="btn btn-primary">
                                    Schedule Appointment
                                </button>
                            )}
                        </li>
                    ))}
                </ul>
            )}
            {selectedEquipment && (
                <div>
                    <h3>Available Time Slots</h3>
                    {timeslots.length > 0 ? (
                        <AppointmentFormComponent
                            companyId={companyId}
                            equipmentId={selectedEquipment}
                            userId={auth.user ? auth.user.id : null}
                            onAppointmentScheduled={handleAppointmentScheduled}
                            timeslots={timeslots}
                        />
                    ) : (
                        <p>No available time slots for this equipment.</p>
                    )}
                </div>
            )}
        </div>
    );
};

export default EquipmentListComponent;

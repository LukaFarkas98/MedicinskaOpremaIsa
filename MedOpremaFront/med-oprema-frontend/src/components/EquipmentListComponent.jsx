import React, { useEffect, useState, useContext } from 'react';
import axios from 'axios';
import { AuthContext } from '../authentication/AuthProvider';
import AppointmentFormComponent from './AppointmentFormComponent';

const EquipmentListComponent = ({ companyId }) => {
    const [equipment, setEquipment] = useState([]);
    const { auth } = useContext(AuthContext);
    const [selectedEquipment, setSelectedEquipment] = useState(null);

    useEffect(() => {
        const fetchEquipment = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/api/companies/${companyId}/equipment`);
                const allEquipment = response.data;

                // Fetch all equipment and its associated timeslots
                const equipmentWithTimeslots = await Promise.all(
                    allEquipment.map(async (item) => {
                        const timeslotResponse = await axios.get(`http://localhost:8080/api/equipment/${item.equipmentId}/timeslots`);
                        return { ...item, timeslots: timeslotResponse.data };
                    })
                );

                // Update state to include equipment with timeslots
                setEquipment(equipmentWithTimeslots);
            } catch (error) {
                console.error('Error fetching equipment:', error);
            }
        };

        fetchEquipment();
    }, [companyId]);

    const handleSchedule = (equipmentId) => {
        setSelectedEquipment(equipmentId);
    };

    const handleAppointmentScheduled = () => {
        setSelectedEquipment(null);
        alert('Appointment scheduled successfully');
    };
return(
<div className="mt-4">
            <h2>Equipment List</h2>
            <ul className="list-group mb-4">
                {equipment.map((item) => (
                    <li key={item.equipmentId} className="list-group-item d-flex justify-content-between align-items-center">
                        {item.equipmentName}
                        {auth.isAuthenticated && (
                            <>
                                {item.timeslots.length > 0 ? (
                                    <button onClick={() => handleSchedule(item.equipmentId)} className="btn btn-primary" disabled={loading}>
                                        Schedule Appointment
                                    </button>
                                ) : (
                                    <span>No available timeslots</span>
                                )}
                            </>
                        )}
                    </li>
                ))}
            </ul>
            {selectedEquipment && (
                <AppointmentFormComponent
                    companyId={companyId}
                    equipmentId={selectedEquipment}
                    userId={auth.user.id}
                    onAppointmentScheduled={handleAppointmentScheduled}
                />
            )}
        </div>
    );
};

export default EquipmentListComponent;

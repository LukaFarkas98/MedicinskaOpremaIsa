import React, { useEffect, useState, useContext } from 'react';
import axios from 'axios';
import { AuthContext } from '../authentication/AuthProvider';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const AppointmentFormComponent = ({ companyId, equipmentId, userId, onAppointmentScheduled }) => {
    const { auth } = useContext(AuthContext);
    const [timeslots, setTimeslots] = useState([]);
    const [selectedTimeslot, setSelectedTimeslot] = useState(null);
    const [quantity, setQuantity] = useState(1); // Default quantity

    useEffect(() => {
        const fetchTimeslots = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/api/available/${equipmentId}`);
                setTimeslots(response.data);
            } catch (error) {
                console.error('Error fetching timeslots:', error);
            }
        };

        fetchTimeslots();
    }, [equipmentId]);

    const handleSchedule = async () => {
        if (!selectedTimeslot) {
            alert('Please select a timeslot');
            return;
        }

        try {
            await axios.post(
                'http://localhost:8080/api/appointments',
                { equipmentId, companyId, userId, timeSlotId: selectedTimeslot.id, quantity }, // Include quantity in the payload
                { headers: { Authorization: `Bearer ${auth.token}` } }
            );
            toast.success('Appointment scheduled successfully!');
            onAppointmentScheduled();
        } catch (error) {
            console.error('Error scheduling appointment:', error);
            toast.error('Error scheduling appointment!');
        }
    };

    return (
        <div>
            <h3>Schedule an Appointment</h3>
            <select
                onChange={(e) => setSelectedTimeslot(timeslots.find(slot => slot.id === parseInt(e.target.value)))}
                className="form-select"
            >
                <option value="">Select a timeslot</option>
                {timeslots.map((slot) => (
                    <option key={slot.id} value={slot.id}>
                        {slot.startTime} - {slot.endTime}
                    </option>
                ))}
            </select>
            <input
                type="number"
                min="1"
                value={quantity}
                onChange={(e) => setQuantity(parseInt(e.target.value))}
                className="form-control mt-2"
                placeholder="Enter quantity"
            />
            <button onClick={handleSchedule} className="btn btn-primary mt-2">
                Schedule Appointment
            </button>
        </div>
    );
};

export default AppointmentFormComponent;

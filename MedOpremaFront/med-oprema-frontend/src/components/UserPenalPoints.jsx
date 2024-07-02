// src/components/UserPenalPointsComponent.jsx

import React, { useEffect, useState, useContext } from 'react';
import axios from 'axios';
import { AuthContext } from '../authentication/AuthProvider';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const UserPenalPointsComponent = () => {
    const [penalPoints, setPenalPoints] = useState([]);
    const { auth } = useContext(AuthContext);

    useEffect(() => {
        const fetchPenalPoints = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/api/penal-points/user/${auth.user.id}`, {
                    headers: { Authorization: `Bearer ${auth.token}` }
                });
                setPenalPoints(response.data);
            } catch (error) {
                console.error('Error fetching penal points:', error);
                toast.error('Failed to fetch penal points. Please try again.');
            }
        };

        fetchPenalPoints();
    }, [auth]);

    return (
        <div className="mt-4">
            <h2>Your Penal Points</h2>
            {penalPoints.length > 0 ? (
                <ul className="list-group">
                    {penalPoints.map((point, index) => (
                        <li key={index} className="list-group-item">
                            {point.timestamp} - {point.points} points
                        </li>
                    ))}
                </ul>
            ) : (
                <p>You have no penal points.</p>
            )}
            <ToastContainer />
        </div>
    );
};

export default UserPenalPointsComponent;

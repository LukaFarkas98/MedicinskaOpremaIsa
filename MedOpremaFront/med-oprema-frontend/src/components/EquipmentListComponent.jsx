import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';

const EquipmentListComponent = () => {
    const { companyId } = useParams();
    const [equipment, setEquipment] = useState([]);

    useEffect(() => {
        axios.get(`http://localhost:8080/api/companies/${companyId}/equipment`)
            .then(response => {
                setEquipment(response.data);
            })
            .catch(error => {
                console.error('There was an error fetching the equipment!', error);
            });
    }, [companyId]);

    return (
        <div>
            <h2>Equipment</h2>
            <ul>
                {equipment.map(item => (
                    <li key={item.id}>{item.equipmentName}</li>
                ))}
            </ul>
        </div>
    );
}

export default EquipmentListComponent;

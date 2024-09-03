import React, { useEffect, useState, useContext } from 'react';
import axios from 'axios';
import { Table, Container, Alert, Spinner } from 'react-bootstrap';
import { AuthContext } from '../authentication/AuthProvider';

const ComplaintHistory = () => {
    const [complaints, setComplaints] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const { auth } = useContext(AuthContext);

    useEffect(() => {
        const fetchComplaints = async () => {
            try {
                const response = await axios.get('http://localhost:8080/api/complaints', {
                    headers: {
                        Authorization: `Bearer ${auth.token}`
                    }
                });
                setComplaints(response.data);
                setLoading(false);
            } catch (err) {
                setError(err.response ? err.response.data : 'Error fetching complaints');
                setLoading(false);
            }
        };

        fetchComplaints();
    }, [auth]);

    if (loading) {
        return (
            <Container className="mt-4 text-center">
                <Spinner animation="border" />
            </Container>
        );
    }

    if (error) {
        return (
            <Container className="mt-4">
                <Alert variant="danger">
                    <h4>Error</h4>
                    <p>{error}</p>
                </Alert>
            </Container>
        );
    }

    return (
        <Container className="mt-4">
            <h2>Complaint History</h2>
            <Table striped bordered hover>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>User ID</th>
                        <th>Complaint Type</th>
                        <th>Details</th>
                        <th>Date</th>
                    </tr>
                </thead>
                <tbody>
                    {complaints.map((complaint) => (
                        <tr key={complaint.id}>
                            <td>{complaint.id}</td>
                            <td>{complaint.userId}</td>
                            <td>{complaint.companyId ? 'Company' : 'Admin'}</td>
                            <td>{complaint.details}</td>
                            <td>{new Date(complaint.createdAt).toLocaleString()}</td>
                        </tr>
                    ))}
                </tbody>
            </Table>
        </Container>
    );
};

export default ComplaintHistory;

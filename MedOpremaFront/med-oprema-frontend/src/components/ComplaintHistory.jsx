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
                const response = await axios.get(`http://localhost:8080/api/complaints/user/${auth.user.id}`, {
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
                        <th>Complaint Type</th>
                        <th>Details</th>
                        <th>Date</th>
                        <th>Response</th> {/* New column */}
                    </tr>
                </thead>
                <tbody>
                    {complaints.map((complaint) => (
                        <tr 
                        key={complaint.id}
                        style={{
                            backgroundColor: complaint.response && complaint.response.trim() !== '' ? '#d4edda' : 'inherit' // Check if response is not empty or null
                        }}
                        >
                            <td>{complaint.id}</td>
                            <td>{complaint.companyId ? 'Company' : 'Admin'}</td>
                            <td>{complaint.details}</td>
                            <td>{new Date(complaint.createdAt).toLocaleString()}</td>
                            <td>{complaint.response || 'No response yet'}</td> {/* Display response if available */}
                        </tr>
                    ))}
                </tbody>
            </Table>
        </Container>
    );
};

export default ComplaintHistory;

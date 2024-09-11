import React, { useState, useEffect, useContext } from 'react';
import axios from 'axios';
import { AuthContext } from '../authentication/AuthProvider';
import { Alert, Button, Container, Form, Row, Col } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';

const ComplaintsList = () => {
    const { auth } = useContext(AuthContext);
    const [complaints, setComplaints] = useState([]);
    const [error, setError] = useState(null);
    const [responseMessage, setResponseMessage] = useState('');
    const [selectedComplaintId, setSelectedComplaintId] = useState(null);
    const [usernames, setUsernames] = useState({});
    const navigate = useNavigate();

    useEffect(() => {
        const fetchComplaints = async () => {
            try {
                const res = await axios.get('http://localhost:8080/api/complaints', {
                    headers: { Authorization: `Bearer ${auth.token}` },
                });
                setComplaints(res.data);

                // Collect user IDs from complaints
                const userIds = [...new Set(res.data.map(complaint => complaint.userId))];

                // Fetch usernames for all user IDs
                const userPromises = userIds.map(userId => 
                    axios.get(`http://localhost:8080/api/users/${userId}`, {
                        headers: { Authorization: `Bearer ${auth.token}` },
                    })
                );

                const userResponses = await Promise.all(userPromises);
                const userMap = userResponses.reduce((acc, userRes) => {
                    acc[userRes.data.id] = userRes.data.email;
                    return acc;
                }, {});

                setUsernames(userMap);
            } catch (err) {
                setError('Error fetching complaints or user details');
            }
        };
        fetchComplaints();
    }, [auth]);

    const handleRespond = async (e, complaintId) => {
        e.preventDefault();
        try {
            await axios.put(
                `http://localhost:8080/api/complaints/respond/${complaintId}`,
                null,
                {
                    params: { response: responseMessage },
                    headers: {
                        Authorization: `Bearer ${auth.token}`,
                    },
                }
            );
            setSelectedComplaintId(null); // Close the response form after successful submission
            setResponseMessage('');
            // Optionally refetch the complaints to update the status
            fetchComplaints();
        } catch (err) {
            setError('Error responding to complaint');
        }
    };

    return (
        <Container>
            <h2 className="my-4">All Complaints</h2>
            {error && <Alert variant="danger">{error}</Alert>}
            {complaints.length === 0 ? (
                <p>No complaints to display.</p>
            ) : (
                complaints.map((complaint) => (
                    <Row 
                        key={complaint.id} 
                        className="mb-3" 
                        style={{
                            backgroundColor: complaint.response && complaint.response.trim() !== '' 
                                ? '#cce5ff' // Blue for responded complaints
                                : '#f8d7da' // Light red for unanswered complaints
                        }}
                    >
                        <Col md={8}>
                            <Alert variant="light">
                                <p>
                                    <strong>Complaint ID:</strong> {complaint.id}
                                </p>
                                <p>
                                    <strong>User:</strong> {usernames[complaint.userId] || 'Loading...'}
                                </p>
                                <p>
                                    <strong>Type:</strong> {complaint.complaintType}
                                </p>
                                <p>
                                    <strong>Details:</strong> {complaint.details}
                                </p>
                                <p>
                                    <strong>Status:</strong> {complaint.response ? 'Responded' : 'Pending'}
                                </p>
                            </Alert>
                        </Col>
                        <Col md={4}>
                            {selectedComplaintId === complaint.id ? (
                                <Form onSubmit={(e) => handleRespond(e, complaint.id)}>
                                    <Form.Group controlId={`response-${complaint.id}`}>
                                        <Form.Control
                                            as="textarea"
                                            rows={3}
                                            value={responseMessage}
                                            onChange={(e) => setResponseMessage(e.target.value)}
                                            placeholder="Write your response"
                                            required
                                        />
                                    </Form.Group>
                                    <Button type="submit" variant="primary" className="mt-2">
                                        Submit Response
                                    </Button>
                                    <Button
                                        variant="secondary"
                                        className="mt-2 ms-2"
                                        onClick={() => setSelectedComplaintId(null)}
                                    >
                                        Cancel
                                    </Button>
                                </Form>
                            ) : (
                                <Button 
                                    onClick={() => setSelectedComplaintId(complaint.id)} 
                                    disabled={complaint.response}
                                >
                                    {complaint.response ? 'Responded' : 'Respond'}
                                </Button>
                            )}
                        </Col>
                    </Row>
                ))
            )}
        </Container>
    );
};

export default ComplaintsList;

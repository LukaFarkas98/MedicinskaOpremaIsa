import React, { useState, useContext } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import { AuthContext } from '../authentication/AuthProvider';
import { Form, Button, Alert, Container, Row, Col } from 'react-bootstrap';

const ComplaintForm = () => {
    const { companyId } = useParams();
    const { auth } = useContext(AuthContext);
    const [complaintType, setComplaintType] = useState('company');
    const [details, setDetails] = useState('');
    const [response, setResponse] = useState(null);
    const [error, setError] = useState(null);

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const res = await axios.post(
                'http://localhost:8080/api/complaints',
                {
                    userId: auth.user.id,
                    companyId: complaintType === 'company' ? companyId : null,
                    adminId: complaintType === 'admin' ? companyId : null,
                    details: details,
                },
                {
                    headers: {
                        'Content-Type': 'application/json',
                        Authorization: `Bearer ${auth.token}`,
                    },
                }
            );
            setResponse(res.data);
            setError(null);
        } catch (err) {
            setError(err.response ? err.response.data : 'Error submitting complaint');
            setResponse(null);
        }
    };

    return (
        <Container>
            <Row className="justify-content-md-center">
                <Col md={6}>
                    <h2 className="my-4">Submit a Complaint</h2>
                    <Form onSubmit={handleSubmit}>
                        <Form.Group controlId="formComplaintType">
                            <Form.Label>Complaint Type</Form.Label>
                            <Form.Control
                                as="select"
                                value={complaintType}
                                onChange={(e) => setComplaintType(e.target.value)}
                            >
                                <option value="company">Company</option>
                                <option value="admin">Admin</option>
                            </Form.Control>
                        </Form.Group>
                        <Form.Group controlId="formDetails">
                            <Form.Label>Details</Form.Label>
                            <Form.Control
                                as="textarea"
                                rows={3}
                                value={details}
                                onChange={(e) => setDetails(e.target.value)}
                                required
                            />
                        </Form.Group>
                        <Button variant="primary" type="submit" className="mt-3">
                            Submit
                        </Button>
                    </Form>
                    {response && (
                        <Alert variant="success" className="mt-4">
                            <h4>Complaint Submitted</h4>
                            <p>Complaint ID: {response.id}</p>
                        </Alert>
                    )}
                    {error && (
                        <Alert variant="danger" className="mt-4">
                            <h4>Error</h4>
                            <p>{error}</p>
                        </Alert>
                    )}
                </Col>
            </Row>
        </Container>
    );
};

export default ComplaintForm;
//popcorn cheese max
//kiki chicken
//bataq
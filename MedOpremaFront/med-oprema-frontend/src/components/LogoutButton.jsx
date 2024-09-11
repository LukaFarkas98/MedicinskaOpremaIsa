// src/components/LogoutButton.jsx
import React, { useContext } from 'react';
import { Button, Spinner } from 'react-bootstrap';
import { AuthContext } from '../authentication/AuthProvider';

const LogoutButton = () => {
  const { auth, logout } = useContext(AuthContext);
  const [loading, setLoading] = React.useState(false);

  const handleLogout = () => {
    setLoading(true);
    logout();
  };

  return (
    <Button
      variant="danger"
      onClick={handleLogout}
      disabled={loading}
    >
      {loading ? <Spinner as="span" animation="border" size="sm" /> : 'Logout'}
    </Button>
  );
};

export default LogoutButton;

// src/authentication/AuthProvider.jsx
import React, { createContext, useState } from 'react';
import { useNavigate } from 'react-router-dom';

export const AuthContext = createContext();

const AuthProvider = ({ children }) => {
  //const navigate = useNavigate();
  const [auth, setAuth] = useState({
    isAuthenticated: false,
    token: null,
    user: null
  });

  const login = (token, user) => {
    setAuth({
      isAuthenticated: true,
      token,
      user
    });
    localStorage.setItem('token', token);
    localStorage.setItem('user', JSON.stringify(user));
  };

  const logout = () => {
    setAuth({
      isAuthenticated: false,
      token: null,
      user: null
    });
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    navigate('/'); // Redirect to homepage
  };

  return (
    <AuthContext.Provider value={{ auth, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export default AuthProvider;

import React, { createContext, useState } from 'react';

export const AuthContext = createContext();

const AuthProvider = ({ children }) => {
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
  };

  const logout = () => {
    setAuth({
      isAuthenticated: false,
      token: null,
      user: null
    });
    localStorage.removeItem('token');
  };

  return (
    <AuthContext.Provider value={{ auth, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export default AuthProvider;

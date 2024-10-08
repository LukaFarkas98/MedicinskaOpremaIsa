import React, {useContext, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import {AuthContext} from '../authentication/AuthProvider';

const LoginComponent = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const { login } = useContext(AuthContext);
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post('http://localhost:8080/api/login', { "email":"isasestica@gmail.com", "username":email, password:"123" });
      login(response.data.token, response.data.user);
      axios.defaults.headers.common['Authorization'] = `Bearer ${response.data.token}`;
      navigate('/'); 
    } catch (error) {
      console.error('Error during login:', error);
    }
  };


  return (
    <div>
      <h2>Login</h2>
      <form onSubmit={handleLogin}>
        <div>
          <label>Email:</label>
          <input type="email" value={email} onChange={(e) => setEmail(e.target.value)}  />
        </div>
        <div>
          <label>Password:</label>
          <input type="password" value={password} onChange={(e) => setPassword(e.target.value)}  />
        </div>
        <button type="submit">Login</button>
      </form>
    </div>
  );
};

export default LoginComponent;

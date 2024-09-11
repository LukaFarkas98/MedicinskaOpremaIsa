// src/App.jsx
import React from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import AuthProvider from './authentication/AuthProvider';
import HeaderComponent from './components/HeaderComponent';
import FooterComponent from './components/FooterComponent';
import CompanyListComponent from './components/CompanyListcomponent';
import EquipmentListComponent from './components/EquipmentListComponent';
import UserAppointmentsComponent from './components/UserAppointmentsComponent';
import UserPenalPointsComponent from './components/UserPenalPoints';
import ComplaintForm from './components/ComplaintFormComponent';
import ComplaintHistory from './components/ComplaintHistory';
import ComplaintsList from './components/ComplaintsList';
import ListUserComponent from './components/ListUserComponent';
import UserComponent from './components/UserComponent';
import LoginComponent from './components/LoginComponent';
import RegisterComponent from './components/RegisterComponent';

function App() {
  return (
    <BrowserRouter>
      <AuthProvider>
        <HeaderComponent />
        <div className='content'>
          <Routes>
            <Route path='/' element={<CompanyListComponent />} />
            <Route path='/companies/:companyId/equipment' element={<EquipmentListComponent />} />
            <Route path='/users' element={<ListUserComponent />} />
            <Route path='/add-user' element={<UserComponent />} />
            <Route path='/edit-user/:id' element={<UserComponent />} />
            <Route path='/login' element={<LoginComponent />} />
            <Route path='/register' element={<RegisterComponent />} />
            <Route path='/appointments' element={<UserAppointmentsComponent />} />
            <Route path='/penalpoints' element={<UserPenalPointsComponent />} />
            <Route path='/submit-complaint/:companyId' element={<ComplaintForm />} />
            <Route path='/complaints' element={<ComplaintHistory />} />
            <Route path='/admin/complaints-list' element={<ComplaintsList />} />
          </Routes>
        </div>
        <FooterComponent />
      </AuthProvider>
    </BrowserRouter>
  );
}

export default App;

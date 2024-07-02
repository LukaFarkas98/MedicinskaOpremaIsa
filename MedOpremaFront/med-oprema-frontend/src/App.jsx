
import FooterComponent from './components/FooterComponent'
import HeaderComponent from './components/HeaderComponent'
import ListUserComponent from './components/ListUserComponent'
import {BrowserRouter, Route, Routes} from 'react-router-dom'
import UserComponent from './components/UserComponent'
import LoginComponent from './components/LoginComponent';
import RegisterComponent from './components/RegisterComponent';
import CompanyListComponent from './components/CompanyListcomponent';
import EquipmentListComponent from './components/EquipmentListComponent';
import UserAppointmentsComponent from './components/UserAppointmentsComponent';
import UserPenalPointsComponent from './components/UserPenalPoints'
function App() {
  return (
    <>
    <div id = "root">
    <BrowserRouter>
      <HeaderComponent/>
      <div className='content'>
        <Routes>
            <Route path='/' element={<CompanyListComponent />} />
            <Route path='/companies/:companyId/equipment' element={<EquipmentListComponent />} />
            {/* //http://localhost:3000 /users */}
            <Route path = '/users' element = {<ListUserComponent/>} ></Route>
            {/* //http://localhost:3000 /add-user */}
            <Route path = '/add-user' element = {<UserComponent/>}></Route>
            {/* //http://localhost:3000 /edit-user/1 */}
            <Route path = '/edit-user/:id' element = {<UserComponent/>}></Route>
            <Route path='/login' element={<LoginComponent />} />
            <Route path='/register' element={<RegisterComponent />} />
            <Route path="/appointments" element={<UserAppointmentsComponent />} />
            <Route path="/penalpoints" element={<UserPenalPointsComponent />} /> {/* New Route */}
        </Routes>
      </div>
      <FooterComponent/>
    </BrowserRouter>
    </div>
    </>
  )
}

export default App

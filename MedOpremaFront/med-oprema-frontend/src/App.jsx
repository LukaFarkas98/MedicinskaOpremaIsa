
import FooterComponent from './components/FooterComponent'
import HeaderComponent from './components/HeaderComponent'
import ListUserComponent from './components/ListUserComponent'
import {BrowserRouter, Route, Routes} from 'react-router-dom'
import UserComponent from './components/UserComponent'
function App() {
  return (
    <>
    <div id = "root">
    <BrowserRouter>
      <HeaderComponent/>
      <div className='content'>
        <Routes>
        
            {/* //http://localhost:3000  */}
            <Route path = '/' element = {<ListUserComponent></ListUserComponent>}></Route>
            {/* //http://localhost:3000 /users */}
            <Route path = '/users' element = {<ListUserComponent/>} ></Route>
            {/* //http://localhost:3000 /add-user */}
            <Route path = '/add-user' element = {<UserComponent/>}></Route>
            {/* //http://localhost:3000 /edit-user/1 */}
            <Route path = '/edit-user/:id' element = {<UserComponent/>}></Route>
        </Routes>
      </div>
      <FooterComponent/>
    </BrowserRouter>
    </div>
    </>
  )
}

export default App

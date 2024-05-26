import React, {useEffect, useState} from 'react'
import 'bootstrap/dist/css/bootstrap.min.css'
import { deleteUser, listUsers } from '../services/userService'
import { useNavigate, useParams} from 'react-router-dom'
const ListUserComponent = () => {
    
      const[users, setUsers] = useState([])

      const navigator = useNavigate();

      const {id} = useParams();
      useEffect(() =>{
        getAllUsers();
      }, []);
      
      function getAllUsers(){
        listUsers().then((response) => {
            console.log('API response:', response.data); 
            setUsers(response.data);
        }).catch(error => {
            console.error(error)
        });
      }

      function addNewUser(){
        navigator('/add-user')
      }

      function updateUser(id){
        navigator(`/edit-user/${id}`)
      }

      function removeUser(id){

        deleteUser(id).then((response) => {
            console.log(response.data);
            getAllUsers();
        }).catch(error => {
            console.error(error)
        });
      }
   
  return (
    <div className="container">
        <h2 className="my-4">List of Users</h2>
        <button className="btn btn-primary mb-2" onClick={addNewUser}>Add user</button>
        <table className="table table-striped table-bordered">
        <thead>
            <tr>
                <th>ID</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Email</th>
                <th>Password</th>
                <th>City</th>
                <th>Country</th>
                <th>Phone</th>
                <th>Profession</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            {
                users.map(user => 
                <tr key={user.id}>
                    <td>{user.id}</td>
                    <td>{user.firstName}</td>
                    <td>{user.lastName}</td>
                    <td>{user.email}</td>
                    <td>{user.password}</td>
                    <td>{user.city}</td>
                    <td>{user.country}</td>
                    <td>{user.phone}</td>
                    <td>{user.profession}</td>
                    <td>
                        <button className='btn btn-info' onClick = {() => updateUser(user.id)}>Update</button>
                        <button className='btn btn-danger' onClick = {() => removeUser(user.id)} style={{marginLeft: '10px'}}>Delete</button>
                    </td>
                </tr>)
            }
        </tbody>
        </table>
    </div>
  )
}

export default ListUserComponent
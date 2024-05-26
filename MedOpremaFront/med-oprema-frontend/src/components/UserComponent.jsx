import React, { useEffect, useState } from 'react';
import { createUser , getUser, updateUser} from '../services/userService';
import { useNavigate, useParams} from 'react-router-dom';

const UserComponent = () => {

    const navigator = useNavigate();
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [city, setCity] = useState('');
    const [country, setCountry] = useState('');
    const [phone, setPhone] = useState('');
    const [profession, setProfession] = useState('');

    const [user, setUser] = useState([]);

    const {id} = useParams();

    useEffect(() => {
        if(id){
            getUser(id).then((response) => {
                setFirstName(response.data.firstName);
                setLastName(response.data.lastName);
                setEmail(response.data.email);
                setPassword(response.data.password);
                setCity(response.data.city);
                setCountry(response.data.country);
                setPhone(response.data.phone);
                setProfession(response.data.profession);
            }).catch(error => {
                console.error(error);
            })
        }
    })

    const handleFirstNameChange = (e) => setFirstName(e.target.value);
    const handleLastNameChange = (e) => setLastName(e.target.value);
    const handleEmailChange = (e) => setEmail(e.target.value);
    const handlePasswordChange = (e) => setPassword(e.target.value);
    const handleCityChange = (e) => setCity(e.target.value);
    const handleCountryChange = (e) => setCountry(e.target.value);
    const handlePhoneChange = (e) => setPhone(e.target.value);
    const handleProfessionChange = (e) => setProfession(e.target.value);
    
    const [errors, setErrors] = useState(
    { firstName: '',
    lastName: '',
    email: '',
    password: '',
    city: '',
    country: '',
    phone: '',
    profession: ''})

    function saveOrUpdateUser(e) {
        e.preventDefault();
        const user = { firstName, lastName, email, password, city, country, phone, profession};
        console.log(user);
        let valid = validateForm()
        if(valid){ 
            if(id){
                updateUser(id, user).then((response) => {
                    console.log(response.data)
                    navigator('/users')
                }).catch(error => {
                    console.error(error)
                })
            }else{
                createUser(user).then((response) => {
                    console.log(response.data);
                    navigator('/users');
                }).catch(error => {
                    console.error(error)
                })
            }      
        }else{

        }
    }

    function validateForm(){
        let valid = true;

        const errorsCopy = {... errors}

        if(!firstName.trim()){
            errorsCopy.firstName = 'First name is required'
            valid = false
        }
        if(!lastName.trim()){
            errorsCopy.lastName = 'Last name is required'
            valid = false
        }if(!password.trim()){
            errorsCopy.password = 'password is required'
            valid = false
        }
        if(!email.trim()){
            errorsCopy.email = 'email is required'
            valid = false
        }

        setErrors(errorsCopy)

        return valid
    }
      function pageTitleChange(){
        if(id){
            return <h2 className='text-center mt-3'>Update User</h2>
        }else{
            return <h2 className='text-center mt-3'>Add User</h2>
        }
      }

    return (
        <div className='container mt-5'>
            <div className='row justify-content-center'>
                <div className='card col-md-6'>
                {
                    pageTitleChange()
                }
                    
                    <div className='card-body'>
                        <form>
                            <div className='form-group mb-3'>
                                <label className='form-label'>First Name:</label>
                                <input
                                    type='text'
                                    name='firstName'
                                    placeholder='Enter User First Name'
                                    defaultValue={firstName}
                                    className= {`form-control ${errors.firstName ? 'is-invalid' : ''}`}
                                    onChange={handleFirstNameChange}
                                  
                                />
                                  {errors.firstName && <div className='invalid-feedback'>{errors.firstName}</div>}
                            </div>
                            <div className='form-group mb-3'>
                                <label className='form-label'>Last Name:</label>
                                <input
                                    type='text'
                                    placeholder='Enter User Last Name'
                                    name='lastName'
                                    defaultValue={lastName}
                                    className={`form-control ${errors.lastName ? 'is-invalid' : ''}`}
                                    onChange={handleLastNameChange}
                                />
                                {errors.lastName && <div className='invalid-feedback'>{errors.lastName}</div>}
                            </div>
                            <div className='form-group mb-3'>
                                <label className='form-label'>Email:</label>
                                <input
                                    type='email'
                                    placeholder='Enter User Email'
                                    name='email'
                                    defaultValue={email}
                                    className={`form-control ${errors.email ? 'is-invalid' : ''}`}
                                    onChange={handleEmailChange}
                                />
                                  {errors.email && <div className='invalid-feedback'>{errors.email}</div>}
                            </div>
                            <div className='form-group mb-3'>
                                <label className='form-label'>Password:</label>
                                <input
                                    type='password'
                                    placeholder='Enter User Password'
                                    name='password'
                                    defaultValue={password}
                                    className={`form-control ${errors.password ? 'is-invalid' : ''}`}
                                    onChange={handlePasswordChange}
                                />
                                  {errors.password && <div className='invalid-feedback'>{errors.password}</div>}
                            </div>
                            <div className='form-group mb-3'>
                                <label className='form-label'>City:</label>
                                <input
                                    type='text'
                                    placeholder='Enter User City'
                                    name='city'
                                    defaultValue={city}
                                    className='form-control'
                                    onChange={handleCityChange}
                                />
                            </div>
                            <div className='form-group mb-3'>
                                <label className='form-label'>Country:</label>
                                <input
                                    type='text'
                                    placeholder='Enter User Country'
                                    name='country'
                                    defaultValue={country}
                                    className='form-control'
                                    onChange={handleCountryChange}
                                />
                            </div>
                            <div className='form-group mb-3'>
                                <label className='form-label'>Phone:</label>
                                <input
                                    type='text'
                                    placeholder='Enter User Phone'
                                    name='phone'
                                    defaultValue={phone}
                                    className='form-control'
                                    onChange={handlePhoneChange}
                                />
                            </div>
                            <div className='form-group mb-3'>
                                <label className='form-label'>Profession:</label>
                                <input
                                    type='text'
                                    placeholder='Enter User Profession'
                                    name='profession'
                                    defaultValue={profession}
                                    className='form-control'
                                    onChange={handleProfessionChange}
                                />
                            </div>
                            <button className='btn btn-success mt-3' onClick = {saveOrUpdateUser}>Submit</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default UserComponent;

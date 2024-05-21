import axios from "axios"

const REST_API_BASE_URL = 'http://localhost:8080/api/users';
//const REST_API_ADD_USER_URL()

export const listUsers = () =>  axios.get(REST_API_BASE_URL);

export const createUser = (user) => axios.post(REST_API_BASE_URL, user)

export const getUser = (userId) => axios.get(REST_API_BASE_URL + '/' + userId)
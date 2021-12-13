import axios from "axios";
import jwt_decode from "jwt-decode";

const API_URL = "http://localhost:8080/api/users/";

export const signIn = async (username, password) => {
    const response = await axios.post(API_URL + "login", { username, password }); 
    localStorage.setItem("user", response.data.jwt);
    return jwt_decode(response.data.jwt);
}

export const signUp = async (username, email, password) => {
    const response = await axios.post(API_URL + "signup", { username, email, password });
    localStorage.setItem("user", response.data.jwt);
    return jwt_decode(response.data.jwt);
}

export const signOut = () => localStorage.removeItem("user");

export const getJwtDetails = () => {
    let user = localStorage.getItem('user');
    if (user) {
        return jwt_decode(user);
    }
}
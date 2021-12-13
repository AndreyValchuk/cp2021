import axios from "axios";
import { getAuthHeader } from "./authHeader";

const API_URL = "http://localhost:8080/api/movies/";

export const getMovies = async (title) => {
    let url = API_URL;

    if (title !== undefined) {
        url += "?title=" + title;
    }

    return (await axios.get(url)).data;
}

export const getMovie = async (id) => (await axios.get(API_URL + id)).data;

export const createMovie = async (movie) => (await axios.post(API_URL, movie, { headers: getAuthHeader() })).data;

export const updateMovie = async (movie, id) => await axios.put(API_URL + id, movie, { headers: getAuthHeader() });

export const deleteMovie = async (id) => await axios.delete(API_URL + id, { headers: getAuthHeader() });

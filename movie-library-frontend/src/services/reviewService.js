import axios from "axios";
import { getAuthHeader } from "./authHeader";

const API_URL = "http://localhost:8080/api/movies/";

export const createReview = async (review, movieId) => (await axios.post(API_URL + movieId + "/reviews", review, { headers: getAuthHeader() })).data;

export const deleteReview = async (reviewId) => await axios.delete(API_URL + "reviews/" + reviewId, { headers: getAuthHeader() });

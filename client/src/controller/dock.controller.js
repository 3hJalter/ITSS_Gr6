import axios from "axios";
import {API_URL} from "../config";

export const getAllDocksController = async () => {
  try {
    return await axios.get(`${API_URL}/dock/list`);
  } catch (error) {
    console.log(error.message);
  }
};

export const searchDockController = async (data) => {
  try {
    return await axios.get(`${API_URL}/dock/search?keyword=${data}`);
  } catch (error) {
    console.log(error.message);
  }
}

export const getDockById = async (data) => {
  try {
    return await axios.get(`${API_URL}/dock/info?id=${data}`);
  } catch (error) {
    console.log(error.message);
  }
}
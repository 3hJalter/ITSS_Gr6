import axios from "axios";
import { API_URL } from "../config";

export const getAllDocksController = async () => {
  try {
    const response = await axios.get(`${API_URL}/dock/list`);
    return response;
  } catch (error) {
    console.log(error.message);
  }
};

export const searchDockController = async (data) => {
  try {
    const response = await axios.get(`${API_URL}/dock/search?keyword=${data}`);
    return response;
  } catch (error) {
    console.log(error.message);
  }
}
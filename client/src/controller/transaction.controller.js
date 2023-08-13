import axios from "axios";

const API_URL = "http://localhost:8000";

export const getActiveTransactionController = async (data) => {
  try {
    const response = await axios.get(`${API_URL}/transaction/active?customerId=1`);
    return response;
  } catch (error) {
    console.log(error.message);
  }
}

export const pauseTransactionController = async (transactionId) => {
  try {
    const response = await axios.get(`${API_URL}/transaction/pause?transactionId=${transactionId}`);
    return response;
  } catch (error) {
    console.log(error.message);
  }
}

export const resumeTransactionController = async (transactionId) => {
  try {
    const response = await axios.get(`${API_URL}/transaction/unPause?transactionId=${transactionId}`);
    return response;
  } catch (error) {
    console.log(error.message);
  }
}


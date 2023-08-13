import axios from "axios";
import { CUSTOMER_ID } from "../config";
import { API_URL } from "../config";

export const createTransactionController = async (data) => {
  try {
    console.log(
      `${API_URL}/transaction/create?customerId=${CUSTOMER_ID}&barcode=${data.barcode}&transactionType=${data.transactionType}`
    );
    const response = await axios.get(
      `${API_URL}/transaction/create?customerId=${CUSTOMER_ID}&barcode=${data.barcode}&transactionType=${data.transactionType}`
    );
    return response;
  } catch (error) {
    console.log(error.message);
  }
};

export const getActiveTransactionController = async () => {
  try {
    const response = await axios.get(`${API_URL}/transaction/active?customerId=${CUSTOMER_ID}`);
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
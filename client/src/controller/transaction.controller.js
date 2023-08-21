import axios from "axios";
import {API_URL, CUSTOMER_ID} from "../config";

export const createTransactionController = async (data) => {
  try {
    return await axios.get(
        `${API_URL}/transaction/create?customerId=${CUSTOMER_ID}&barcode=${data.barcode}&transactionType=${data.transactionType}&cardholderName=${data.cardHolderName}&cardNumber=${data.cardNumber}&issueBank=${data.issueBank}&month=${data.month}&year=${data.year}&securityCode=${data.securityCode}`
    );
  } catch (error) {
    console.log(error.message);
  }
};

export const getActiveTransactionController = async () => {
  try {
    return await axios.get(`${API_URL}/transaction/active?customerId=${CUSTOMER_ID}`);
  } catch (error) {
    console.log(error.message);
  }
}

export const pauseTransactionController = async (transactionId) => {
  try {
    return await axios.get(`${API_URL}/transaction/pause?transactionId=${transactionId}`);
  } catch (error) {
    console.log(error.message);
  }
}

export const resumeTransactionController = async (transactionId) => {
  try {
    return await axios.get(`${API_URL}/transaction/unPause?transactionId=${transactionId}`);
  } catch (error) {
    console.log(error.message);
  }
}
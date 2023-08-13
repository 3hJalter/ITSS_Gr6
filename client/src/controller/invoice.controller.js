import axios from 'axios';
import { API_URL } from '../config';

export const createInvoiceController = async (data) => {
  try {
    const response = await axios.get(`${API_URL}/invoice/create?transactionId=${data.transactionId}&dockId=${data.dockId}`);
    return response;
  } catch (error) {
    console.log(error.message);
  }
}
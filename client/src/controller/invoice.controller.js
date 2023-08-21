import axios from "axios";
import {API_URL} from "../config";

export const createInvoiceController = async (data) => {
  try {
    return await axios.get(
        `${API_URL}/invoice/create?transactionId=${data.transactionId}&dockId=${data.dockId}&cardNumber=${data.cardNumber}&cardholderName=${data.cardholderName}&issueBank=${data.issueBank}&month=${data.month}&year=${data.year}&securityCode=${data.securityCode}`
    );
  } catch (error) {
    console.log(error.message);
  }
};

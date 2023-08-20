import axios from "axios";
import { API_URL } from "../config";

export const getBikeByDockController = async (data) => {
  try {
    const response = await axios.get(`${API_URL}/bike/byDockId?dockId=${data}`);
    return response;
  } catch (error) {
    console.log(error.message);
  }
};

export const getBikeFromBarcodeController = async (data) => {
  try {
    const response = await axios.get(`${API_URL}/bike/barcodeInfo?barcode=${data}`);
    return response;
  } catch (error) {
    console.log(error.message);
  }
};
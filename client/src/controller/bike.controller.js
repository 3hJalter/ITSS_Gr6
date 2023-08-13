import axios from "axios";
import { API_URL } from "../config";

export const getBikeByDockController = async (data) => {
  try {
    return await axios.get(`${API_URL}/bike/byDockId?dockId=${data}`);
  } catch (error) {
    console.log(error.message);
  }
};

export const getBikeFromBarcodeController = async (data) => {
  try {
    console.log(`${API_URL}/bike/barcodeInfo?barcode=${data}`);
    return await axios.get(`${API_URL}/bike/barcodeInfo?barcode=${data}`);
  } catch (error) {
    console.log(error.message);
  }
};
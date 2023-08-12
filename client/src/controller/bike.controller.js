import axios from "axios";

const API_URL = "http://localhost:8000/bike";

// export const createBookController = async (data) => {
//   try {
//     return await axios.post(API_URL, data);
//   } catch (error) {
//     if (error.response && error.response.data && error.response.data.message) {
//       const errorMessage = error.response.data.message;
//       alert(errorMessage);
//     } else {
//       console.error(error);
//       alert("An error occurred while saving the book data");
//     }
//   }
// };

export const getAllBikesController = async () => {
  try {
    console.log(API_URL);
    return await axios.get(`${API_URL}/list`);
  } catch (error) {
    console.log(error.message);
  }
};

export const getBikeByIdController = async (data) => {
  try {
    return await axios.get(`${API_URL}/info?id=${data}`);
  } catch (error) {
    console.log(error.message);
  }
};

// export const updateBookController = async (data, id) => {
//   try {
//     return await axios.patch(`${API_URL}/${id}`, data);
//   } catch (error) {
//     if (error.response && error.response.data && error.response.data.message) {
//       const errorMessage = error.response.data.message;
//       alert(errorMessage);
//     } else {
//       console.error(error);
//       alert("An error occurred while saving the new book data");
//     }
//   }
// };

// export const deleteBookController = async (id) => {
//   try {
//     return await axios.delete(`${API_URL}/${id}`);
//   } catch (error) {
//     console.log(error.message);
//   }
// };

// export const searchDockController = async (data) => {
//   try {
//     return await axios.get(`${API_URL}/search?keyword=${data}`);
//   } catch (error) {
//     console.log(error.message);
//   }
// }
// export const getBikeByDockController = async (data) => {
//   try {
//     return await axios.get(`${API_URL}/bike/byDockId?dockId=${data}`);
//   } catch (error) {
//     console.log(error.message);
//   }
// }
import { TableHead, TableBody, TableCell } from "@mui/material";
import { useEffect, useState } from "react";
import {
  getAllBikesController,
  getBikeByIdController,
  getBikeByDockController,
  rentBikeController
} from "../../controller/bike.controller.js";
import {useLocation} from "react-router-dom";
import {
  StyledTable,
  StyledTableHead,
  StyledTableCell,
  StyledTableBody,
} from "../muiStyled.js";
import { DeleteButton, UpdateButton, RentBikeButton } from "../button/Button.jsx";

const GetAllBikes = () => {
  const location = useLocation();
  const bikeData = location.state;
  const [bikes, setBikes] = useState([]);
  // useEffect(() => {
  //   getAllBikeHandler();
  // }, []);

  const getAllBikeHandler = async () => {
    const response = await getBikeByDockController();
    setBikes(response.data.object);
  };

  // export const rentBikeController = async (data) => {
  //   try {
  //     return await axios.post(`${API_URL}/transaction/create`, data);
  //   } catch (error) {
  //     console.log(error.message);
  //   }
  // }

  const rentBikeHandler = async (id) => {
    const data = {
      bikeId: id,
      customerId: "1",
    }
    const response = await rentBikeController(data);
      alert(response.data.message)
  }

  return (
    <StyledTable>
      <TableHead>
        <StyledTableHead>
          <StyledTableCell style={{ borderRadius: "15px 0 0 0" }}>
            Bike ID
          </StyledTableCell>
          <StyledTableCell>Bike name</StyledTableCell>
          <StyledTableCell>Category</StyledTableCell>
          <StyledTableCell>Dock name</StyledTableCell>
          <StyledTableCell>Battery</StyledTableCell>
          <StyledTableCell>Image</StyledTableCell>
          <TableCell style={{ borderRadius: "0 15px 0 0", width: "10px" }}>
            Actions
          </TableCell>
        </StyledTableHead>
      </TableHead>


      <TableBody>
        {bikeData.map((bike) => (
          <StyledTableBody key={bike.bikeId}>
            <StyledTableCell
              style={{
                maxWidth: "120px",
                wordWrap: "break-word",
                fontWeight: "bold",
                padding: "25px",
              }}
            >
              {bike.bikeId}
            </StyledTableCell>
            <StyledTableCell
              style={{
                wordWrap: "break-word",
                maxWidth: "100px",
              }}
            >
              {bike.bikeName}
            </StyledTableCell>
            <StyledTableCell
              style={{
                wordWrap: "break-word",
                maxWidth: "100px",
              }}
            >
              {bike.category.categoryName}
            </StyledTableCell>
            <StyledTableCell
              style={{
                wordWrap: "break-word",
                maxWidth: "100px",
              }}
            >
              {bike.dock.dockName}
            </StyledTableCell>
            <StyledTableCell
              style={{
                wordWrap: "break-word",
                maxWidth: "100px",
              }}
            >
              {bike.battery}
            </StyledTableCell>

            <StyledTableCell>
              <img
                src={bike.image}
                alt={bike.image}
                style={{
                  display: "block",
                  margin: "0 auto",
                  width: "200px",
                  height: "130px",
                  wordWrap: "break-word",
                }}
              />
            </StyledTableCell>

            <StyledTableCell>
              <RentBikeButton onClick={() => rentBikeHandler(bike.bikeId)} />
            </StyledTableCell>

          </StyledTableBody>
        ))}
      </TableBody>
    </StyledTable>
  );
};

export default GetAllBikes;

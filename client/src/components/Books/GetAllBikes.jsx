import { TableHead, TableBody, TableCell } from "@mui/material";
import { useEffect, useState } from "react";
import {
  getAllBikesController,
  getBikeByIdController
} from "../../controller/bike.controller.js";
import {
  StyledTable,
  StyledTableHead,
  StyledTableCell,
  StyledTableBody,
} from "../muiStyled.js";
import { DeleteButton, UpdateButton } from "../button/Button.jsx";

const GetAllBikes = () => {
  const [bikes, setBikes] = useState([]);
  useEffect(() => {
    getAllBikeHandler();
  }, []);

  const getAllBikeHandler = async () => {
    const response = await getAllBikesController();
    setBikes(response.data.object);
  };

  // const deleteBookHandler = async (id) => {
  //   if (confirm(`Are you sure you want to delete`)) {
  //     await deleteBookController(id);
  //   } else return;
  //   await getAllBikeHandler().then(() => {});
  // };

  return (
    <StyledTable>
      <TableHead>
        <StyledTableHead>
          <StyledTableCell style={{ borderRadius: "15px 0 0 0" }}>
            Bike ID
          </StyledTableCell>
          <StyledTableCell>Bike name</StyledTableCell>
          <StyledTableCell>Category Id</StyledTableCell>
          <StyledTableCell>Dock Id</StyledTableCell>
          <StyledTableCell>Battery</StyledTableCell>
          <StyledTableCell>Image</StyledTableCell>
          <TableCell style={{ borderRadius: "0 15px 0 0", width: "10px" }}>
            Actions
          </TableCell>
        </StyledTableHead>
      </TableHead>


      <TableBody>
        {bikes.map((bike) => (
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
                  width: "100px",
                  height: "150px",
                  wordWrap: "break-word",
                }}
              />
            </StyledTableCell>
            <StyledTableCell
              style={{
                wordWrap: "break-word",
                maxWidth: "100px",
              }}
            >
              {bike.address}
            </StyledTableCell>

            {/* <TableCell>
              <UpdateButton book={bike} />
              <DeleteButton onClick={() => deleteBookHandler(bike._id)} />
            </TableCell> */}

          </StyledTableBody>
        ))}
      </TableBody>
    </StyledTable>
  );
};

export default GetAllBikes;

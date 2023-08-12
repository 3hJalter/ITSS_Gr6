import {
  TableHead,
  TableBody,
  TableCell,
  TextField,
  Button,
} from "@mui/material";
import { useEffect, useState } from "react";
import {
  getAllBikesController,
  getBikeByIdController,
  getBikeByDockController,
  rentBikeController,
} from "../controller/bike.controller.js";
import { useLocation } from "react-router-dom";
import {
  StyledTable,
  StyledTableHead,
  StyledTableCell,
  StyledTableBody,
} from "./muiStyled.js";
import {
  DeleteButton,
  UpdateButton,
  RentBikeButton,
} from "./button/Button.jsx";

import { useNavigate } from "react-router-dom";

const GetAllBikes = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const bikeData = location.state;
  const [barcode, setBarcode] = useState("");
  // const [bikes, setBikes] = useState([]);
  // useEffect(() => {
  //   getAllBikeHandler();
  // }, []);

  const depositHandler = async () => {
    if (barcode.trim() === "") {
      alert("Please enter a barcode.");
      return;
    }

    // const data = {
    //   customerId: 1,
    //   barcode: barcode,
    // };

    // const response = await rentBikeController(data);
    // alert(response.data.message);

    navigate("/deposit", { state: barcode });
  };

  return (
    <>
      <TextField
        label="Enter Barcode"
        variant="outlined"
        fullWidth
        value={barcode}
        onChange={(e) => setBarcode(e.target.value)}
        style={{ marginTop: "200px", width: "50%" }}
      />
      <Button
        variant="contained"
        color="primary"
        onClick={depositHandler}
        style={{ marginTop: "20px", width: "15%" }}
      >
        Deposite
      </Button>
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
            <TableCell style={{ borderRadius: "0 15px 0 0", width: "10px" }}>Image</TableCell>
            {/* <TableCell style={{ borderRadius: "0 15px 0 0", width: "10px" }}>
              Actions
            </TableCell> */}
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
                {bike.battery ? bike.battery + "%" : "Not available"}
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

              {/* <StyledTableCell>
                <RentBikeButton onClick={() => rentBikeHandler(bike.bikeId)} />
              </StyledTableCell> */}
            </StyledTableBody>
          ))}
        </TableBody>
      </StyledTable>
    </>
  );
};

export default GetAllBikes;

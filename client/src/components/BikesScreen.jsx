import {
  TableHead,
  TableBody,
  TableCell,
  TextField,
  Button,
} from "@mui/material";
import { useState } from "react";
import { getBikeFromBarcodeController } from "../controller/bike.controller.js";
import { useLocation } from "react-router-dom";
import {
  StyledTable,
  StyledTableHead,
  StyledTableCell,
  StyledTableBody,
} from "./style/muiStyled.js";

import { useNavigate } from "react-router-dom";

const BikesScreen = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const bikeData = location.state;
  const [barcode, setBarcode] = useState("");

  const depositHandler = async () => {
    if (barcode.trim() === "") {
      alert("Please enter a barcode.");
      return;
    }

    const response = await getBikeFromBarcodeController(barcode);
    const bikeData = response.data.object;
    if (response.data.message !== "Successful") {
      alert(response.data.message);
      return;
    }
    navigate("/deposit", { state: bikeData });
  };

  const backHandler = () => {
    navigate("/docks");
  };

  return (
    <>
      <TextField
        label="Enter Barcode"
        variant="outlined"
        fullWidth
        value={barcode}
        onChange={(e) => setBarcode(e.target.value)}
        style={{ marginTop: "120px", width: "50%" }}
      />
      <div className="grid grid-cols-2 gap-10 p-8">
        <Button variant="contained" color="primary" onClick={backHandler}>
          Back
        </Button>
        <Button variant="contained" color="primary" onClick={depositHandler}>
          Deposit
        </Button>
      </div>
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
            <StyledTableCell>Barcode</StyledTableCell>
            <TableCell style={{ borderRadius: "0 15px 0 0" }}>Image</TableCell>
          </StyledTableHead>
        </TableHead>

        <TableBody>
          {bikeData.map((bike) => (
            <StyledTableBody key={bike.bikeId}>
              <StyledTableCell
                style={{
                  maxWidth: "80px",
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
                  maxWidth: "50px",
                }}
              >
                {bike.dock.dockName}
              </StyledTableCell>
              <StyledTableCell
                style={{
                  wordWrap: "break-word",
                  maxWidth: "50px",
                }}
              >
                {bike.battery ? bike.battery + "%" : "Not available"}
              </StyledTableCell>

              <StyledTableCell
                style={{
                  wordWrap: "break-word",
                  maxWidth: "250px",
                }}
              >
                {bike.barcode}
              </StyledTableCell>

              <StyledTableCell>
                <img
                  src={bike.image}
                  alt={bike.image}
                  style={{
                    display: "block",
                    margin: "0 auto",
                    width: "200px",
                    height: "140px",
                    wordWrap: "break-word",
                  }}
                />
              </StyledTableCell>
            </StyledTableBody>
          ))}
        </TableBody>
      </StyledTable>
    </>
  );
};

export default BikesScreen;

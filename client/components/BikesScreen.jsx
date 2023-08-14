import { TableHead, TableBody, TableCell, TextField } from "@mui/material";
import { useState } from "react";
import { getBikeFromBarcodeController } from "../controller/bike.controller.js";
import { getActiveTransactionController } from "../controller/transaction.controller.js";
import { useLocation, useNavigate } from "react-router-dom";
import {
  StyledTable,
  StyledTableHead,
  StyledTableCell,
  StyledTableBody,
} from "./style/muiStyled.js";

import { DepositButton, BackButton } from "./button/Button.jsx";

import { toast } from "react-toastify";

const BikesScreen = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const bikeData = location.state;
  const [barcode, setBarcode] = useState("");

  const depositHandler = async () => {
    const activeTransaction = await getActiveTransactionController();
    console.log(activeTransaction.data);
    if (activeTransaction.data.code === "200_T0") {
      toast.error("Customer already has an active transaction.");
      return;
    }

    if (barcode.trim() === "") {
      toast.error("Please enter a barcode.");
      return;
    }

    const response = await getBikeFromBarcodeController(barcode);
    const bikeData = response.data.object;
    if (response.data.message !== "Successful") {
      toast.error(response.data.message);
      return;
    }
    toast.success("Deposit successfully");

    console.log(bikeData.dock);
    if (bikeData.dock == null) {
      toast.error("Bike is already rented")
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
        <BackButton onClick={backHandler} />
        <DepositButton onClick={depositHandler} />
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
            <StyledTableCell style={{ minWidth: "280px" }}>
              Barcode
            </StyledTableCell>
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
                  maxWidth: "120px",
                }}
              >
                {bike.category.categoryName}
              </StyledTableCell>
              <StyledTableCell
                style={{
                  wordWrap: "break-word",
                  maxWidth: "80px",
                }}
              >
                {bike.dock.dockName}
              </StyledTableCell>
              <StyledTableCell
                style={{
                  wordWrap: "break-word",
                  maxWidth: "200px",
                }}
              >
                {bike.battery ? bike.battery + "%" : "No Battery"}
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
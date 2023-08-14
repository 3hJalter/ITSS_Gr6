import { React, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import { createTransactionController } from "../controller/transaction.controller";
import { DepositButton, CancelButton } from "./button/Button";
import { TextField, Typography, Select, MenuItem } from "@mui/material";
import CreditCardIcon from "@mui/icons-material/CreditCard";

function DepositScreen() {
  const navigate = useNavigate();
  const location = useLocation();
  const bikeData = location.state;
  const [transactionType, setTransactionType] = useState("normal");

  const cancelHandler = () => {
    navigate(`/docks`);
  };

  const transactionTypeHandler = (e) => {
    setTransactionType(e.target.value);
  };

  const depositHandler = async () => {
    const data = {
      customerId: 1,
      barcode: bikeData.barcode,
      transactionType: transactionType,
    };

    const response = await createTransactionController(data);
    const transaction = response.data;
    if (transaction.message !== "Successful") {
      toast.error(transaction.message);
      return;
    }
    toast.success("Deposit successfully");
    navigate("/active-transaction", { state: { transaction } });
  };

  return (
    <>
      <div className="grid items-center justify-center w-2/3 shadow-3xl mt-32 mb-40 rounded-2xl p-10">
        <div className="text-center font-bold text-4xl">Bike information</div>
        <div className=" grid grid-cols-2 items-center justify-center m-6 gap-10">
          <div className="flex w-full p-4 shadow-3xl h-full rounded-xl">
            <img
              src={bikeData.image}
              alt="image"
              style={{ maxWidth: "100%" }}
            />
          </div>
          <div className="card w-full h-full shadow-3xl px-8 py-7 text-2xl rounded-2xl">
            <div className="text-3xl font-bold">
              Bike name: {bikeData.bikeName}
            </div>
            <div>Category: {bikeData.category.categoryName}</div>
            <div>Dock: {bikeData.dock.dockName}</div>
            <div>Bike price: {bikeData.category.bikePrice}</div>
            <div>Deposite rate: {bikeData.category.depositRate}</div>
            <div>Rent price: {bikeData.category.rentPrice}</div>
          </div>
        </div>
        {/* ============================================== */}
        <div className="text-center font-bold text-2xl m-4">
          Select Transaction Type:
        </div>
        <div className="flex items-center justify-center w-full gap-4">
          <Select value={transactionType} onChange={transactionTypeHandler}>
            <MenuItem value="normal">Normal</MenuItem>
            <MenuItem value="24h">24 Hours</MenuItem>
          </Select>

          {/* =============================================== */}
        </div>
        <div className="mt-4">
          <Typography
            variant="h5"
            sx={{ fontWeight: "bold" }}
            className="text-center"
          >
            Enter Payment Details <CreditCardIcon />
          </Typography>
          <div className="flex justify-center items-center">
            <form className="w-1/2">
              <TextField
                label="Card Number"
                fullWidth
                variant="outlined"
                margin="normal"
                placeholder="Enter card number"
              />
              <TextField
                label="Cardholder Name"
                fullWidth
                variant="outlined"
                margin="normal"
                placeholder="Enter cardholder name"
              />
              <TextField
                label="CVV Number"
                fullWidth
                variant="outlined"
                margin="normal"
                placeholder="Enter CVV number"
              />
            </form>
          </div>
        </div>
        {/* ================================================ */}
        <div className=" flex items-center justify-center text-center w-full gap-10">
          <div className="flex items-center justify-center text-center w-full gap-10 mt-6">
            <CancelButton onClick={cancelHandler} />
            <DepositButton onClick={depositHandler} />
          </div>
        </div>
      </div>
    </>
  );
}

export default DepositScreen;

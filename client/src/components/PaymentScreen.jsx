import React, { useState } from "react";
import { Typography, TextField } from "@mui/material";
import CreditCardIcon from "@mui/icons-material/CreditCard";
import { useLocation, useNavigate } from "react-router-dom";
import { createInvoiceController } from "../controller/invoice.controller";
import { BackButton, PayButton } from "./button/Button";
import { toast } from "react-toastify";

function PaymentScreen() {
  const navigate = useNavigate();
  const location = useLocation();
  const activeTransaction = location.state.activeTransaction;
  const [dockId, setDockId] = useState("");

  const createInvoiceHandler = () => {
    const data = {
      transactionId: activeTransaction.transaction.transactionId,
      dockId: dockId,
    };
    createInvoiceController(data);
    toast.success("Payment successfully");
    navigate("/docks");
  };

  const toHoursAndMinutes = (totalMinutes) => {
    const hours = Math.floor(totalMinutes / 60);
    const minutes = totalMinutes % 60;

    return `${padToTwoDigits(hours)}h${padToTwoDigits(minutes)}m`;
  };

  const padToTwoDigits = (num) => {
    return num.toString().padStart(2, "0");
  };

  const backHandler = () => {
    navigate("/active-transaction");
  };

  return (
    <>
      <div className="grid grid-cols-2 gap-10 mt-40 w-3/5">
        {/* First div */}
        <div className=" flex flex-col shadow-3xl justify-center items-center rounded-xl">
          <div className="text-2xl font-semibold m-4">Invoice Information</div>
          <div>
            <img src={activeTransaction.transaction.bike.image} alt="" />
          </div>
          <div className="text-xl font-semibold m-8">
            <div>
              Transaction ID: {activeTransaction.transaction.transactionId}
            </div>
            <div>
              Total time: {toHoursAndMinutes(activeTransaction.timeRent)}
            </div>
            <div>Total price: {activeTransaction.currentPay}VND</div>
          </div>
        </div>

        {/* ============================================ */}

        {/* Second div */}
        <div className="rounded-xl shadow-3xl p-8">
          <div>
            <Typography
              variant="h5"
              sx={{ fontWeight: "bold", mb: 3 }}
              className="text-center"
            >
              Enter Payment Details
            </Typography>
            <div className="flex justify-center items-center">
              <CreditCardIcon style={{ fontSize: "48px" }} />
            </div>
            <form>
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
              <TextField
                label="Return Dock ID"
                fullWidth
                variant="outlined"
                margin="normal"
                placeholder="Enter Dock ID to return bike"
                value={dockId}
                onChange={(event) => setDockId(event.target.value)}
              />
            </form>
          </div>
        </div>

        {/* ============================================ */}
      </div>
      <div className="mb-32">
        <div className="grid grid-cols-2 gap-32 m-10">
          <BackButton onClick={backHandler} />
          <PayButton onClick={createInvoiceHandler} />
        </div>
      </div>
    </>
  );
}

export default PaymentScreen;

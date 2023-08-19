import React, { useState } from "react";
import { Typography, TextField } from "@mui/material";
import CreditCardIcon from "@mui/icons-material/CreditCard";
import { useLocation, useNavigate } from "react-router-dom";
import { createInvoiceController } from "../controller/invoice.controller";
import { BackButton, PayButton } from "./button/Button";
import { toast } from "react-toastify";

function PaymentScreen() {
  const [cardNumber, setCardNumber] = useState(0);
  const [cardHolderName, setCardHolderName] = useState("");
  const [issueBank, setIssueBank] = useState("");
  const [cvvNumber, setCvvNumber] = useState(0);
  const [expireMonth, setExpireMonth] = useState(0);
  const [expireYear, setExpireYear] = useState(0);

  const navigate = useNavigate();
  const location = useLocation();
  const activeTransaction = location.state.activeTransaction;
  const [dockId, setDockId] = useState("");

  const createInvoiceHandler = () => {
    const data = {
      transactionId: activeTransaction.transaction.transactionId,
      dockId: dockId,
      cardNumber: cardNumber,
      cardHolderName: cardHolderName,
      issueBank: issueBank,
      securityCode: cvvNumber,
      month: expireMonth,
      year: expireYear,
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
      <div className="grid grid-cols-2 gap-10 mt-40">
        {/* First div */}
        <div className=" flex flex-col shadow-3xl justify-center items-center rounded-xl">
          <div className="text-2xl font-semibold m-4">Invoice Information</div>
          <div>
            <img src={activeTransaction.transaction.bike.image} alt="bike logo" />
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
            {/* <form>
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
            </form> */}
            <div className="flex justify-center items-center">
            <form className="flex">
              <TextField
                value={cardNumber}
                onChange={(e) => setCardNumber(e.target.value)}
                label="Card Number"
                fullWidth
                variant="outlined"
                margin="normal"
                placeholder="Enter card number"
                style={{ marginRight: "10px" }} // Add margin-right for spacing
              />
              <TextField
                value={cardHolderName}
                onChange={(e) => setCardHolderName(e.target.value)}
                label="Cardholder Name"
                fullWidth
                variant="outlined"
                margin="normal"
                placeholder="Enter cardholder name"
              />
            </form>
          </div>
          <div className="flex justify-center items-center">
            <form className="flex">
              <TextField
                value={issueBank}
                onChange={(e) => setIssueBank(e.target.value)}
                label="Issue Bank"
                fullWidth
                variant="outlined"
                margin="normal"
                placeholder="Enter bank"
                style={{ marginRight: "10px" }} // Add margin-right for spacing
              />
              <TextField
                value={cvvNumber}
                onChange={(e) => setCvvNumber(e.target.value)}
                label="CVV Number"
                fullWidth
                variant="outlined"
                margin="normal"
                placeholder="Enter CVV number"
              />
            </form>
          </div>
          <div className="flex justify-center items-center">
            <form className="flex">
              <TextField
                value={expireMonth}
                onChange={(e) => setExpireMonth(e.target.value)}
                label="Month"
                fullWidth
                variant="outlined"
                margin="normal"
                placeholder="Expire month"
                style={{ marginRight: "10px" }} // Add margin-right for spacing
              />
              <TextField
                value={expireYear}
                onChange={(e) => setExpireYear(e.target.value)}
                label="Year"
                fullWidth
                variant="outlined"
                margin="normal"
                placeholder="Expire year"
              />
              </form>

            </div>
            <TextField
                label="Return Dock ID"
                fullWidth
                variant="outlined"
                margin="normal"
                placeholder="Enter Dock ID to return bike"
                value={dockId}
                onChange={(event) => setDockId(event.target.value)}
              />
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

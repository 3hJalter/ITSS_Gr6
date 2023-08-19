import React, { useState, useEffect } from "react";
import { Typography, TextField } from "@mui/material";
import CreditCardIcon from "@mui/icons-material/CreditCard";
import { useLocation, useNavigate } from "react-router-dom";
import { createInvoiceController } from "../controller/invoice.controller";
import { BackButton, PayButton } from "./button/Button";
import { toast } from "react-toastify";
import { getActiveTransactionController } from "../controller/transaction.controller";

import Dialog from "@mui/material/Dialog";
import DialogTitle from "@mui/material/DialogTitle";
import DialogContent from "@mui/material/DialogContent";
import DialogActions from "@mui/material/DialogActions";
import Button from "@mui/material/Button";

function PaymentScreen() {
  const [cardNumber, setCardNumber] = useState(0);
  const [cardHolderName, setCardHolderName] = useState("");
  const [issueBank, setIssueBank] = useState("");
  const [cvvNumber, setCvvNumber] = useState(0);
  const [expireMonth, setExpireMonth] = useState(0);
  const [expireYear, setExpireYear] = useState(0);

  const navigate = useNavigate();
  // const location = useLocation();
  // const activeTransaction = location.state.activeTransaction;
  const [dockId, setDockId] = useState("");
  const [isConfirmationOpen, setIsConfirmationOpen] = useState(false);

  const initialTransactionData = {
    transaction: {
      transactionId: 0,
      customer: {
        customerId: 0,
        username: "admin",
      },
      createdAt: 0,
      deposit: 0,
      bike: {
        bikeId: 0,
        bikeName: "",
        category: {
          categoryId: 0,
          categoryName: "",
          bikePrice: 0,
          depositRate: 0,
          rentPrice: 0,
          priceMultiple: 0,
        },
        dock: null,
        image: "",
        barcode: "",
      },
      status: "",
      transactionType: "",
      minuteUsed: 0,
      lastPause: 0,
    },
    currentPay: 0,
    timeRent: 0,
  };
  const [activeTransaction, setActiveTransaction] = useState(
    initialTransactionData
  );

  useEffect(() => {
    getActiveTransaction();
    // pauseHandler();
    // resumeHandler();
  }, []);

  const getActiveTransaction = async () => {
    const response = await getActiveTransactionController();
    const transaction = response.data.object;
    if (response.data.message !== "Successful") {
      toast.success(response.data.message);
      return;
    }
    setActiveTransaction(transaction);
  };

  const createInvoiceHandler = async () => {
    const data = {
      transactionId: activeTransaction.transaction.transactionId,
      dockId: dockId,
      cardNumber: cardNumber,
      cardholderName: cardHolderName,
      issueBank: issueBank,
      securityCode: cvvNumber,
      month: expireMonth,
      year: expireYear,
    };
    console.log(data);
    const response = await createInvoiceController(data);
    console.log(response);
    if (response.data.message !== "Successful") {
      toast.error(response.data.message);
      return;
    }
    toast.success(response.data.message);
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

  const openConfirmationDialog = () => {
    setIsConfirmationOpen(true);
  };

  const closeConfirmationDialog = () => {
    setIsConfirmationOpen(false);
  };

  return (
    <>
      <div className="grid grid-cols-2 gap-10 mt-40 w-3/5">
        {/* First div */}
        <div className=" flex flex-col shadow-3xl justify-center items-center rounded-xl">
          <div className="text-2xl font-semibold m-4">Invoice Information</div>
          <div className="w-4/5">
            <img
              src={activeTransaction.transaction.bike.image}
              alt="bike logo"
            />
          </div>
          <div className="m-8">
            <div className="text-2xl font-semibold p-1 text-center">
              Transaction ID: {activeTransaction.transaction.transactionId}
            </div>
            <div className="text-2xl font-semibold p-1 text-center">
              Total time: {toHoursAndMinutes(activeTransaction.timeRent)}
            </div>
            <div className="text-2xl font-semibold p-1 text-center">
              Total price: {activeTransaction.currentPay}VND
            </div>
            <div className="text-md font-semibold pt-2">
              (You will not be charged if you return the bike within 10 minutes)
            </div>
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
              <form className="flex w-full">
                <TextField
                  value={cardNumber}
                  onChange={(e) => setCardNumber(e.target.value)}
                  label="Card Number"
                  fullWidth
                  variant="outlined"
                  margin="normal"
                  placeholder="Enter card number"
                  style={{ marginRight: "10px" }} // Add margin-right for spacing
                  inputProps={{ maxLength: 16 }}
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
              <form className="flex w-full">
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
                  inputProps={{ maxLength: 3 }}
                />
              </form>
            </div>
            <div className="flex justify-center items-center">
              <form className="flex w-full">
                <TextField
                  value={expireMonth}
                  onChange={(e) => setExpireMonth(e.target.value)}
                  label="Month"
                  fullWidth
                  variant="outlined"
                  margin="normal"
                  placeholder="Expire month"
                  style={{ marginRight: "10px" }} // Add margin-right for spacing
                  inputProps={{ maxLength: 2 }}
                />
                <TextField
                  value={expireYear}
                  onChange={(e) => setExpireYear(e.target.value)}
                  label="Year"
                  fullWidth
                  variant="outlined"
                  margin="normal"
                  placeholder="Expire year"
                  inputProps={{ maxLength: 2 }}
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
          <PayButton onClick={openConfirmationDialog} />
        </div>
      </div>

      {/* Confirmation Dialog */}
      <Dialog open={isConfirmationOpen} onClose={closeConfirmationDialog}>
        <DialogTitle>
          Are you sure you want to proceed with the payment?
        </DialogTitle>
        <DialogContent>
          <div>
            Total payment: {activeTransaction.currentPay}VND.
          </div>
          <div>
            After payment, you will receive the deposit refund of {activeTransaction.transaction.deposit}VND.
          </div>
          <div>
            Please return the bike to the dock {dockId}
          </div>
        </DialogContent>
        <DialogActions>
          <Button
            onClick={closeConfirmationDialog}
            color="error"
            variant="outlined"
          >
            Cancel
          </Button>
          <Button onClick={createInvoiceHandler} color="primary" variant="contained">
            Confirm
          </Button>
        </DialogActions>
      </Dialog>
    </>
  );
}

export default PaymentScreen;

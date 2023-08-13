import { React, useState, useEffect } from "react";
import { Button } from "@mui/material";
import { useNavigate } from "react-router-dom";
import {
  getActiveTransactionController,
  pauseTransactionController,
  resumeTransactionController,
} from "../controller/transaction.controller";

function ActiveTransaction() {
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
  const [pauseButtonText, setPauseButtonText] = useState("Pause");

  useEffect(() => {
    getActiveTransaction();
    pauseHandler();
    resumeHandler();
  }, []);

  const getActiveTransaction = async () => {
    const response = await getActiveTransactionController();
    const transaction = response.data.object;
    if (response.data.message !== "Successful") {
      alert(response.data.message);
      return;
    }
    setActiveTransaction(transaction);
  };

  const pauseHandler = () => {
    // activeTransaction.transaction.status = "paused";
    setPauseButtonText("Resume");
    const transactionId = activeTransaction.transaction.transactionId;
    pauseTransactionController(transactionId);
    getActiveTransaction();
  };

  const resumeHandler = () => {
    // activeTransaction.transaction.status = "active";
    setPauseButtonText("Pause");
    const transactionId = activeTransaction.transaction.transactionId;
    resumeTransactionController(transactionId);
    getActiveTransaction();
  };

  const payHandler = () => {
    console.log("payHandler");
  };

  return (
    <>
      <div className="w-3/4">
        <div className="text-center font-bold text-4xl mt-28">
          Active Transaction
        </div>

        {/* ======================================================================= */}
        <div className=" grid grid-cols-2 items-center justify-center gap-10 p-12">
          <div className="card w-full shadow-3xl px-8 py-7 text-2xl rounded-2xl h-full flex justify-center items-center">
            <div>
              <img
                src={activeTransaction.transaction.bike.image}
                alt="bike image"
              />
            </div>
          </div>
          {/* =================================================================================== */}
          <div className="card w-full shadow-3xl px-8 py-7 text-2xl rounded-2xl h-full">
            <div className="text-3xl font-bold">
              Transaction Id: {activeTransaction.transaction.transactionId}
            </div>
            <div className="text-3xl font-bold">
              Bike Id: {activeTransaction.transaction.bike.bikeId}
            </div>
            <div className="text-3xl font-bold">
              Customer Id: {activeTransaction.transaction.customer.customerId}
            </div>
            <div className="text-3xl font-bold">
              Customer Name: {activeTransaction.transaction.customer.username}
            </div>
            <div className="text-3xl font-bold">
              Transaction Type: {activeTransaction.transaction.transactionType}
            </div>
            <div className="text-3xl font-bold">
              Status: {activeTransaction.transaction.status}
            </div>
            <div className="text-3xl font-bold">
              Deposit: {activeTransaction.transaction.deposit}
            </div>
            <div className="text-3xl font-bold">
              Deposit rate:{" "}
              {activeTransaction.transaction.bike.category.depositRate}
            </div>
            <div className="text-3xl font-bold">
              Minute Used: {activeTransaction.transaction.minuteUsed}
            </div>
            <div className="text-3xl font-bold">
              Last Pause: {activeTransaction.transaction.lastPause}
            </div>
            <div className="text-3xl font-bold">
              Current Pay: {activeTransaction.currentPay}
            </div>
            <div className="text-3xl font-bold">
              Time Rent: {activeTransaction.timeRent}
            </div>
          </div>
        </div>
        {/* ======================================================================= */}

        <div className="flex items-center justify-center w-full gap-4 mb-40">
          {activeTransaction.transaction.status === "active" ? (
            <Button
              variant="contained"
              style={{ width: "15%" }}
              onClick={pauseHandler}
            >
              <div className="text-2xl">{pauseButtonText}</div>
            </Button>
          ) : (
            <Button
              variant="contained"
              style={{ width: "15%" }}
              onClick={resumeHandler}
            >
              <div className="text-2xl">{pauseButtonText}</div>
            </Button>
          )}

          <Button
            variant="contained"
            style={{ width: "15%" }}
            onClick={payHandler}
          >
            <div className="text-2xl">Pay</div>
          </Button>
        </div>
      </div>
    </>
  );
}

export default ActiveTransaction;

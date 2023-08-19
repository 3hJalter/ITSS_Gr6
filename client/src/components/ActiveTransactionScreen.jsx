import { React, useState, useEffect } from "react";
import { toast } from "react-toastify";
import { useNavigate } from "react-router-dom";
import {
  getActiveTransactionController,
  pauseTransactionController,
  resumeTransactionController,
} from "../controller/transaction.controller";

import {
  PauseButton,
  ResumeButton,
  PayButton,
  CancelButton,
} from "./button/Button";

function ActiveTransactionScreen() {
  const navigate = useNavigate();
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
    navigate("/payment", { state: { activeTransaction } });
  };

  const timeLastPause = new Date(
    activeTransaction.transaction.lastPause
  ).toLocaleString();

  const toHoursAndMinutes = (totalMinutes) => {
    const hours = Math.floor(totalMinutes / 60);
    const minutes = totalMinutes % 60;

    return `${padToTwoDigits(hours)}h${padToTwoDigits(minutes)}m`;
  };

  const padToTwoDigits = (num) => {
    return num.toString().padStart(2, "0");
  };

  return (
    <>
      <div className="w-5/6">
        <div className="text-center font-bold text-4xl mt-28">
          Active Transaction
        </div>

        {/* ======================================================================= */}
        <div className="grid grid-cols-2 items-center justify-center gap-10 p-12">
          <div className="card w-full shadow-3xl px-8 py-7 text-2xl rounded-2xl h-full flex justify-center items-center">
            <div>
              <img
                src={activeTransaction.transaction.bike.image}
                alt="Bike image"
              />
            </div>
          </div>
          {/* =================================================================== */}
          <div className="card w-full shadow-3xl px-8 py-7 text-2xl rounded-2xl h-full">
            <div className="text-3xl font-bold">
              Transaction ID: {activeTransaction.transaction.transactionId}
            </div>
            <div className="text-3xl font-bold">
              Bike ID: {activeTransaction.transaction.bike.bikeId}
            </div>
            <div className="text-3xl font-bold">
              Customer ID: {activeTransaction.transaction.customer.customerId}
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
              Deposit: {activeTransaction.transaction.deposit}VND
            </div>
            {/* <div className="text-3xl font-bold">
              Deposit rate:{" "}
              {activeTransaction.transaction.bike.category.depositRate}
            </div> */}
            <div className="text-3xl font-bold">
              Last Pause: {timeLastPause}
            </div>
            <div className="text-3xl font-bold">
              Current Pay: {activeTransaction.currentPay}
            </div>
            <div className="text-3xl font-bold">
              Time Rent: {toHoursAndMinutes(activeTransaction.timeRent)}
            </div>
          </div>
        </div>
        {/* ======================================================================= */}
        <div className="flex items-center justify-center w-full gap-20 mb-40">
          <CancelButton onClick={() => navigate("/docks")} />
          {activeTransaction.transaction.status === "active" ? (
            <PauseButton onClick={pauseHandler} text={pauseButtonText} />
          ) : (
            <ResumeButton onClick={resumeHandler} text={pauseButtonText} />
          )}
          <PayButton onClick={payHandler} />
        </div>
      </div>
    </>
  );
}

export default ActiveTransactionScreen;

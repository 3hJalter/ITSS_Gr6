import { React, useState } from "react";
import { Button } from "@mui/material";
import { useLocation, useNavigate } from "react-router-dom";
import { createTransactionController } from "../controller/transaction.controller";

function DepositScreen() {
  const navigate = useNavigate();
  const location = useLocation();
  const bikeData = location.state;
  const [transactionType, setTransactionType] = useState("normal");

  const cancelHandler = () => {
    navigate("/docks/");
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
      alert(transaction.message);
      return;
    }
    console.log("response", response);
    console.log("response.data", response.data);
    alert("Deposit successfully");
    navigate("/active-transaction", { state: { transaction } });
  };

  return (
    <>
      <div className="grid items-center justify-center w-1/2 shadow-3xl mt-32 mb-40 rounded-2xl p-8">
        <div className="text-center font-bold text-4xl">Bike information</div>
        <div className=" grid grid-cols-2 items-center justify-center mt-10 gap-10 p-12">
          <div className="card w-full p-4">
            <img
              src={bikeData.image}
              alt="image"
              style={{ maxWidth: "100%" }}
            />
          </div>
          <div className="card w-full shadow-3xl px-8 py-7 text-2xl rounded-2xl">
            {/* <div className="text-3xl font-bold">Bike Id: {bikeData.bikeId}</div> */}
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
        <div className="text-center font-bold text-2xl mt-6">
          Select Transaction Type:
        </div>
        <div className="flex items-center justify-center w-full gap-4">
          <select
            value={transactionType}
            onChange={transactionTypeHandler}
            className="py-2 px-4 text-xl border rounded-md"
          >
            <option value="normal">Normal</option>
            <option value="24h">24 Hours</option>
          </select>
        </div>
        {/* ================================================ */}
        <div className=" flex items-center justify-center text-center w-full gap-10">
          <div className="flex items-center justify-center text-center w-full gap-10 mt-6">
            <Button variant="outlined" color="primary" onClick={cancelHandler}>
              <div className="text-2xl">Cancel</div>
            </Button>
            <Button variant="outlined" color="primary" onClick={depositHandler}>
              <div className="text-2xl">Deposit</div>
            </Button>
          </div>
        </div>
      </div>
    </>
  );
}

export default DepositScreen;

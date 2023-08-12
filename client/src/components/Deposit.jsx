import React from "react";
import { Button } from "@mui/material";
import { useLocation, useNavigate } from "react-router-dom";
import { rentBikeController } from "../controller/bike.controller";

function Deposit() {
  const navigate = useNavigate();
  const location = useLocation();
  const bikeData = location.state;
  console.log("bike", bikeData);

  const depositHandler = async () => {
    const data = {
      customerId: 1,
      barcode: bikeData.barcode,
    };

    const response = await rentBikeController(data);
    alert("Deposit successfully");

    navigate("/docks");
  };

  return (
    <>
      <div className="grid items-center justify-center w-1/2 shadow-3xl mt-32 rounded-2xl p-8">
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
        <div className="text-center w-full">
          <Button
            variant="outlined"
            color="primary"
            onClick={depositHandler}
          >
            <div className="text-2xl">Deposite</div>
          </Button>
        </div>
      </div>
    </>
  );
}

export default Deposit;

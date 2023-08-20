import React from "react";
import { useNavigate } from "react-router-dom";

function HomeScreen() {
  const navigate = useNavigate();
  const viewDocksHandler = () => {
    navigate("/docks");
  };

  return (
    <div
      className="mt-8"
      style={{
        background: `url(./images/bg.jpg) no-repeat center center`,
        backgroundSize: "100% 100%",
        height: "100vh",
        width: "100%",
        display: "flex",
        alignItems: "center",
        textAlign: "center",
      }}
    >
      <div style={{ marginLeft: "6%" }}>
        <h1 style={{ fontSize: "2.2rem", color: "#000", fontWeight: "bolder" }}>
          Welcome to EcoBike Rental System
        </h1>
        <p style={{ fontSize: "1.4rem", color: "#000", fontWeight: "bold" }}>
          Explore the city with our bikes
        </p>

        <button
          className="font-semibold rounded-xl text-xl text-white hover:scale-110 transition p-4 m-4 bg-primary hover:bg-white hover:text-primary"
          onClick={viewDocksHandler}
        >
          Get Started
        </button>
      </div>
    </div>
  );
}

export default HomeScreen;

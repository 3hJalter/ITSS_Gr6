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
        background: `url(./images/home-image.jpg) no-repeat center center`,
        backgroundSize: "100% 100%",
        height: "100vh",
        width: "100%",
        display: "flex",
        alignItems: "center",
        textAlign: "center",
      }}
    >
      <div style={{ marginLeft: "8%" }}>
        <h1 style={{ fontSize: "2.2rem", color: "#fff", fontWeight: "bolder" }}>
          Welcome to EcoBike Rental System
        </h1>
        <p style={{ fontSize: "1.4rem", color: "#fff", fontWeight: "bold" }}>
          Explore the city with our bikes
        </p>

        <button
          className="font-semibold rounded-xl text-xl hover:scale-110 transition p-4 m-4 bg-white hover:bg-primary hover:text-white"
          onClick={viewDocksHandler}
        >
          Get Started
        </button>
      </div>
    </div>
  );
}

export default HomeScreen;

import React from "react";
import { Button } from "@mui/material";
import { useNavigate } from "react-router-dom";

function Home() {
  const navigate = useNavigate();
  const viewDocksHandler = () => {
    navigate("/docks");
  };

  return (
    <div
      className="mt-8"
      style={{
        background: `url(./images/home-image.jpg) no-repeat center center`,
        backgroundSize: "100% 100%", // Adjust this line
        height: "100vh",
        width: "100%",
        display: "flex",
        alignItems: "center",
        // justifyContent: 'center',
        textAlign: "center",
      }}
    >
      <div style={{ marginLeft: "12%" }}>
        <h1 style={{ fontSize: "2.5rem", color: "#fff", fontWeight: "bolder" }}>
          Welcome to EcoBike Rental System
        </h1>
        <p style={{ fontSize: "1.3rem", color: "#fff", fontWeight: "bold" }}>
          Explore the city with our bikes
        </p>

        <Button
          variant="contained"
          style={{
            backgroundColor: "#fff",
            color: "#000",
            fontWeight: "bold",
            fontSize: "1.2rem",
            padding: "1rem 2rem",
            marginTop: "2rem",
            borderRadius: "1.5rem",
          }}
          onClick={viewDocksHandler}
        >
          Get Started
        </Button>
      </div>
    </div>
  );
}

export default Home;

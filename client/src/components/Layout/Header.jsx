import React, { useEffect, useRef } from "react";
import { useNavigate } from "react-router-dom";
import { Header, Tabs } from "../style/muiStyled.js";
import { Toolbar, Button, Typography } from "@mui/material";
import { getActiveTransactionController } from "../../controller/transaction.controller.js";
import { toast } from "react-toastify";

const NavBar = () => {
  const header = useRef();
  const navigate = useNavigate();
  useEffect(() => {
    let lastScrollPosition = 0;

    const scrollHandler = () => {
      const currentScrollPosition = window.scrollY;
      const scrollDirection =
        currentScrollPosition > lastScrollPosition ? "down" : "up";
      header.current.style.transform = `translateY(${
        scrollDirection === "down" ? "-100%" : "0%"
      })`;
      lastScrollPosition = currentScrollPosition;
    };

    window.addEventListener("scroll", scrollHandler);

    return () => window.removeEventListener("scroll", scrollHandler);
  }, []);

  const getActiveTransactionHandler = async () => {
    const response = await getActiveTransactionController();
    if (response.data.code === "400_T0") {
      toast.error(response.data.message); // transaction does not exist
      return;
    }
    navigate("/active-transaction");
  };

  return (
    <div
      ref={header}
      style={{
        position: "fixed",
        width: "100%",
        marginBottom: "20px",
        transition: "transform 0.2s ease-in-out",
      }}
    >
      <Header
        position="static"
        sx={{
          width: "100%",
        }}
      >
        <Toolbar>
          <Tabs to="/">
            <div style={{ display: "flex", alignItems: "center" }}>
              <img
                src="http://localhost:5173/images/ecobike.png"
                alt="logo"
                style={{ width: "80px" }}
              />
              <Typography variant="h6" component="div" sx={{ marginLeft: 2 }}>
                <h1 className="font-bold text-xl">EcoBike Rental System</h1>
              </Typography>
            </div>
          </Tabs>
          <div className="flex gap-10 ml-auto mr-12">
            <Button color="inherit" onClick={getActiveTransactionHandler}>
              Active Transaction
            </Button>
            <Button color="inherit">Home</Button>
            <Button color="inherit">About</Button>
            <Button color="inherit">Service</Button>
            <Button color="inherit">Contact</Button>
          </div>
        </Toolbar>
      </Header>
    </div>
  );
};

export default NavBar;

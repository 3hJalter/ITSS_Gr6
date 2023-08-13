import { Toolbar } from "@mui/material";
import { Header, Tabs } from "../style/muiStyled.js";
import { useEffect, useRef, React } from "react";
const NavBar = () => {
  const header = useRef();
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
            <img
              src="http://localhost:5173/images/ecobike.png"
              alt="logo"
              style={{ width: "80px" }}
            />
          </Tabs>
          {/* <Tabs to="/home">Home</Tabs> */}
          {/* <Tabs to="/about">About</Tabs> */}
          {/* <Tabs to="/contact">Contact</Tabs> */}
        </Toolbar>
      </Header>
    </div>
  );
};
export default NavBar;

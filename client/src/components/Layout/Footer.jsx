import React, { useEffect, useRef } from "react";
import { useLocation } from "react-router-dom";
import { BottomNavigation, BottomNavigationAction } from "@mui/material";
import GitHubIcon from "@mui/icons-material/GitHub";
import FacebookIcon from "@mui/icons-material/Facebook";
import InstagramIcon from "@mui/icons-material/Instagram";
import EmailIcon from "@mui/icons-material/Email";
import PinterestIcon from "@mui/icons-material/Pinterest";

function Footer() {
  let location = useLocation();
  const footer = useRef();
  useEffect(() => {
    let lastScrollPosition = 0;
    footer.current.style.transform = "translateY(0%)";
    const scrollHandler = () => {
      const currentScrollPosition = window.scrollY;
      const scrollDirection =
        currentScrollPosition >= lastScrollPosition ? "down" : "up";
      footer.current.style.transform = `translateY(${
        scrollDirection === "down" ? "0%" : "100%"
      })`;
      lastScrollPosition = currentScrollPosition;
    };

    window.addEventListener("scroll", scrollHandler);

    return () => window.removeEventListener("scroll", scrollHandler);
  }, [location]);

  return (
    <div
      ref={footer}
      style={{
        position: "fixed",
        width: "100%",
        left: 0,
        bottom: 0,
        transition: "transform 0.2s ease-in-out",
      }}
    >
      <div
        style={{
          display: "flex",
          justifyContent: "space-between",
          alignItems: "center",
          backgroundColor: "#4d5a76",
          height: "60px",
          padding: "0 20px",
        }}
      >
        <div style={{ display: "flex" }}>
          <BottomNavigation
            style={{ backgroundColor: "inherit", height: "60px" }}
          >
            {/* <BottomNavigationAction
              icon={<GitHubIcon />}
              href=""
              target="_blank"
              rel="noopener noreferrer"
              style={{ color: "white" }}
            />
            <BottomNavigationAction
              icon={<FacebookIcon />}
              href=""
              target="_blank"
              rel="noopener noreferrer"
              style={{ color: "white" }}
            />
            <BottomNavigationAction
              icon={<InstagramIcon />}
              href=""
              target="_blank"
              rel="noopener noreferrer"
              style={{ color: "white" }}
            />
            <BottomNavigationAction
              icon={<PinterestIcon />}
              href=""
              target="_blank"
              rel="noopener noreferrer"
              style={{ color: "white" }}
            />
            <BottomNavigationAction
              icon={<EmailIcon />}
              href=""
              target="_blank"
              rel="noopener noreferrer"
              style={{ color: "white" }}
            /> */}
          </BottomNavigation>
        </div>
        <div
          style={{
            textAlign: "right",
            color: "white",
            fontSize: "16px",
          }}
        >
          &copy; {new Date().getFullYear()}{" "}
          <a
            href=""
            style={{ color: "white" }}
          >
            ITSS Group 6
          </a>
          . All rights reserved.
        </div>
      </div>
    </div>
  );
}

export default Footer;

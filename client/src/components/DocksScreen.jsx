import { TableHead, TableBody, TableCell, TextField } from "@mui/material";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import {
  getAllDocksController,
  searchDockController,
} from "../controller/dock.controller.js";

import {
  getBikeByDockController
} from "../controller/bike.controller.js";

import {
  StyledTable,
  StyledTableHead,
  StyledTableCell,
  StyledTableBody,
} from "./style/muiStyled.js";
import { ViewButton } from "./button/Button.jsx";

const DocksScreen = () => {
  const navigate = useNavigate();
  const [docks, setDocks] = useState([]);
  const [searchQuery, setSearchQuery] = useState("");
  useEffect(() => {
    getAllDocksHandler();
  }, []);

  const getAllDocksHandler = async () => {
    const response = await getAllDocksController();
    setDocks(response.data.object);
  };

  const searchDockHandler = async (event) => {
    const query = event.target.value;
    setSearchQuery(query);

    if (query === "") {
      getAllDocksHandler();
      return;
    }

    const response = await searchDockController(query);
    setDocks(response.data.object);
  };

  const getBikeByDockHandler = async (id) => {
    const response = await getBikeByDockController(id);
    const bikeData = response.data.object;
    navigate(`/dock/${id}/list-bike`, { state: bikeData });
  };

  return (
    <>
      <TextField
        label="Search Dock"
        variant="outlined"
        fullWidth
        value={searchQuery}
        onChange={searchDockHandler}
        style={{ marginTop: "120px", width: "50%" }}
      />
      {/* ================================================= */}
      <StyledTable>
        <TableHead>
          <StyledTableHead>
            <StyledTableCell
              style={{ borderRadius: "15px 0 0 0", width: "150px" }}
            >
              Dock ID
            </StyledTableCell>
            <StyledTableCell style={{ width: "180px" }}>
              Dock name
            </StyledTableCell>
            <StyledTableCell style={{ width: "300px" }}>
              Address
            </StyledTableCell>

            <StyledTableCell style={{ width: "250px" }}>Image</StyledTableCell>
            <TableCell style={{ borderRadius: "0 15px 0 0", width: "150px" }}>
              Action
            </TableCell>
          </StyledTableHead>
        </TableHead>

        <TableBody>
          {docks.map((dock) => (
            <StyledTableBody key={dock.dockId}>
              <StyledTableCell
                style={{
                  maxWidth: "100px",
                  wordWrap: "break-word",
                  fontWeight: "bold",
                  padding: "25px",
                }}
              >
                {dock.dockId}
              </StyledTableCell>
              <StyledTableCell
                style={{
                  wordWrap: "break-word",
                  maxWidth: "80px",
                }}
              >
                {dock.dockName}
              </StyledTableCell>
              <StyledTableCell
                style={{
                  wordWrap: "break-word",
                  maxWidth: "100px",
                }}
              >
                {dock.address}
              </StyledTableCell>

              <StyledTableCell>
                <img
                  src={dock.image}
                  alt={dock.image}
                  style={{
                    display: "block",
                    margin: "0 auto",
                    width: "200px",
                    height: "120px",
                    wordWrap: "break-word",
                  }}
                />
              </StyledTableCell>

              <TableCell>
                <div className="justify-center items-center flex">
                  <ViewButton
                    onClick={() => getBikeByDockHandler(dock.dockId)}
                  />
                </div>
              </TableCell>
            </StyledTableBody>
          ))}
        </TableBody>
      </StyledTable>
    </>
  );
};
export default DocksScreen;

import { TableHead, TableBody, TableCell, TextField } from "@mui/material";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import {
  getAllDocksController,
  // deleteBookController,
  searchDockController,
  getBikeByDockController,
} from "../controller/dock.controller.js";
import {
  StyledTable,
  StyledTableHead,
  StyledTableCell,
  StyledTableBody,
} from "./muiStyled.js";
import { DeleteButton, UpdateButton, ViewButton } from "./button/Button.jsx";

const GetAllDocks = () => {
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

  // const deleteBookHandler = async (id) => {
  //   if (confirm(`Are you sure you want to delete`)) {
  //     await deleteBookController(id);
  //   } else return;
  //   await getAllDocksHandler().then(() => {});
  // };

  const searchDockHandler = async (event) => {
    const query = event.target.value;
    setSearchQuery(query);

    if (query === "") {
      getAllDocksHandler();
      return;
    }

    // Call the searchDockController and await its response
    const response = await searchDockController(query);

    // Update the docks state with the new search results
    setDocks(response.data.object);
  };

  const getBikeByDockHandler = async (id) => {
    const response = await getBikeByDockController(id);
    const bikeData = response.data.object;
    console.log("bike", bikeData);
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
      {/* <div className=" w-5/6"> */}
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

              <StyledTableCell style={{ width: "250px" }}>
                Image
              </StyledTableCell>
              <TableCell style={{ borderRadius: "0 15px 0 0", width: "150px" }}>
                Actions
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
                  {/* <UpdateButton book={dock} />
                <DeleteButton onClick={() => deleteBookHandler(dock._id)} /> */}
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
      {/* </div> */}
    </>
  );
};

export default GetAllDocks;

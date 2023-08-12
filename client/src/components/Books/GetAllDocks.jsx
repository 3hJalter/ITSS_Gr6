import { TableHead, TableBody, TableCell } from "@mui/material";
import { useEffect, useState } from "react";
import {
  getAllBooksController,
  deleteBookController,
} from "../../controller/book.controller.js";
import {
  StyledTable,
  StyledTableHead,
  StyledTableCell,
  StyledTableBody,
} from "../muiStyled.js";
import { DeleteButton, UpdateButton } from "../button/Button.jsx";

const GetAllDocks = () => {
  const [docks, setDocks] = useState([]);
  useEffect(() => {
    getAllBooksHandler();
  }, []);

  const getAllBooksHandler = async () => {
    const response = await getAllBooksController();
    setBooks(response.data);
    console.log("data", response.data);
  };

  const deleteBookHandler = async (id) => {
    if (confirm(`Are you sure you want to delete`)) {
      await deleteBookController(id);
    } else return;
    await getAllBooksHandler().then(() => {});
  };

  return (
    <StyledTable>
      <TableHead>
        <StyledTableHead>
          <StyledTableCell style={{ borderRadius: "15px 0 0 0" }}>
            Dock ID
          </StyledTableCell>
          <StyledTableCell>Dock name</StyledTableCell>
          <StyledTableCell>Address</StyledTableCell>

          <StyledTableCell>Image</StyledTableCell>
          <TableCell style={{ borderRadius: "0 15px 0 0", width: "10px" }}>
            Actions
          </TableCell>
        </StyledTableHead>
      </TableHead>


      <TableBody>
        {docks.map((dock) => (
          <StyledTableBody key={dock._id}>
            <StyledTableCell
              style={{
                maxWidth: "120px",
                wordWrap: "break-word",
                fontWeight: "bold",
                padding: "25px",
              }}
            >
              {dock.name}
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
                src={dock.image_url}
                alt={dock.image_url}
                style={{
                  display: "block",
                  margin: "0 auto",
                  width: "100px",
                  height: "150px",
                  wordWrap: "break-word",
                }}
              />
            </StyledTableCell>

            <TableCell>
              <UpdateButton book={dock} />
              <DeleteButton onClick={() => deleteBookHandler(dock._id)} />
            </TableCell>

          </StyledTableBody>
        ))}
      </TableBody>
    </StyledTable>
  );
};

export default GetAllDocks;

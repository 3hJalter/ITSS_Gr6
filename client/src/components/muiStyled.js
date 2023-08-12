import {
    FormGroup, Typography, styled, Table, TableRow, Button, AppBar, TableCell,
} from "@mui/material";
import {NavLink} from "react-router-dom";

export const Container = styled(FormGroup)`
  width: 50%;
  margin: 7.5% auto 10% auto;

  & > div {
    margin-top: 25px;
  }
`;

export const StyledTypography = styled(Typography)`
  text-align: center;
  font-size: 45px;
  font-weight: bold;
  color: #394867
`;

export const StyledTable = styled(Table)`
  width: 90%;
  margin: 100px auto 100px auto;
  box-shadow: rgba(0, 0, 0, 0.35) 0 5px 15px;
  border-radius: 15px;
`;

export const StyledTableHead = styled(TableRow)`
  background: #4d5a76;

  & > th {
    color: #fff;
    font-size: 18px;
    text-align: center;
    font-weight: 600;
  }
`;

export const StyledTableBody = styled(TableRow)`
  & > td {
    font-size: 16px;
  }

  &:nth-of-type(odd) {
    background-color: #f8f8f8;
  }

  &:nth-of-type(even) {
    background-color: white;
  }
`;

export const StyledTableCell = styled(TableCell)`
  border-right: 1px solid #c4c8d1;
`;

export const StyledCancelButton = styled(Button)`
  width: 35%;
  height: 50px;
  margin-left: 0;
  margin-top: 20px;
  font-size: 16px;
  display: flex;
  position: absolute;
  border-color: #394867;
  color: #394867;

  &:hover {
    background-color: #d8d8d8;
    border-color: #394867;
  }
`;

export const StyledCreateButton = styled(Button)`
  width: 35%;
  margin-left: 65%;
  height: 50px;
  margin-top: 20px;
  font-size: 16px;
  display: flex;
  background-color: #4d5a76;

  &:hover {
    background-color: #606c85;
  }
`;

export const StyledDeleteButton = styled(Button)`
  border-color: #394867;
  color: #394867;
  margin-top: 20px;
`;

export const StyledSaveChangeButton = styled(Button)`
  width: 35%;
  margin-left: 65%;
  height: 50px;
  margin-top: 20px;
  font-size: 16px;
  display: flex;
  background-color: #394867;

  &:hover {
    background-color: #606c85;
  }
`;

export const StyledUpdateButton = styled(Button)`
  border-color: #394867;
  color: #394867;
`;

export const Header = styled(AppBar)`
  background: #4d5a76;
  margin-top: 0;
`;

export const Tabs = styled(NavLink)`
  font-size: 22px;
  margin-left: 30px;
  margin-right: 40px;
  color: inherit;
  text-decoration: none;
  font-weight: 600;

  &:hover {
    color: #171e2b;
  }
`;
import CancelIcon from "@mui/icons-material/Cancel";
import { useNavigate } from "react-router-dom";
import {
  StyledCancelButton,
  StyledUpdateButton,
} from "../style/muiStyled.js";
import VisibilityIcon from '@mui/icons-material/Visibility';

export const CancelButton = () => {
  const navigate = useNavigate();
  return (
    <div>
      <StyledCancelButton
        onClick={() => navigate("/")}
        variant="outlined"
        startIcon={<CancelIcon />}
      >
        Cancel
      </StyledCancelButton>
    </div>
  );
};

export const ViewButton = ({ onClick }) => {
  return (
    <StyledUpdateButton
      variant="outlined"
      startIcon={<VisibilityIcon />}
      onClick={onClick}
    >
      View
    </StyledUpdateButton>
  );
};
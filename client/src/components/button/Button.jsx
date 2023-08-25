import CancelIcon from "@mui/icons-material/Cancel";
import { StyledButton } from "../style/muiStyled.js";
import VisibilityIcon from "@mui/icons-material/Visibility";
import PaymentsIcon from "@mui/icons-material/Payments";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import PauseIcon from '@mui/icons-material/Pause';
import PlayArrowIcon from '@mui/icons-material/PlayArrow';

export const CancelButton = ({ onClick }) => {
  return (
    <StyledButton
      onClick={onClick}
      variant="outlined"
      startIcon={<CancelIcon />}
    >
      Cancel
    </StyledButton>
  );
};

export const ViewButton = ({ onClick }) => {
  return (
    <StyledButton
      variant="outlined"
      startIcon={<VisibilityIcon />}
      onClick={onClick}
    >
      View
    </StyledButton>
  );
};

export const DepositButton = ({ onClick }) => {
  return (
    <StyledButton
      variant="outlined"
      startIcon={<PaymentsIcon />}
      onClick={onClick}
    >
      Deposit
    </StyledButton>
  );
};

export const BackButton = ({ onClick }) => {
  return (
    <StyledButton
      variant="outlined"
      startIcon={<ArrowBackIcon />}
      onClick={onClick}
    >
      Back
    </StyledButton>
  );
};

export const PauseButton = ({ onClick }) => {
  return (
    <StyledButton
      variant="outlined"
      startIcon={<PauseIcon />}
      onClick={onClick}
    >
      Pause
    </StyledButton>
  );
}

export const ResumeButton = ({ onClick }) => {
  return (
    <StyledButton
      variant="outlined"
      startIcon={<PlayArrowIcon />}
      onClick={onClick}
    >
      Resume
    </StyledButton>
  );
}

export const PayButton = ({ onClick }) => {
  return (
    <StyledButton
      variant="outlined"
      startIcon={<PaymentsIcon />}
      onClick={onClick}
    >
      Pay
    </StyledButton>
  );
}
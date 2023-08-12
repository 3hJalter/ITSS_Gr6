import CancelIcon from "@mui/icons-material/Cancel";
import {Link, useNavigate} from "react-router-dom";
import {
    StyledCancelButton,
    StyledCreateButton,
    StyledDeleteButton,
    StyledSaveChangeButton,
    StyledUpdateButton,
} from "../muiStyled.js";
import AddCircleOutlineIcon from "@mui/icons-material/AddCircleOutline.js";
import DeleteIcon from "@mui/icons-material/Delete.js";
import EditIcon from "@mui/icons-material/Edit.js";
import SaveIcon from "@mui/icons-material/Save.js";

export const CancelButton = () => {
    const navigate = useNavigate();
    return (
        <div>
            <StyledCancelButton
                onClick={() => navigate("/")}
                variant="outlined"
                startIcon={<CancelIcon/>}
            >
                Cancel
            </StyledCancelButton>
        </div>
    );
};

export const CreateButton = ({onClick}) => {
    return (
        <StyledCreateButton
            onClick={onClick}
            variant="contained"
            startIcon={<AddCircleOutlineIcon/>}
        >
            Create
        </StyledCreateButton>
    );
};

export const DeleteButton = ({onClick}) => {
    return (
        <StyledDeleteButton
            variant="outlined"
            startIcon={<DeleteIcon/>}
            onClick={onClick}
        >
            Delete
        </StyledDeleteButton>
    );
};

export const UpdateButton = ({book: book}) => {
    return (
        <StyledUpdateButton
            variant="outlined"
            startIcon={<EditIcon/>}
            component={Link}
            to={`/update/${book._id}`}
        >
            Update
        </StyledUpdateButton>
    );
};

export const SaveChangesButton = ({onClick}) => {
    return (
        <StyledSaveChangeButton
            variant="contained"
            startIcon={<SaveIcon/>}
            onClick={onClick}
        >
            Save Changes
        </StyledSaveChangeButton>
    );
};
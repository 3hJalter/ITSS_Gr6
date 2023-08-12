import {useState} from "react";
import {useNavigate} from "react-router-dom";
import {createBookController} from "../../controller/book.controller.js";
import {CreateButton} from "./button/Button.jsx";
import {Container, StyledTypography} from "./muiStyled.js";
import BookForm from "./BookForm.jsx";

const initialFormState = {
    title: "",
    author: "",
    description: "",
    year: "",
    price: "",
    image_url: "",
    isbn: "",
    publisher: "",
    genre: "",
    pages: "",
    category: "",
};

const CreateBook = () => {
    const [book, setBook] = useState(initialFormState);
    const navigate = useNavigate();
    const createBookHandler = async () => {
        navigate(await createBookController(book) ? "/" : "/create");
    };

    return (
        <Container>
            <StyledTypography
                variant="h4"
                color="initial"
                style={{textAlign: "center", fontWeight: "bold", color: "#394867"}}
            >
                Create new book
            </StyledTypography>
            <BookForm prop={book} setProp={setBook} func={createBookHandler} Btn={CreateButton}/>
        </Container>
    );
};

export default CreateBook;

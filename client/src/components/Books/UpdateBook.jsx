// import {useState, useEffect} from "react";
// import {getBookByIdController, updateBookController} from "../../controller/book.controller.js";
// import {useNavigate, useParams} from "react-router-dom";
// import {SaveChangesButton} from "../button/Button.jsx";
// import {Container, StyledTypography} from "../muiStyled.js";
// import BookForm from "./BookForm.jsx";

// const initialFormState = {
//     title: "",
//     author: "",
//     description: "",
//     year: "",
//     price: "",
//     image_url: "",
//     isbn: "",
//     publisher: "",
//     genre: "",
//     pages: "",
//     category: "",
// };

// const UpdateBook = () => {
//     const [book, setBook] = useState(initialFormState);
//     const navigate = useNavigate();
//     const {id} = useParams();

//     useEffect(() => {
//         getBookByIdHandler();
//     }, []);

//     const getBookByIdHandler = async () => {
//         const response = await getBookByIdController(id);
//         setBook(response.data);
//     };

//     const updateBookHandler = async () => {
//         navigate(await updateBookController(book, id) ? "/" : `/update/${id}`);
//     };

//     return (
//         <Container>
//             <StyledTypography
//                 variant="h4"
//                 color="initial"
//                 style={{textAlign: "center", fontWeight: "bold", color: "#394867"}}
//             >
//                 Update book
//             </StyledTypography>
//             <BookForm prop={book} setProp={setBook} func={updateBookHandler} Btn={SaveChangesButton}/>
//         </Container>
//     );
// };

// export default UpdateBook;

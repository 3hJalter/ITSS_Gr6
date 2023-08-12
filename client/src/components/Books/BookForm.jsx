import {FormControl, Input, InputLabel} from "@mui/material";
import {CancelButton} from "../button/Button.jsx";

const BookForm = ({prop, setProp, func, Btn}) => {

    const onValueChange = (e) => {
        setProp({...prop, [e.target.name]: e.target.value});
    };

    return (
        <>
            <FormControl>
                <InputLabel>Title</InputLabel>
                <Input
                    onChange={(e) => onValueChange(e)}
                    name="title"
                    value={prop.title}
                />
            </FormControl>
            <FormControl>
                <InputLabel>Author</InputLabel>
                <Input
                    onChange={(e) => onValueChange(e)}
                    name="author"
                    value={prop.author}
                />
            </FormControl>
            <FormControl>
                <InputLabel>Description</InputLabel>
                <Input
                    onChange={(e) => onValueChange(e)}
                    name="description"
                    value={prop.description}
                />
            </FormControl>
            <FormControl>
                <InputLabel>Publisher</InputLabel>
                <Input
                    onChange={(e) => onValueChange(e)}
                    name="publisher"
                    value={prop.publisher}
                />
            </FormControl>
            <FormControl>
                <InputLabel>Year</InputLabel>
                <Input
                    onChange={(e) => onValueChange(e)}
                    name="year"
                    value={prop.year}
                />
            </FormControl>
            <FormControl>
                <InputLabel>Category</InputLabel>
                <Input
                    onChange={(e) => onValueChange(e)}
                    name="category"
                    value={prop.category}
                />
            </FormControl>
            <FormControl>
                <InputLabel>Genre</InputLabel>
                <Input
                    onChange={(e) => onValueChange(e)}
                    name="genre"
                    value={prop.genre}
                />
            </FormControl>
            <FormControl>
                <InputLabel>Pages</InputLabel>
                <Input
                    onChange={(e) => onValueChange(e)}
                    name="pages"
                    value={prop.pages}
                />
            </FormControl>
            <FormControl>
                <InputLabel>Price</InputLabel>
                <Input
                    onChange={(e) => onValueChange(e)}
                    name="price"
                    value={prop.price}
                />
            </FormControl>
            <FormControl>
                <InputLabel>Image</InputLabel>
                <Input
                    onChange={(e) => onValueChange(e)}
                    name="image_url"
                    value={prop.image_url}
                />
            </FormControl>
            <FormControl>
                <CancelButton/>
                <Btn onClick={() => func()}/>
            </FormControl>
        </>
    )
}

export default BookForm
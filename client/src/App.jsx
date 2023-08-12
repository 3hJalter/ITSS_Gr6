import React from "react";
import Header from "./components/Layout/Header";
import GetAllDocks from "./components/Books/GetAllDocks";
import CreateBook from "./components/Books/CreateBook";
import {BrowserRouter, Routes, Route} from "react-router-dom";
import UpdateBook from "./components/Books/UpdateBook";
import Footer from "./components/Layout/Footer";
import NotFound from "./components/ErrorPage/NotFound";
import Favicon from "react-favicon";
function App() {
    return (
        <>
            <div className="App">
                <Favicon url="./src/favicon.png"></Favicon>
            </div>
            <BrowserRouter>
                <Header/>
                <div style={{
                    display: "flex",
                    flexDirection: "column",
                    alignItems: "center"
                }}>
                    <Routes>
                        <Route path="/" element={<GetAllDocks/>}/>
                        {/* <Route path="/create" element={<CreateBook/>}/>
                        <Route path="/update/:id" element={<UpdateBook/>}></Route>
                        <Route path="*" element={<NotFound/>}></Route> */}
                    </Routes>
                </div>
                <Footer/>
            </BrowserRouter>

        </>
    );
}

export default App;

import React from "react";
import Header from "./components/Layout/Header";
import GetAllDocks from "./components/GetAllDocks";
import GetAllBikes from "./components/GetAllBikes";
import Deposit from "./components/Deposit";
// import CreateBook from "./components/Books/CreateBook";
import { BrowserRouter, Routes, Route } from "react-router-dom";
// import UpdateBook from "./components/Books/UpdateBook";
import Footer from "./components/Layout/Footer";
import Home from "./components/Home";
import ActiveTransaction from "./components/ActiveTransaction";
import NotFound from "./components/ErrorPage/NotFound";
import Favicon from "react-favicon";
function App() {
  return (
    <>
      <div className="App">
        <Favicon url="./src/favicon.png"></Favicon>
      </div>
      <BrowserRouter>
        <Header />
        <div
          style={{
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
          }}
        >
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/docks" element={<GetAllDocks />} />
            <Route path="/dock/:id/list-bike" element={<GetAllBikes />} />
            <Route path="/deposit/" element={<Deposit />}></Route>
            <Route path="/active-transaction" element={<ActiveTransaction />}></Route>
            {/* <Route path="/create" element={<CreateBook/>}/>
                        <Route path="/update/:id" element={<UpdateBook/>}></Route>*/}
            <Route path="*" element={<NotFound />}></Route>
          </Routes>
        </div>
        <Footer />
      </BrowserRouter>
    </>
  );
}

export default App;

import React from "react";
import Header from "./components/Layout/Header";
import DocksScreen from "./components/DocksScreen";
import BikesScreen from "./components/BikesScreen";
import DepositScreen from "./components/DepositScreen";
// import CreateBook from "./components/Books/CreateBook";
import { BrowserRouter, Routes, Route } from "react-router-dom";
// import UpdateBook from "./components/Books/UpdateBook";
import Footer from "./components/Layout/Footer";
import HomeScreen from "./components/HomeScreen";
import PaymentScreen from "./components/PaymentScreen";
import ActiveTransactionScreen from "./components/ActiveTransactionScreen";
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
            <Route path="/" element={<HomeScreen />} />
            <Route path="/docks" element={<DocksScreen />} />
            <Route path="/dock/:id/list-bike" element={<BikesScreen />} />
            <Route path="/deposit/" element={<DepositScreen />}></Route>
            <Route
              path="/active-transaction"
              element={<ActiveTransactionScreen />}
            ></Route>
            <Route path="/payment" element={<PaymentScreen />}></Route>
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

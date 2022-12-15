import "./App.css";
import "../node_modules/bootstrap/dist/css/bootstrap.min.css";
import Navbar from "./layout/Navbar";
import AdminPage from "./pages/AdminPage";
import Register from "./Users/Register";
import Login from "./pages/Login";
import Home from "./pages/Home";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import EditUser from "./Users/EditUser";
import ViewUser from "./Users/ViewUser";
import AddTask from "./tasks/AddTask";

function App() {
  return (
    <div className="App">
      <Router>
        <Navbar />
        <Routes>
          <Route exact path="/" element={<Home />} />
          <Route exact path="/AdminPage" element={<AdminPage />} />
          <Route exact path="/Register" element={<Register />} />
          <Route exact path="/Login" element={<Login />} />
          <Route exact path="/edituser/:id" element={<EditUser />} />
          <Route exact path="/viewuser/:id" element={<ViewUser />} />
          <Route exact path="/AddTask/:id" element={<AddTask />} />
        </Routes>
      </Router>
    </div>
  );
}

export default App;

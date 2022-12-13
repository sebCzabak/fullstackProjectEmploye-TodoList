import React from "react";
import Register from "./Register";
import { Container } from "react-bootstrap";
import { AuthProvider } from "../contexts/AuthConthex";

function App() {
  return (
    <AuthProvider>
      <Container
        className="d-flex align-items-center 
    justify-content-center"
        style={{ minHeight: "100vh" }}
      >
        <div className="w-100" style={{ maxWidth: "400px" }}>
          <Register />
        </div>
      </Container>
    </AuthProvider>
  );
}

export default App;

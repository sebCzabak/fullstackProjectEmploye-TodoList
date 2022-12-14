import React from "react";
import Register from "./Register";
import { Container } from "react-bootstrap";
import { AuthProvider } from "../contexts/AuthConthex";

function App() {
  return (
    <AuthProvider>
      <Container className="container">
        <div className="register-container">
          <Register />
        </div>
      </Container>
    </AuthProvider>
  );
}

export default App;

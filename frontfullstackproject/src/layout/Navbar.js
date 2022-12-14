import React from "react";
import { Link } from "react-router-dom";

export default function Navbar() {
  return (
    <div>
      <nav className="navbar navbar-dark bg-dark">
        <div className="container-fluid">
          <a className="navbar-brand" href="/AdminPage">
            Fullstack Project
          </a>
          <Link className="btn btn-outline-light" to="/Register">
            Register
          </Link>
        </div>
      </nav>
    </div>
  );
}

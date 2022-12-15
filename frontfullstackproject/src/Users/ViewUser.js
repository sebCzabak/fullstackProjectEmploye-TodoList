import axios from "axios";
import React, { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";

export default function ViewUser() {
  const { id } = useParams();
  const [employee, setEmployee] = useState({
    fullName: "",
    username: "",
    email: "",
  });

  useEffect(() => {
    loadEmployee();
  }, []);
  const loadEmployee = async () => {
    const result = await axios.get(`http://localhost:8080/api/employees/${id}`);
    setEmployee(result.data);
  };
  return (
    <div className="container">
      <div className="row">
        <div className="col-md-6 offset-md-3 border rounded p-4 mt-2 shadow">
          <h2 className="text-center m-4">Edit User</h2>

          <div className="card">
            <div className="car-header">
              Details of user id :{employee.id}
              <ul className="list-group list-group-flush">
                <li className="list-group-item">
                  <b>FullName:</b>
                  {employee.fullName}
                </li>
                <li className="list-group-item">
                  <b>UserName:</b>
                  {employee.username}
                </li>
                <li className="list-group-item">
                  <b>Email:</b>
                  {employee.email}
                </li>
              </ul>
            </div>
          </div>
          <Link className="btn btn-outline-primary my-2" to={"/AdminPage"}>
            Back
          </Link>
        </div>
      </div>
    </div>
  );
}

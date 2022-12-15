import React, { useState, useEffect } from "react";
import axios from "axios";
import { Link, useParams } from "react-router-dom";

export default function AdminPage() {
  const [employee, setEmployee] = useState([]);

  useEffect(() => {
    loadEmployee();
  }, []);
  const { id } = useParams();

  const loadEmployee = async () => {
    const result = await axios.get("http://localhost:8080/api/employees");
    setEmployee(result.data);
  };
  const deleteEmployee = async (id) => {
    await axios.delete(`http://localhost:8080/api/employees/${id}`);
    loadEmployee();
  };

  return (
    <div className="container">
      <div className="py-4">
        <table className="table table-hover">
          <thead>
            <tr>
              <th scope="col">#</th>
              <th scope="col">Full Name</th>
              <th scope="col">UserName</th>
              <th scope="col">Email</th>
              <th scope="col">Action</th>
            </tr>
          </thead>
          <tbody>
            {employee.map((employee, index) => (
              <tr>
                <th scope="row" key="{index}">
                  {index + 1}
                </th>
                <td>{employee.fullName}</td>
                <td>{employee.userName}</td>
                <td>{employee.email}</td>
                <td>
                  <Link
                    className="btn btn-primary mx-2"
                    to={`/edituser/${employee.id}`}
                  >
                    Edit
                  </Link>
                  <Link
                    className="btn btn-outline-primary mx-2"
                    to={`/viewuser/${employee.id}`}
                  >
                    View
                  </Link>
                  <button
                    className="btn btn-danger mx-2"
                    onClick={() => deleteEmployee(employee.id)}
                  >
                    Delete
                  </button>
                  <Link
                    className="btn btn-success mx-2"
                    to={`/addtask/${employee.id}`}
                  >
                    AddTask
                  </Link>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

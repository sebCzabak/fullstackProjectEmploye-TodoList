import React from "react";
import { Link } from "react-router-dom";

export default function Home() {
  return (
    <div className="container">
      Home
      <div>
        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin ligula
        neque, vehicula nec consequat id, suscipit vitae est. Aliquam convallis
        odio sapien, et pharetra magna fermentum non. Suspendisse a massa at mi
        vulputate maximus id ac sem. Integer tellus velit, pulvinar porta nisl
        non, dignissim auctor orci. Nulla diam dolor, semper sit amet porta ut,
        ultricies vitae justo. Nulla at neque quis nunc posuere venenatis eu
        eget dolor. Etiam sagittis ornare ligula at ultricies. Nunc ut vehicula
        nisl. Pellentesque eget turpis vestibulum, vulputate velit ut,
        ullamcorper neque. Vivamus in metus posuere, porttitor elit sed, euismod
        dui. Phasellus mi libero, dignissim a venenatis eu, facilisis id ipsum.
        Maecenas eget dignissim sapien, eu accumsan tortor. Nam et nulla in ante
        placerat gravida. Aenean in fermentum elit. Fusce ultrices purus ut nisl
        eleifend aliquet. Morbi non tincidunt orci.
      </div>
      <div>Check email for registration code</div>
      <Link to="/Login">After code verification log in</Link>
    </div>
  );
}

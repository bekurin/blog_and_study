import React, { useEffect, useState } from "react";
import "./Nav.css";

const Nav = () => {
  const [show, handleShow] = useState(false);

  useEffect(() => {
    window.addEventListener("scroll", () => {
      if (window.scrollY > 100) {
        handleShow(true);
      } else handleShow(false);
    });

    return () => {
      window.removeEventListener("scroll");
    };
  }, []);

  return (
    <div className={`nav ${show && "nav_black"}`}>
      <img
        className="nav_logo"
        src="https://t1.daumcdn.net/cfile/tistory/99EE2F485ECDDF470C"
        alt="Netflix Logo"
      />

      <img
        className="nav_avatar"
        src="https://img.icons8.com/bubbles/2x/netflix.png"
        alt="Netflix Profile"
      />
    </div>
  );
};

export default Nav;

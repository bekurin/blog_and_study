import React from "react";
import "./Home.css";
import Search from "../../components/Search";
import { Link } from "react-router-dom";
import AppsIcon from "@material-ui/icons/Apps";
import { Avatar } from "@material-ui/core";

const Home = () => {
  return (
    <div className="home">
      <div className="home_header">
        <div className="home_headerLeft">
          <Link to="/about">About</Link>
          <Link to="/store">Store</Link>
        </div>
        <div className="home_headerRight">
          <Link to="/gmail">Gmail</Link>
          <Link to="/images">Images</Link>
          <AppsIcon />
          <Avatar />
        </div>
      </div>

      <div className="home_body">
        <img
          src="https://i2.wp.com/kr.hypebeast.com/files/2018/09/google-logos-2018-5.png?w=1600"
          alt="Google Logo"
        />
        <div className="home_inputContainer">
          <Search hideButtons />
        </div>
      </div>
    </div>
  );
};

export default Home;

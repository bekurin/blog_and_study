import React from "react";
import "./Modal.css";

const Modal = ({ modalMovie }) => {
  function truncate(str, n) {
    return str?.length > n ? str.substr(0, n - 1) + "..." : str;
  }

  return (
    <div
      className="modal"
      style={{
        backgroundSize: "cover",
        backgroundImage: `url(
                "https://image.tmdb.org/t/p/original/${modalMovie?.backdrop_path}"
            )`,
        backgroundPosition: "center center",
      }}
    >
      <div className="modal_contents">
        <h1 className="modal_title">
          {modalMovie?.title || modalMovie?.name || modalMovie?.original_name}
        </h1>
        <div className="modal_buttons">
          <button className="modal_button">재생</button>
          <button className="modal_button">내가 찜한 콘텐츠</button>
        </div>
        <h1 className="modal_description">
          {truncate(modalMovie?.overview, 100)}
        </h1>
      </div>
    </div>
  );
};

export default Modal;

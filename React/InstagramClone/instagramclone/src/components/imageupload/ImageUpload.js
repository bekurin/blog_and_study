import React, { useState } from "react";
import { Button } from "@material-ui/core";
import firebase from "firebase";
import { storage, db } from "../../firebase";
import "./ImageUpload.css";

const ImageUpload = ({ userName }) => {
  const [caption, setCaption] = useState("");
  const [image, setImage] = useState(null);
  //   const [url, setUrl] = useState("");
  const [progress, setProgress] = useState(0);

  const handleChange = (e) => {
    if (e.target.files[0]) {
      setImage(e.target.files[0]);
    }
  };

  const handleUpload = (e) => {
    const uploadTask = storage.ref(`images/${image.name}`).put(image);
    uploadTask.on(
      "state_changed",
      (snapshot) => {
        const progress = Math.round(
          (snapshot.bytesTransferred / snapshot.totalBytes) * 100
        );
        setProgress(progress);
      },
      (error) => {
        console.log(error);
        alert(error.message);
      },
      () => {
        storage
          .ref("images")
          .child(image.name)
          .getDownloadURL()
          .then((url) => {
            db.collection("posts").add({
              timestamp: firebase.firestore.FieldValue.serverTimestamp(),
              caption: caption,
              imageUrl: url,
              userName: userName,
            });

            setProgress(0);
            setCaption("");
            setImage(null);
          });
      }
    );
  };

  return (
    <div className="imageupload">
      <progress className="imageupload_progress" value={progress} max="100" />
      <input
        type="text"
        placeholder="본문을 적어주세요."
        onChange={(e) => setCaption(e.target.value)}
      />
      <input type="file" onChange={handleChange} />
      <Button onClick={handleUpload}>업로드</Button>
    </div>
  );
};

export default ImageUpload;

import React, { forwardRef, useState } from "react";
import "./Post.css";
import db from "../../firebase";
import { Avatar, Modal, Button } from "@material-ui/core";
import VerifiedUserIcon from "@material-ui/icons/VerifiedUser";
import ChatBubbleOutlineIcon from "@material-ui/icons/ChatBubbleOutline";
import EditIcon from "@material-ui/icons/Edit";
import FavoriteBorderIcon from "@material-ui/icons/FavoriteBorder";
import PublishIcon from "@material-ui/icons/Publish";
import BackspaceIcon from "@material-ui/icons/Backspace";

const Post = forwardRef(({ id, post }, ref) => {
  const [open, setOpen] = useState(false);
  const [tweetMessage, setTweetMessage] = useState("");
  const [tweetImage, setTweetImage] = useState("");

  const deletePost = (e) => {
    e.preventDefault();
    db.collection("posts").doc(id).delete();
  };

  const updatePost = (e) => {
    e.preventDefault();
    db.collection("posts").doc(id).set(
      {
        text: tweetMessage,
        image: tweetImage,
      },
      { merge: true }
    );
    setOpen(false);
  };

  return (
    <div className="post" ref={ref}>
      <div className="post__avatar">
        <Avatar src={post.post.avatar} />
      </div>
      <div className="post__body">
        <div className="post__header">
          <div className="post__headerText">
            <h3>
              {post.post.displayName}{" "}
              <span className="post__headerSpecial">
                {post.post.verified && (
                  <VerifiedUserIcon className="post_badge" />
                )}{" "}
                @{post.post.username}
              </span>
            </h3>
          </div>
          <div className="post__headerDescription">
            <p>{post.post.text}</p>
          </div>
        </div>
        <img src={post.post.image} alt="" />
        <div className="post_footer">
          <ChatBubbleOutlineIcon fontSize="small" />
          <EditIcon onClick={(e) => setOpen(true)} fontSize="small" />
          <FavoriteBorderIcon fontSize="small" />
          <PublishIcon fontSize="small" />
          <BackspaceIcon onClick={deletePost} fontSize="small" />
        </div>
        <Modal open={open} onClose={(e) => setOpen(false)}>
          <div className="modal">
            <h1>나는 모달이다. 두두등장</h1>
            <div>
              <input
                className="modal_input"
                placeholder={post.post.text}
                value={tweetMessage}
                onChange={(event) => setTweetMessage(event.target.value)}
              />
            </div>
            <div>
              <img className="modal_image" src={post.post.image} alt="" />
              <input
                className="modal_input"
                placeholder={post.post.image}
                value={tweetImage}
                onChange={(event) => setTweetImage(event.target.value)}
              />
            </div>
            <Button className="modal_button" onClick={updatePost}>
              업데이트 트윗
            </Button>
          </div>
        </Modal>
      </div>
    </div>
  );
});

export default Post;

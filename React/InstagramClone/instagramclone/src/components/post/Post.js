import React, { useState, useEffect } from "react";
import "./Post.css";
import Avatar from "@material-ui/core/Avatar";
import { db } from "../../firebase";
import firebase from "firebase";

const Post = ({ postId, user, userName, imageUrl, caption }) => {
  const [comments, setComments] = useState([]);
  const [comment, setComment] = useState("");

  useEffect(() => {
    let unsubscribe;
    if (postId) {
      unsubscribe = db
        .collection("posts")
        .doc(postId)
        .collection("comments")
        .orderBy("timestamp", "desc")
        .onSnapshot((snapshot) => {
          setComments(snapshot.docs.map((doc) => doc.data()));
        });
    }
    return () => {
      unsubscribe();
    };
  }, [postId]);

  const postComment = (e) => {
    e.preventDefault();

    db.collection("posts").doc(postId).collection("comments").add({
      text: comment,
      userName: user.displayName,
      timestamp: firebase.firestore.FieldValue.serverTimestamp(),
    });
    setComment("");
  };

  return (
    <div className="post">
      <div className="post_header">
        <Avatar className="post_avatar" alt="piao_chung" src="" />
        <h3>{userName}</h3>
      </div>
      <img className="post_image" src={imageUrl} alt="postImage" />
      <h4 className="post_text">
        <strong>{userName}</strong> {caption}
      </h4>

      <div className="post_comments">
        {comments.map((comment) => (
          <p>
            <strong>{comment.userName}</strong> {comment.text}
          </p>
        ))}
      </div>

      {user && (
        <form className="post_commentBox">
          <input
            className="post_input"
            type="text"
            placeholder="댓글 달기..."
            value={comment}
            onChange={(e) => setComment(e.target.value)}
          />
          <button
            disabled={!comment}
            className="post_button"
            type="submit"
            onClick={postComment}
          >
            게시
          </button>
        </form>
      )}
    </div>
  );
};

export default Post;

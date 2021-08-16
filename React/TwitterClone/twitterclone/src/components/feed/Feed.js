import React, { useState, useEffect } from "react";
import "./Feed.css";
import TweetBox from "./TweetBox";
import Post from "./Post";
import db from "../../firebase";
import FlipMove from "react-flip-move";

const Feed = () => {
  const [posts, setPosts] = useState([]);

  useEffect(() => {
    db.collection("posts")
      .orderBy("timestamp", "desc")
      .onSnapshot((snapshot) => {
        setPosts(
          snapshot.docs.map((doc) => ({ id: doc.id, post: doc.data() }))
        );
      });
  }, []);

  return (
    <div className="feed">
      <div className="feed__header">
        <h2>홈</h2>
      </div>

      <TweetBox />

      <FlipMove>
        {posts.map((post) => (
          <Post id={post.id} post={post} key={post.id} />
        ))}
      </FlipMove>
    </div>
  );
};

export default Feed;

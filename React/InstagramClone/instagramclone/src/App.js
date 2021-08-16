import React, { useState, useEffect } from "react";
import "./App.css";
import Post from "./components/post/Post";
import { db, auth } from "./firebase";
import { makeStyles } from "@material-ui/core/styles";
import { Button, Input, Modal } from "@material-ui/core";
import ImageUpload from "./components/imageupload/ImageUpload";

function getModalStyle() {
  const top = 50;
  const left = 50;

  return {
    top: `${top}%`,
    left: `${left}%`,
    transform: `translate(-${top}%, -${left}%)`,
  };
}

const useStyles = makeStyles((theme) => ({
  paper: {
    position: "absolute",
    width: 400,
    backgroundColor: theme.palette.background.paper,
    border: "2px solid #000",
    boxShadow: theme.shadows[5],
    padding: theme.spacing(2, 4, 3),
  },
}));

function App() {
  const classes = useStyles();
  const [modalStyle] = useState(getModalStyle);

  const [user, setUser] = useState(null);
  const [userName, setUserName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const [posts, setPosts] = useState([]);
  const [openSignIn, setOpenSignIn] = useState(false);
  const [open, setOpen] = useState(false);

  useEffect(() => {
    const unsubscribe = auth.onAuthStateChanged((authUser) => {
      if (authUser) {
        //user has logged in...
        console.log(authUser);
        setUser(authUser);

        if (authUser.displayName) {
          //dont update username
        } else {
          return authUser.updateProfile({
            displayName: userName,
          });
        }
      } else {
        //user has logged out...
        setUser(null);
      }
    });
    return () => {
      //perform somme cleanup actions
      unsubscribe();
    };
  }, [user, userName]);

  useEffect(() => {
    db.collection("posts")
      .orderBy("timestamp", "desc")
      .onSnapshot((snapshot) => {
        setPosts(
          snapshot.docs.map((doc) => ({
            id: doc.id,
            post: doc.data(),
          }))
        );
      });
  }, []);

  const signIn = (e) => {
    e.preventDefault();

    auth
      .signInWithEmailAndPassword(email, password)
      .catch((error) => alert(error.message));
    setOpenSignIn(false);
  };

  const signUp = (e) => {
    e.preventDefault();
    auth
      .createUserWithEmailAndPassword(email, password)
      .then((authUser) => {
        return authUser.user.updateProfile({
          displayName: userName,
        });
      })
      .catch((error) => alert(error.message));

    setOpen(false);
  };

  return (
    <div className="app">
      <Modal open={open} onClose={() => setOpen(false)}>
        <div style={modalStyle} className={classes.paper}>
          <form className="app_signup">
            <center>
              <img
                className="app_headerImage"
                src="https://t1.daumcdn.net/cfile/tistory/9952D7405BD3592106"
                alt="instagram icon"
              />
            </center>
            <Input
              placeholder="이름을 입력하세요."
              type="text"
              value={userName}
              onChange={(e) => setUserName(e.target.value)}
            />
            <Input
              placeholder="이메일을 입력하세요."
              type="text"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
            <Input
              placeholder="비밀번호를 입력하세요."
              type="text"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
            <Button type="submit" onClick={signUp}>
              회원가입
            </Button>
          </form>
        </div>
      </Modal>

      <Modal open={openSignIn} onClose={() => setOpenSignIn(false)}>
        <div style={modalStyle} className={classes.paper}>
          <form className="app_signup">
            <center>
              <img
                className="app_headerImage"
                src="https://t1.daumcdn.net/cfile/tistory/9952D7405BD3592106"
                alt="instagram icon"
              />
            </center>
            <Input
              placeholder="이메일을 입력하세요."
              type="text"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
            <Input
              placeholder="비밀번호를 입력하세요."
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
            <Button type="submit" onClick={signIn}>
              로그인
            </Button>
          </form>
        </div>
      </Modal>
      <div className="app_header">
        <img
          className="app_headerImage"
          src="https://t1.daumcdn.net/cfile/tistory/9952D7405BD3592106"
          alt="instagram logo"
        />
        {user ? (
          <Button onClick={() => auth.signOut()}>로그아웃</Button>
        ) : (
          <div className="app_loginContainer">
            <Button onClick={() => setOpenSignIn(true)}>로그인</Button>
            <Button onClick={() => setOpen(true)}>회원가입</Button>
          </div>
        )}
      </div>

      <div className="app_posts">
        {posts.map(({ id, post }) => (
          <Post
            key={id}
            postId={id}
            user={user}
            userName={post.userName}
            caption={post.caption}
            imageUrl={post.imageUrl}
          />
        ))}
      </div>

      {user?.displayName ? (
        <ImageUpload userName={user.displayName} />
      ) : (
        <h3>로그인을 해주세요.</h3>
      )}
    </div>
  );
}

export default App;

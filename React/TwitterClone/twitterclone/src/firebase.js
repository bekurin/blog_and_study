import firebase from "firebase";

const firebaseConfig = {
  apiKey: "AIzaSyClmVWxXSBeNPIEhv_qyMUkS7u9Ui-V7w4",
  authDomain: "twittweclone2020.firebaseapp.com",
  databaseURL: "https://twittweclone2020.firebaseio.com",
  projectId: "twittweclone2020",
  storageBucket: "twittweclone2020.appspot.com",
  messagingSenderId: "751214948856",
  appId: "1:751214948856:web:2d6ef6ccaeab5bc7ea260d",
  measurementId: "G-T1XDCNZDGN",
};

const firebaseApp = firebase.initializeApp(firebaseConfig);

const db = firebaseApp.firestore();

export default db;

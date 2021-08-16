import firebase from "firebase";

const firebaseConfig = {
  apiKey: "AIzaSyB6OIp4dKjd8FKR4WWiyPWm2rKGhD38cI0",
  authDomain: "instagramclone2020-3d479.firebaseapp.com",
  databaseURL: "https://instagramclone2020-3d479.firebaseio.com",
  projectId: "instagramclone2020-3d479",
  storageBucket: "instagramclone2020-3d479.appspot.com",
  messagingSenderId: "599632382786",
  appId: "1:599632382786:web:1a91ebb1953420a7d496db",
  measurementId: "G-J6PPBCPX5C",
};

const firebaseApp = firebase.initializeApp(firebaseConfig);

const db = firebaseApp.firestore();
const auth = firebase.auth();
const storage = firebase.storage();

export { db, auth, storage };

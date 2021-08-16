import React from "react";
import "./App.css";
import Nav from "./components/nav/Nav";
import Banner from "./components/banner/Banner";
import Row from "./components/row/Row";
import requests from "./request";

function App() {
  return (
    <div className="app">
      <Nav />
      <Banner />
      <Row
        title="추천 콘텐츠"
        fetchUrl={requests.fetchTrending}
        isLargeRow={true}
      />
      <Row title="최고 순위" fetchUrl={requests.fetchTopRated} />
      <Row title="Netflix 오리지널" fetchUrl={requests.fetchNetflixOriginals} />
      <Row title="액션과 어드밴처" fetchUrl={requests.fetchActionMovies} />
      <Row title="코미디" fetchUrl={requests.fetchComedyMovies} />
      <Row title="호러" fetchUrl={requests.fetchHorrorMovies} />
      <Row title="로맨스" fetchUrl={requests.fetchRomanceMovies} />
      <Row title="다큐멘터리" fetchUrl={requests.fetchDocumentaries} />
    </div>
  );
}

export default App;

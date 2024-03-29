import React from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { paths } from './constants/paths';
import Post from './pages/post/Post';
import PostContainer from './pages/post/PostContainer';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path={paths.HOME} element={<PostContainer />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;

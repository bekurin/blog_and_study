import './App.css'
import UserContainer from './pages/user/UserContainer'
import { Route, Router, Routes } from 'react-router'
import { BrowserRouter } from 'react-router-dom'

function App() {
  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route path='/' element={<UserContainer />} />
        </Routes>
      </BrowserRouter>
    </>
  )
}

export default App

import './App.css'
import UserContainer from './pages/user/UserContainer'
import { BrowserRouter, Routes, Route } from 'react-router-dom'

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path='/' element={<UserContainer />} />
      </Routes>
    </BrowserRouter>
  )
}

export default App

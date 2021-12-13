import { useState } from 'react';
import { BrowserRouter, Switch, Route, Redirect } from 'react-router-dom';
import NavMenu from './components/NavMenu';
import Profile from './components/Profile';
import SignIn from './components/SignIn';
import SignUp from './components/SignUp';
import Movies from './components/Movies'
import Movie from './components/Movie'
import AddMovie from './components/AddMovie';
import { getJwtDetails } from './services/authService';

const App = () => {
  const [user, setUser] = useState(getJwtDetails());

  return (
    <BrowserRouter>
      <NavMenu user={user} setUser={setUser} /> 
      <Switch>
        <Redirect exact from='/' to='/movies'/>
        <Route path="/movies/:id"><Movie user={user} /></Route>
        <Route path="/movies"><Movies user={user} /></Route>
        <Route path="/addMovie"><AddMovie user={user} /></Route>
        <Route path="/profile"><Profile user={user} /></Route>
        <Route path="/signin"><SignIn setUser={setUser} /></Route>
        <Route path="/signup"><SignUp setUser={setUser} /></Route>
      </Switch>
     </BrowserRouter>
  )
}

export default App;
import { Navbar, Container } from 'react-bootstrap';
import { NavLink } from 'react-router-dom';
import { signOut } from '../services/authService';

const NavMenu = ({user, setUser}) => {
  const processSignOut = (e) => {
    e.preventDefault();
    signOut();
    setUser();
  }

  return (
    <Navbar bg="primary" variant="light">
      <Container className='ms-5'>
        <Navbar.Text>
          <NavLink to="/movies">Movies</NavLink>
        </Navbar.Text>
      </Container>
      <Container className="justify-content-end me-5">
        <Navbar.Text>
          {user === undefined ? <NavLink to="/signin">Sign in</NavLink> : <NavLink to="/profile">{user.username}</NavLink>}
        </Navbar.Text>
        <Navbar.Text className="ps-3">
          {user === undefined ? <NavLink to="/signup">Sign up</NavLink> : <NavLink to="" onClick={processSignOut}>Sign out</NavLink>}
        </Navbar.Text>
      </Container>
    </Navbar>
  )
}

export default NavMenu
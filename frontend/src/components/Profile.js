import { Redirect } from 'react-router-dom';
import { Container } from 'react-bootstrap';

const Profile = ({user}) => {  
  if (user === undefined) {
    return <Redirect to="/signin" />
  }

  return (
    <Container>
      <div>Username: {user.username}</div>
      <div>Email: {user.email}</div>
      <div>Roles: {user.roles.join(", ")}</div>
    </Container>
  )
}

export default Profile
import { useState } from 'react';
import { Container, Row, Col, Form, Button } from 'react-bootstrap';
import { useHistory } from 'react-router';
import { signUp } from '../services/authService';

const SignUp = ({setUser}) => {
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const history = useHistory();

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      let user = await signUp(username, email, password);
      setUser(user);
      history.push("/profile");
    }
    catch (error) {
      if (error.response !== undefined && error.response.status === 409) {
        setErrorMessage("Username or email is already taken");
        return;
      }
      setErrorMessage("Something went wrong");
    }
  }

  return (
    <Container>
      <Row className="row mt-5 justify-content-center">
        <Col lg="5">
          <Form onSubmit={handleSubmit}>
            <Form.Group>
              <Form.Label>Username</Form.Label>
              <Form.Control type="text" value={username} onChange={e => setUsername(e.target.value)} />
            </Form.Group>

            <Form.Group className="mt-2">
              <Form.Label className="m-0">Email address</Form.Label>
              <Form.Control type="email" value={email} onChange={e => setEmail(e.target.value)} />
            </Form.Group>
      
            <Form.Group className="mt-2">
              <Form.Label className="m-0">Password</Form.Label>
              <Form.Control type="password" value={password} onChange={e => setPassword(e.target.value)} />
            </Form.Group>

            <div className="text-danger">{errorMessage}</div>

            <Button className="mt-3" variant="primary" type="submit">Sign up</Button>
          </Form>
        </Col>
      </Row>
    </Container>
  )
}
  
export default SignUp
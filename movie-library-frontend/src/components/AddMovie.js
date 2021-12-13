import { Redirect, useHistory } from 'react-router-dom';
import { Container, Row, Col, Form, Button } from 'react-bootstrap';
import { useState } from 'react';
import { createMovie } from '../services/movieService';

const AddMovie = ({user}) => {
  const [title, setTitle] = useState('');
  const [releaseYear, setReleaseYear] = useState('');
  const [country, setCountry] = useState('');
  const [genres, setGenres] = useState('');
  const [description, setDescription] = useState('');
  const history = useHistory()

  if (user === undefined || !user.roles.includes("ROLE_EDITOR")) {
    return <Redirect to="/signin" />
  }

  const handleSubmit = async (e) => {
    e.preventDefault();

    let movie = {
      title,
      releaseYear: Number(releaseYear),
      country,
      description,
      genres: genres.split(", ")
    }

    let movieId = (await createMovie(movie)).id;

    history.push("/movies/" + movieId);
  }

  return (
    <Container>
      <Row className="row mt-5 justify-content-center">
        <Col lg="5">
          <Form onSubmit={handleSubmit}>
            <Form.Group>
              <Form.Label>Title</Form.Label>
              <Form.Control type="text" value={title} onChange={e => setTitle(e.target.value)} />
            </Form.Group>

            <Form.Group className="mt-2">
              <Form.Label className="m-0">Release year</Form.Label>
              <Form.Control type="number" value={releaseYear} onChange={e => setReleaseYear(e.target.value)} />
            </Form.Group>

            <Form.Group className="mt-2">
              <Form.Label className="m-0">Country</Form.Label>
              <Form.Control type="text" value={country} onChange={e => setCountry(e.target.value)} />
            </Form.Group>

            <Form.Group className="mt-2">
              <Form.Label className="m-0">Genres</Form.Label>
              <Form.Control type="text" value={genres} onChange={e => setGenres(e.target.value)} />
            </Form.Group>

            <Form.Group className="mt-2">
              <Form.Label className="m-0">Description</Form.Label>
              <Form.Control as="textarea" rows={5} value={description} onChange={e => setDescription(e.target.value)} />
            </Form.Group>

            <Button className="mt-3" variant="primary" type="submit">Add</Button>
          </Form>
        </Col>
      </Row>
    </Container>
  )
}

export default AddMovie
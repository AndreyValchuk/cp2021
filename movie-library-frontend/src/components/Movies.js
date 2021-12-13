import { useEffect } from 'react';
import { useState } from 'react';
import { Container, Form, Row, Col } from 'react-bootstrap';
import { NavLink  } from 'react-router-dom';
import { getMovies } from '../services/movieService';
import MovieShortInfo from './MovieShortInfo'

const Movies = ({user}) => {
  const [pagedMovies, setPagedMovies] = useState();
  
  useEffect(() => getMovies().then(data => setPagedMovies(data)), []);

  const search = async (title) => {
    setPagedMovies(await getMovies(title));
  }

  return (
    <Container>
      {
        pagedMovies === undefined ? "Loading..." :
          <>
            <Row className="mt-3">
              <Col md={6}>
                <Form.Control type="text" placeholder="Search" onChange={e => search(e.target.value)} />
              </Col>
            </Row>
            {user !== undefined && user.roles.includes("ROLE_EDITOR") ?
              <div className='m-3'><NavLink to="/addMovie">Add Movie</NavLink></div> : "" }
            {pagedMovies.movies.map(movie => <div key={movie.id} className='mb-2'><MovieShortInfo movie={movie} /></div>)}
          </>
      }
    </Container>
  )
}

export default Movies
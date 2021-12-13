import { Row, Col } from 'react-bootstrap';

const MovieFullInfo = ({movieInfo}) => {
  return (
    <>
      <Row className='h2'>
        <Col xs={11}>{movieInfo.title}</Col>
        <Col xs={1} className='text-end'>{movieInfo.averageScore}/10</Col>
      </Row>
      <div>Release year: {movieInfo.releaseYear}</div>
      <div>Country: {movieInfo.country}</div>
      <div>Genres: {movieInfo.genres.join(", ")}</div>
      <div>Description: {movieInfo.description}</div>
    </>
  )
}

export default MovieFullInfo
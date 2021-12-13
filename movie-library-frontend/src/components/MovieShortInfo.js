import { Card } from 'react-bootstrap'
import { useHistory } from 'react-router-dom';

const Movies = ({movie}) => {
  const history = useHistory();

  const redirectToMovie = () => history.push("/movies/" + movie.id);

  return (
    <Card onClick={redirectToMovie} style={{ cursor: "pointer" }}>
      <Card.Body>
        <div className='h3'>{movie.title}</div>
        <div className='mt-2'>{movie.releaseYear}</div>
      </Card.Body>
    </Card>
  )
}

export default Movies
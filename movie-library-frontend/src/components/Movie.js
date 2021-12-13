import { useEffect } from 'react';
import { useState } from 'react';
import { useParams, NavLink, useHistory } from 'react-router-dom';
import { Container, Button } from 'react-bootstrap';
import MovieFullInfo from './MovieFullInfo';
import EditMovie from './EditMovie';
import Review from './Review';
import AddReview from './AddReview';
import { getMovie, deleteMovie } from '../services/movieService';
import { deleteReview } from '../services/reviewService';

const Movie = ({user}) => {
  const [movie, setMovie] = useState();
  const [mode, setMode] = useState("view");
  const { id } = useParams();
  const history = useHistory();
  
  useEffect(() => getMovie(id).then(data => setMovie(data)), [id])

  const updateMovie = (e) => {
    e.preventDefault();
    setMode("edit");
  }

  const deleteMovieInfo = async (e) => {
    e.preventDefault();
    await deleteMovie(id);
    history.push("/movies");
  }

  const deleteReviewInfo = async (reviewId) => {
    await deleteReview(reviewId);
    setMovie(prevState => ({...prevState, reviews: prevState.reviews.filter(review => review.id !== reviewId)}));
  }

  return (
    <Container>
      {
        movie === undefined ? "Loading..." :
        <>
          <div className='mt-2'>
            {mode === "view" ? <MovieFullInfo movieInfo={movie} /> : <EditMovie movie={movie} setMovie={setMovie} setMode={setMode} />}
          </div>
          {mode === "view" && user !== undefined && user.roles.includes("ROLE_EDITOR")
            ?
            <>
              <NavLink to="" onClick={updateMovie}>Edit</NavLink>
              <span className='mx-3'><NavLink to="" onClick={deleteMovieInfo}>Delete</NavLink></span>
            </>
            : ""
          }
          <div className='h5 mt-3'>Reviews</div>
          {user !== undefined ? (movie.reviews.some(review => review.reviewerId === Number(user.sub)) ? "" :
            <AddReview user={user} movieId={id} setMovie={setMovie} />) : "" }
          {
            movie.reviews.map(review => {
              return (user !== undefined && (review.reviewerId === Number(user.sub) || user.roles.includes("ROLE_MODERATOR"))) ?
                <div key={review.id} className='mt-2'>
                  <Review review={review} />
                  <Button className="mt-1" variant="danger" onClick={() => deleteReviewInfo(review.id)}>Delete</Button>
                </div>
                : <div key={review.id} className='mt-2'><Review review={review} /></div>
            })
          }
        </>
      }
    </Container>
  )
}

export default Movie
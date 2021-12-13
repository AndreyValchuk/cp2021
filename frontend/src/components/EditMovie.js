import { Form, Button } from 'react-bootstrap';
import { useState } from 'react';
import { updateMovie } from '../services/movieService';

const EditMovie = ({movie, setMovie, setMode}) => {
  const [title, setTitle] = useState(movie.title);
  const [releaseYear, setReleaseYear] = useState(movie.releaseYear);
  const [country, setCountry] = useState(movie.country);
  const [genres, setGenres] = useState(movie.genres.join(", "));
  const [description, setDescription] = useState(movie.description);

  const handleSubmit = async (e) => {
    e.preventDefault();

    let updatedMovie = {
      title,
      releaseYear: Number(releaseYear),
      country,
      description,
      genres: genres.split(", ")
    }

    await updateMovie(updatedMovie, movie.id);

    setMovie({...movie, ...updatedMovie});
    setMode("view");
  }

  return (
    <Form onSubmit={handleSubmit}>
      <div className='h2 text-end'>{movie.averageScore}/10</div>

      <Form.Group>
        <Form.Label className="m-0">Title</Form.Label>
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

      <Button className="mt-3" variant="primary" type="submit">Edit</Button>
    </Form>
  )
}

export default EditMovie
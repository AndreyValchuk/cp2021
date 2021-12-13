import { useState } from 'react';
import { Form, Button } from 'react-bootstrap';
import { createReview } from '../services/reviewService';

const AddReview = ({ user, movieId, setMovie }) => {
  const [text, setText] = useState('');
  const [score, setScore] = useState('');

  const addReview = async (e) => {
    e.preventDefault();

    let review = await createReview({ text, score }, movieId);
    review["reviewerUsername"] = user.username;
    setMovie(prevState => ({...prevState, reviews: [review, ...prevState.reviews]}));
  }

  return (
    <Form onSubmit={addReview}>
      <Form.Group className="mt-2">
        <Form.Control as="textarea" rows={3} value={text} onChange={e => setText(e.target.value)} />
      </Form.Group>

      <div className="mt-2">
        <Form.Label className="d-inline">Score</Form.Label>
        <Form.Control className="d-inline ms-2" type="number" value={score} onChange={e => setScore(e.target.value)} style={{width: 75}} />
        <Button className="ms-5" variant="primary" type="submit">Add review</Button>
      </div>
    </Form>
  )
}

export default AddReview
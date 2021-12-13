import { Row, Col } from 'react-bootstrap';

const Review = ({review}) => {
  return (
    <>
      <Row>
        <Col xs={11}>{review.reviewerUsername}</Col>
        <Col xs={1} className='text-end'>{review.score}/10</Col>
      </Row>
      <div>{review.text}</div>
    </>
  )
}

export default Review
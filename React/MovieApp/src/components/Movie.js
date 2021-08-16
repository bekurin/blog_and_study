import React from 'react';
import Proptypes from 'prop-types';
import './Movie.css';
import { Link } from 'react-router-dom';

function Movie({ year, title, summary, poster, rating, genres }) {
    return (
        <div className="movie">
            <Link
                to={{
                    pathname: '/movie-detail',
                    state: { year, title, summary, poster, rating, genres },
                }}
            >
                <img src={poster} alt={title} title={title} />
                <div className="movie_data">
                    <h3 className="movie_title">{title}</h3>
                    <h5 className="movie_year">{year}</h5>
                    <h5 className="movie_rating">★{rating}/10.0</h5>
                    <ul className="movie_genres">
                        {genres.map((genre, index) => {
                            return <li key={index} className="movie_genre">{genre}</li>;
                        })}
                    </ul>
                    <p className="movie_summary">{summary.slice(0, 180)}...</p>
                </div>
            </Link>
        </div>
    )
}

Movie.prototype = {
    year: Proptypes.string.isRequired,
    title: Proptypes.string.isRequired,
    summary: Proptypes.string.isRequired,
    poster: Proptypes.string.isRequired,
    rating: Proptypes.number.isRequired,
    genres: Proptypes.arrayOf(Proptypes.string).isRequired,
};

export default Movie;
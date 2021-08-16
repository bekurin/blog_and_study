const API_KEY = "put yout api key";

const requests = {
  fetchTrending: `/trending/all/week?api_key=${API_KEY}&language=ko-KR`,
  fetchNetflixOriginals: `/discover/tv?api_key=${API_KEY}&with_networks=213&language=ko-KR`,
  fetchTopRated: `/movie/top_rated?api_key=${API_KEY}&language=ko-KR`,
  fetchActionMovies: `/discover/movie?api_key=${API_KEY}&with_genres=28&language=ko-KR`,
  fetchComedyMovies: `/discover/movie?api_key=${API_KEY}&with_genres=35&language=ko-KR`,
  fetchHorrorMovies: `/discover/movie?api_key=${API_KEY}&with_genres=27&language=ko-KR`,
  fetchRomanceMovies: `/discover/movie?api_key=${API_KEY}&with_genres=10749&language=ko-KR`,
  fetchDocumentaries: `/discover/movie?api_key=${API_KEY}&with_genres=99&language=ko-KR`,
};

export default requests;

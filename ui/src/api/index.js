import axios from 'axios';

function create(baseURL, options) {
  const token = localStorage.getItem('token');
  if (token) {
    axios.defaults.headers['Authorization'] = 'Bearer ' + token;
  }
  axios.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8';
  return axios.create(Object.assign({baseURL}, options));
}

export const concert = create(
    `${import.meta.env.VITE_APP_API_URL}/concerts`
);
export const ticketPrice = create(
    `${import.meta.env.VITE_APP_API_URL}/tickets/prices`
);
export const category = create(
    `${import.meta.env.VITE_APP_API_URL}/categories`
);
export const seat = create(
    `${import.meta.env.VITE_APP_API_URL}/seats`
);
export const reservation = create(
    `${import.meta.env.VITE_APP_API_URL}/reservations`
);
export const ticket = create(
    `${import.meta.env.VITE_APP_API_URL}/tickets`
);

export const waiting = create(
    `${import.meta.env.VITE_APP_WEBFLUX_API_URL}/queue`
)

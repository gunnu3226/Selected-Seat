import axios from 'axios';

function create(baseURL, options) {
	const instance = axios.create(Object.assign({ baseURL }, options));
	return instance;
}

export const concert = create(`${import.meta.env.VITE_APP_API_URL}/concerts`);
export const ticketPrice = create(`${import.meta.env.VITE_APP_API_URL}/tickets/prices`);
export const category = create(`${import.meta.env.VITE_APP_API_URL}/categories`);

import { concert } from '@/api/index';

export function getConcertByCategory(params) {
	return concert.get('', { params });
}

export function getConcertById(id, params){
	return concert.get(`/${id}`, { params });
}

export function getConcertRatingById(id){
	return concert.get(`/rating/${id}`);
}

export function getConcertDates(id){
	return concert.get(`/${id}/dates`);
}

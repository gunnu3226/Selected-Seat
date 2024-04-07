import { concert } from '@/api/index';

export function getConcertByCategory(params) {
	return concert.get('/', { params });
}

import {ticketPrice} from '@/api/index';

export function getTicketPrice(id) {
  return ticketPrice.get(`/concerts/${id}`);
}

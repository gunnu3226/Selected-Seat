import {ticket} from "@/api/index.js";

export function getTicket(id) {
  return ticket.get(`/${id}`);
}

import {reservation} from "@/api/index.js"

export function createReservation(data) {
  return reservation.post("", data)
}

export function getReservation(id) {
  return reservation.get(`/${id}`)
}

export function completeReservation(id) {
  return reservation.post(`/${id}`)
}

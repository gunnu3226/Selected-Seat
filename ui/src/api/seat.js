import {seat} from "@/api/index.js"

export function getSeat(params) {
  return seat.get('', {params});
}

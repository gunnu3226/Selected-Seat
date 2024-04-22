import {waiting} from "@/api/index.js"

export function isAllow(params) {
  return waiting.get("/allowed", {params: params});
}

export function getRankForMember(params) {
  return waiting.get("/rank", {params: params});
}

export function exitQueue(data) {
  return waiting.delete("/exit", {data})
}

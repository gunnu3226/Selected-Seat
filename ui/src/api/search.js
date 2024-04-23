import {search} from "@/api/"

export function getSuggestions(params) {
  return search.get("/suggestions", {params});
}

export function searchKeyword(params) {
  return search.get("", {params});
}

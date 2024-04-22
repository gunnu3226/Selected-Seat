import {search} from "@/api/"

export function getSuggestions(data) {
  return search.get("/suggestions", data);
}

import { category } from "@/api/index.js"

export function  getCategories() {
  return category.get("")
}

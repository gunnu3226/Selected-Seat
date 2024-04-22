import {member} from "@/api/index.js"

export function signup(data) {
  return member.post("/sign-up", data);
}

export function login(data) {
  return member.post("/login", data);
}

export function getEmailAuthCode(data){
  return member.post("/invites", data);
}

export function checkEmailAuthCode(data){
  return member.post("/invites/check", data);
}

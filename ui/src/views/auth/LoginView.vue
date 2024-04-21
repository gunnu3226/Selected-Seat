<template>
	<section class="login-section-wrapper">
		<div class="container d-flex justify-content-center align-items-center">
			<div>
				<img src="/src/assets/logo.svg" />
			</div>
			<div class="login-form-wrapper">
				<form @submit.prevent>
					<div class="input-group mb-3">
						<span class="input-group-text" id="inputGroup-sizing-default"
							>이메일</span
						>
						<input
							type="text"
							class="form-control"
							name="email"
							v-model.trim="email"
						/>
					</div>

					<div class="input-group mb-3">
						<span class="input-group-text" id="inputGroup-sizing-default"
							>비밀번호</span
						>
						<input
							type="password"
							class="form-control"
							name="password"
							v-model.trim="password"
						/>
					</div>
					<button type="button" class="btn login-btn" @click="requestLogin">
						로그인
					</button>
				</form>
			</div>
		</div>
	</section>
</template>

<script setup>
import { ref } from 'vue';
import {login} from "@/api/member.js";
import {useRouter} from "vue-router";
import {useAuthenticationStore} from "@/store/authenticated.js";

const store = useAuthenticationStore();
const router = useRouter();
const email = ref('');
const password = ref('');

const requestLogin = async() => {
  if (email.value.trim() === "" || password.value.trim() === "") {
    alert("아이디 및 비밀번호를 입력해주세요");
    return;
  }

  await login({
    email: email.value,
    password: password.value,
  }).then(res => {
    const memberId = res.data.data.memberId;
    const token = res.headers.get("authorization").slice(7);

    localStorage.setItem("token", token);
    localStorage.setItem("memberId", memberId);
    store.setAuthenticated(true);
    router.push("/")
  }).catch(ex => {
    alert("아이디 혹은 비밀번호가 틀립니다");
    email.value = '';
    password.value = '';
  })
}
</script>
<style scoped>
.login-form-wrapper {
	margin-left: 8rem;
	color: #9963e0;
}

.login-section-wrapper {
	display: flex;
	align-items: center;
	height: 65%;
	margin-top: 10rem;
}

.login-btn {
	width: 100%;
	background-color: #9963e0;
	color: white;
	margin-top: 2rem;
}
</style>

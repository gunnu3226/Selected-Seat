<template>
	<section class="signup-section-wrapper">
		<div
			class="container d-flex flex-column justify-content-center align-items-center"
		>
			<div class="d-flex justify-content-center"></div>
			<div class="signup-form-wrapper">
				<h2
					class="signup-title text-center mb-5 flex-column d-flex justify-content-center align-items-center"
				>
					회원가입
				</h2>
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
							>생년월일</span
						>
						<input
							type="date"
							class="form-control"
							name="birth"
							v-model.trim="birth"
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

					<div class="input-group mb-3">
						<span class="input-group-text" id="inputGroup-sizing-default"
							>비밀번호 확인</span
						>
						<input
							type="password"
							class="form-control"
							name="passwordCheck"
							v-model.trim="passwordCheck"
						/>
					</div>
					<button type="button" class="btn signup-btn" @click="requestSignup">
						회원가입
					</button>
				</form>
			</div>
		</div>
	</section>
</template>

<script setup>
import { ref } from 'vue';
import {signup} from "@/api/member.js";
import {useRouter} from "vue-router";

const router = useRouter();
const email = ref('');
const birth = ref('');
const password = ref('');
const passwordCheck = ref('');
const requestSignup = async () => {
  if (password.value !== passwordCheck.value) {
    alert("비밀번호가 일치 하지 않습니다");
    this.password.value = '';
    this.passwordCheck.value = '';
    return;
  }

  await signup({
    email: email.value,
    password: password.value,
    profile: "",
    birth: birth.value,
  }).then(response => {
    router.push("/login");
  })
};
</script>
<style scoped>
.signup-form-wrapper {
	color: #9963e0;
}

.signup-section-wrapper {
	margin-top: 5rem;
	height: 65%;
}

.signup-btn {
	width: 100%;
	background-color: #9963e0;
	color: white;
	margin-top: 2rem;
}
</style>

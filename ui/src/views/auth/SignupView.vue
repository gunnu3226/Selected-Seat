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
            <button v-if="!isEmailAuthenticated" class="btn btn-primary"
                    @click="emailAuthenticated">이메일 인증
            </button>
          </div>
          <span v-if="isEmailAuthenticated">
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

					<div class="input-group mb-1">
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
          <p class="mb-3"><small>비밀번호: 8글자~15자, 대문자 1개, 소문자 1개, 숫자 1개 이상</small></p>
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
            </span>
        </form>
      </div>
    </div>
  </section>
</template>

<script setup>
import {ref} from 'vue';
import {checkEmailAuthCode, getEmailAuthCode, signup} from "@/api/member.js";
import {useRoute, useRouter} from "vue-router";

const router = useRouter();
const email = ref('');
const code = ref('');
const birth = ref('');
const password = ref('');
const passwordCheck = ref('');
const isEmailAuthenticated = ref(false);
const route = useRoute();
(async () => {
  if (localStorage.getItem("isEmailAuthenticated") !== null) {
    email.value = route.query['email'];
    isEmailAuthenticated.value = true;
    return;
  }

  async function checkAuthCode() {
    await checkEmailAuthCode({
      email: route.query['email'],
      code: route.query['code'],
    }).then(response => {
      alert("이메일이 인증되었습니다");
      email.value = route.query['email'];
      isEmailAuthenticated.value = true;
      localStorage.setItem('isEmailAuthenticated', true);
    }).catch(ex => {
      alert("이메일 인증에 실패했습니다");
      email.value = "";
    });
  }

  if (route.query['code']
      && route.query['email']
      && localStorage.getItem("isEmailAuthenticated") === null
  ) {
    await checkAuthCode();
  }
})();

const emailAuthenticated = async () => {
  if (email.value.trim() === '') {
    alert("이메일을 입력해주세요");
    return;
  }

  await getEmailAuthCode({
    email: email.value,
  }).then(response => {
    alert("인증 메일이 발송되었습니다");
  });
}

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
    localStorage.removeItem("isEmailAuthenticated");
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

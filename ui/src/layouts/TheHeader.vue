<template>
	<nav class="navbar bg-body-tertiary px-4 py-1 border-bottom">
		<div class="container">
			<RouterLink class="navbar-brand" to="/">
				<img
					src="/src/assets/logo.svg"
					alt="Logo"
					width="100"
					height="100"
					class="d-inline-block align-text-top"
				/>
			</RouterLink>
			<form role="search" class="search-form" @submit.prevent>
				<input
					class="form-control"
					type="search"
					placeholder="Search"
					aria-label="Search"
					v-model="keyword"
					@keyup="searching"
					@keyup.enter="searching"
				/>
			</form>
			<div class="d-flex">
				<ul class="nav" v-if="!isAuthenticated">
					<li class="nav-item">
						<RouterLink to="/login" class="nav-link" active-class="active">
							로그인
						</RouterLink>
					</li>
					<li class="nav-item">
						<RouterLink to="/signup" class="nav-link" active-class="active">
							회원가입
						</RouterLink>
					</li>
				</ul>
				<ul class="nav" v-else>
					<li class="nav-item">
						<RouterLink to="/my-page" class="nav-link" active-class="active">
							마이페이지
						</RouterLink>
					</li>
          <li class="nav-item">
            <a class="nav-link cursor-pointer" @click="logout">
              로그아웃
            </a>
          </li>
				</ul>
			</div>
		</div>
	</nav>
</template>

<script setup>
import { useAuthenticationStore } from '@/store/authenticated';
import {computed, ref} from 'vue';
import {useRouter} from "vue-router";

const store = useAuthenticationStore();
const router = useRouter();
const keyword = ref('');
const isAuthenticated = computed(()=> store.isAuthenticated)
const searching = () => {
	if (keyword.value.trim() === '') {
		return;
	}
	// TODO: implement searching feature
};

const logout = () => {
  store.setAuthenticated(false);
  localStorage.removeItem('token');
  localStorage.removeItem('memberId');
  isAuthenticated.value = false;
  router.push("/login");
}
</script>

<style scoped>
.search-form {
	width: 40%;
}

.cursor-pointer {
  cursor: pointer;
}
</style>

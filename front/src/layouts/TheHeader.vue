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
				<ul class="nav" v-if="store.isAuthenticated">
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
						<RouterLink to="/Mypage" class="nav-link" active-class="active">
							마이페이지
						</RouterLink>
					</li>
				</ul>
			</div>
		</div>
	</nav>
</template>

<script setup>
import { useAuthenticationStore } from '@/store/authenticated';
import { ref } from 'vue';

const store = useAuthenticationStore();

const keyword = ref('');

const searching = () => {
	if (keyword.value.trim() === '') {
		return;
	}
	console.log(keyword.value);
};
</script>

<style scoped>
.search-form {
	width: 40%;
}
</style>

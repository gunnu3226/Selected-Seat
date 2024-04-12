<template>
	<div>
		<h2>home</h2>
		<p>{{ $route.path }}</p>
		<button class="btn btn-primary" @click="$router.push('/about')">
			go to acbout
		</button>
	</div>
</template>

<script setup></script>

<style scoped></style>

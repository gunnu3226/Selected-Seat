<script setup>
import TheHeader from '@/layouts/TheHeader.vue';
import TheView from '@/layouts/TheView.vue';
import TheFooter from '@/layouts/TheFooter.vue';
</script>

<template>
	<TheHeader></TheHeader>
	<TheView></TheView>
	<TheFooter></TheFooter>
</template>

<style scoped>
@import '@/assets/reset.css';

#app {
	height: 100%;
}
</style>

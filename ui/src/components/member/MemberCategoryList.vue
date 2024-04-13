<template>
	<ul class="nav container px-4 px-lg-5 d-flex justify-content-between">
		<div
			class="d-flex flex-grow-1 justify-content-center item align-items-center"
			:class="isActive(category)"
			@click="$emit('selectCategory', category)"
			v-for="category in categories"
			:key="category"
		>
			<li class="px-3">
				{{ category }}
			</li>
		</div>
	</ul>
</template>

<script setup>
import { computed } from 'vue';

const props = defineProps({
	selectedCategory: {
		type: String,
		required: true,
	},
});

const categories = ['티켓내역', '예매내역'];

const isActive = computed(() => {
	return value => {
		return props.selectedCategory === value ? 'active' : '';
	};
});
</script>

<style scoped>
.item {
	color: #540cb3;
	cursor: pointer;
	height: 50px;
	border-radius: 4px;
}

.item.active {
	font-weight: 500;
	color: white;
	background-color: #9963e0;
}
</style>

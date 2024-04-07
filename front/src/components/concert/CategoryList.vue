<template>
	<ul class="nav container px-4 px-lg-5 d-flex justify-content-between">
		<div
			class="d-flex flex-grow-1"
			v-for="category in categories"
			:key="category"
		>
			<li
				class="item px-3"
				:class="isActive(category)"
				@click="$emit('selectCategory', category)"
			>
				{{ category }}
			</li>
			<div class="category-item">|</div>
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

const categories = [
	'아이돌',
	'발라드',
	'페스티벌',
	'인디/록',
	'투어',
	'힙합',
	'팬클럽',
	'기타',
];

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
}

.item.active {
	font-weight: 500;
	color: white;
	background-color: #9963e0;
}

.category-item {
	flex-grow: 1;
	flex-shrink: 0;
	display: flex;
	justify-content: center;
	align-items: center;
	color: #c3a2ed;
}
</style>

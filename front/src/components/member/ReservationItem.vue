<template>
	<div class="card mb-3 p-3 card-wrapper">
		<div class="d-flex justify-content-between align-items-center">
			<div
				class="text-wrapper reservation-date-wrapper text-center flex-grow-1"
			>
				{{ props.reservationDate }}
			</div>
			<div
				class="thumbnail-wrapper flex-grow-1 d-flex justify-content-center align-items-center"
			>
				<img
					src="/src/assets/logo.svg"
					height="100"
					class="rounded-start"
					alt="..."
				/>
			</div>
			<div class="text-wrapper title-wrapper text-center flex-grow-1">
				<h5 class="card-title">{{ props.title }}</h5>
			</div>
			<div class="text-wrapper flex-grow-1 text-center">
				<p class="card-text">{{ props.reservationState }}</p>
			</div>
		</div>
	</div>
</template>

<script setup>
import {} from 'vue';

const props = defineProps({
	reservationDate: {
		type: String,
		required: true,
	},
	thumbnail: {
		type: String,
		required: true,
	},
	title: {
		type: String,
		required: true,
	},
	reservationState: {
		type: String,
		required: true,
	},
});
</script>

<style scoped>
.thumbnail-wrapper {
	border-right: 1p solid #c3a2ed;
}

.text-wrapper {
	color: #540cb3;
}

.title-wrapper {
	border-left: 1px solid #c3a2ed;
	border-right: 1px solid #c3a2ed;
}

.card-wrapper {
	cursor: pointer;
}

.reservation-date-wrapper {
	border-right: 1px solid #c3a2ed;
}
</style>

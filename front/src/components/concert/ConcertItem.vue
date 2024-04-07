<template>
	<div class="col mb-5">
		<div class="card" style="height: 350px">
			<img
				style="height: 60%"
				class="card-img-top"
				src="https://dummyimage.com/450x300/dee2e6/6c757d.jpg"
				alt="..."
			/>
			<div class="card-body p-3">
				<div class="text-left">
					<h5 class="fw-bolder">{{ title }}</h5>
					<p>{{ description }}</p>
					<p>
						<small>{{ startDate }} ~ {{ endDate }}</small>
					</p>
				</div>
			</div>
		</div>
	</div>
</template>

<script setup>
defineProps({
	title: {
		type: String,
		required: true,
	},
	description: {
		type: String,
		required: true,
	},
	startDate: {
		type: String,
		required: true,
	},
	endDate: {
		type: String,
		required: true,
	},
});
</script>

<style scoped></style>

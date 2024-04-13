<template>
	<div class="col mb-5">
		<div class="card" style="height: 350px">
			<img
				style="height: 60%"
				class="card-img-top"
				:src="concertInfo.thumbnail"
				alt="..."
			/>
			<div class="card-body p-3">
				<div class="text-left">
					<h5 class="fw-bolder">{{ concertInfo.name }}</h5>
					<p class="mb-0 mt-4">공연장: {{ concertInfo.hall }}</p>
					<p class="mb-0">
						공연일정: <small>{{ formatter(concertInfo.startedAt) }} ~ {{ formatter(concertInfo.endedAt) }}</small>
					</p>
				</div>
			</div>
		</div>
	</div>
</template>

<script setup>
import {computed} from "vue";

defineProps({
  concertInfo: {
    type: Object,
    required: true,
  }
});

const formatter = computed(() =>{
  return value => {
    return value[0] + "." + value[1] + "." + value[2];
  }
})
</script>

<style scoped></style>

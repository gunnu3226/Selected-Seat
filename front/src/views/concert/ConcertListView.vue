<template>
	<section class="py-5">
		<CategoryList
			:selectedCategory="selectedCategory"
			@selectCategory="selectCategory"
		>
		</CategoryList>
		<div class="container px-4 px-lg-5 mt-5">
			<div class="row gx-4 gx-lg-5 row-cols-2 row-cols-md-3 row-cols-xl-4">
				<div v-for="concertInfo in concertList" :key="concertInfo.title">
					<span class="concert-info-wrapper">
						<ConcertItem
							:title="concertInfo.title"
							:description="concertInfo.description"
							:startDate="concertInfo.startDate"
							:endDate="concertInfo.endDate"
							@click="goToDetail(concertInfo.id)"
						></ConcertItem>
					</span>
				</div>
			</div>
		</div>
	</section>
</template>

<script setup>
import CategoryList from '@/components/concert/CategoryList.vue';
import ConcertItem from '@/components/concert/ConcertItem.vue';
import { ref } from 'vue';
import { useRouter } from 'vue-router';
const selectedCategory = ref('아이돌');
const concertList = [
	{
		id: 1,
		title: '공연 제목',
		description: '공연 설명',
		startDate: '2023.12.03',
		endDate: '2023.12.03',
	},
	{
		id: 2,
		title: '공연 제목',
		description: '공연 설명',
		startDate: '2023.12.03',
		endDate: '2023.12.03',
	},
	{
		id: 3,
		title: '공연 제목',
		description: '공연 설명',
		startDate: '2023.12.03',
		endDate: '2023.12.03',
	},
	{
		id: 4,
		title: '공연 제목',
		description: '공연 설명',
		startDate: '2023.12.03',
		endDate: '2023.12.03',
	},
];

const selectCategory = category => {
	selectedCategory.value = category;
};

const router = useRouter();
const goToDetail = id => {
	router.push({
		name: 'ConcertDetailView',
		params: {
			id,
		},
	});
};
</script>

<style scoped>
.concert-info-wrapper {
	cursor: pointer;
}
</style>

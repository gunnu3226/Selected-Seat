<template>
	<div class="container px-4 d-flex">
		<div class="mt-5 profile-wrapper">
			<div class="g-0 d-flex align-items-center">
				<div class="d-flex justify-content-center">
					<img
						src="/src/assets/unknown.jpeg"
						class="img-fluid rounded-start"
						alt="..."
						width="150"
					/>
				</div>
				<div class="profile-content-wrapper">
					<h5 class="card-title">{{ userInfo.nickname }}</h5>
					<p class="card-text">
						<small class="text-body-secondary follower">
							팔로워: {{ userInfo.follower }}
						</small>
						<small class="text-body-secondary">
							팔로잉: {{ userInfo.following }}
						</small>
					</p>
				</div>
			</div>
		</div>
	</div>

	<MemberCategoryList
		:selectedCategory="selectedCategory"
		@selectCategory="selectCategory"
	/>

	<div class="px-4 px-lg-5 mt-5">
		<div class="row" v-if="selectedCategory === '티켓내역'">
			<div
				class="col-12"
				v-for="ticketInfo in ticketList"
				:key="ticketInfo.title"
			>
				<TicketItem
					:title="ticketInfo.title"
					:concertDate="ticketInfo.concertDate"
					@click="showTicketModal(ticketInfo)"
				></TicketItem>
			</div>
		</div>
		<div class="row" v-else>
			<div class="container mb-5 reservation-category-title">
				<ul class="nav d-flex justify-content-around">
					<li class="">예매일자</li>
					<li class="">공연정보</li>
					<li class="">예매상태</li>
				</ul>
			</div>
			<div
				class="col-12"
				v-for="reservationInfo in reservationList"
				:key="reservationInfo.title"
			>
				<ReservationItem
					:title="reservationInfo.title"
					:reservationDate="reservationInfo.reservationDate"
					:thumbnail="reservationInfo.thumbnail"
					:reservationState="reservationInfo.reservationState"
				></ReservationItem>
			</div>
		</div>
	</div>
</template>

<script setup>
import MemberCategoryList from '@/components/member/MemberCategoryList.vue';
import TicketItem from '@/components/member/TicketItem.vue';
import ReservationItem from '@/components/member/ReservationItem.vue';

import {reactive, ref} from 'vue';

const selectedCategory = ref('티켓내역');

const selectCategory = category => {
	selectedCategory.value = category;
};

const reservationList = [
	{
		reservationDate: '2024.01.02',
		thumbnail: '',
		title: '공연 제목',
		reservationState: '예매완료',
	},
	{
		reservationDate: '2024.01.02',
		thumbnail: '',
		title: '공연 제목',
		reservationState: '예매완료',
	},
	{
		reservationDate: '2024.01.02',
		thumbnail: '',
		title: '공연 제목',
		reservationState: '예매완료',
	},
	{
		reservationDate: '2024.01.02',
		thumbnail: '',
		title: '공연 제목',
		reservationState: '예매완료',
	},
];

const ticketList = [
	{
		title: '공연 제목',
		concertDate: '2023.12.03',
	},
	{
		title: '공연 제목',
		concertDate: '2023.12.03',
	},
	{
		title: '공연 제목',
		concertDate: '2023.12.03',
	},
	{
		title: '공연 제목',
		concertDate: '2023.12.03',
	},
];

const showTicketModal = ticketInfo => {
};

const userInfo = reactive({
	nickname: 'nickname',
	follower: 100,
	following: 200,
});
</script>

<style scoped>
.reservation-category-title {
	color: #540cb3;
	font-weight: 500;
	font-size: 1.2rem;
	margin-top: 2rem;
	border-bottom: 0.5px solid #dacced;
	padding-bottom: 2rem;
}

.profile-wrapper {
	margin-bottom: 8rem;
	color: #540cb3;
}

.follower {
	margin-right: 3rem;
}

.profile-content-wrapper {
	margin-left: 3rem;
}
</style>

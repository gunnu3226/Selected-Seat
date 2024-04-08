<template>
	<div class="container px-4" style="width: 100%">
		<div class="mt-5" style="width: 100%">
			<div class="concert-wrapper" style="border-color: #dacced">
				<div
					class="concert-info-wrapper d-flex"
					style="height: 100%; width: 100%"
				>
					<div class="">
						<img
							src="/src/assets/logo.svg"
							class="img-fluid rounded-start thumbnail"
							alt="..."
						/>
					</div>
					<div class="concert-info">
						<h5 class="concert-info-title">{{ info.title }}</h5>
						<p class="">장소: {{ info.location }}</p>
						<p class="">공연 기간: {{ info.startDate }} ~ {{ info.endDate }}</p>
						<p class="">관람 연령: {{ info.concertRating }}</p>
						<p class="">좌석수: {{ info.totalSeats }} 석</p>
						<p class="d-flex">
							<span class="performers-info-label"> 출연진 </span>
							<span
								class="performer-info"
								v-for="performer in info.performaers"
								:key="performer.name"
							>
								<div>
									<img src="/src/assets/unknown.jpeg" alt="" />
								</div>
							</span>
						</p>
						<p class="">
							<small class="text-body-secondary">Last updated 3 mins ago</small>
						</p>
					</div>
					<div class="seat-info-wrapper">
						<h6 class="mb-4">좌석 등급 정보</h6>
						<p>
							S석:
							<span class="rate-info">
								{{ convert(info.seatsPrice.sRate) }} 원
							</span>
						</p>
						<p>
							A석:
							<span class="rate-info">{{
								convert(info.seatsPrice.aRate)
							}}</span>
						</p>
						<p>
							R석:
							<span class="rate-info">{{
								convert(info.seatsPrice.rRate)
							}}</span>
						</p>
					</div>
				</div>
			</div>
		</div>

		<div class="reservation-form d-flex">
			<div class="input-group input-wrapper">
				<span
					class="input-group-text"
					id="inputGroup-sizing-default"
					style="border-color: #dacced; background-color: #dacced; color: white"
					>날짜선택</span
				>
				<input
					type="date"
					class="form-control"
					aria-label="Sizing example input"
					aria-describedby="inputGroup-sizing-default"
					v-model="date"
					style="border-color: #dacced"
				/>
			</div>
			<div class="input-group input-wrapper">
				<span
					class="input-group-text"
					id="inputGroup-sizing-default"
					style="border-color: #dacced; background-color: #dacced; color: white"
					>등급선택</span
				>
				<select
					class="form-select"
					v-model="rating"
					style="border-color: #dacced"
				>
					<option selected value="R">R</option>
					<option value="S">S</option>
					<option value="A">A</option>
				</select>
			</div>

			<div class="input-wrapper">
				<button type="button" class="btn" @click="reservation">
					RESERVATION
				</button>
			</div>
		</div>
	</div>
</template>

<script setup>
import { useRoute, useRouter } from 'vue-router';
import { onBeforeMount, reactive, ref } from 'vue';

const router = useRouter();
const route = useRoute();
const info = reactive({
	id: 1,
	title: '공연제목',
	location: '공연장소',
	concertRating: '관람 연령',
	totalSeats: '공연 좌석수',
	thumbnail: '/src/assets/log.svg',
	startDate: '2024.02.04',
	endDate: '2024.04.10',
	performaers: [
		{
			name: '이름',
			profile: '프로필',
		},
		{
			name: '이름',
			profile: '프로필',
		},
	],
	seatsPrice: {
		rRate: 140000,
		sRate: 110000,
		aRate: 80000,
	},
	concertDates: [
		{
			date: '2024.03.24',
		},
		{
			date: '2024.03.30',
		},
	],
});

onBeforeMount(async () => {
	console.log('정보 요청', route.params.id);
});

const convert = price => {
	return price.toLocaleString();
};

const date = ref(new Date().toISOString().slice(0, 10));
const rating = ref('R');

const reservation = () => {
	console.log(date.value, rating.value);

	router.push({
		name: 'TicketSelectView',
		state: {
			date: date.value,
			concertId: info.id,
		},
	});
};
</script>

<style scoped>
.concert-wrapper {
	height: 280px;
}

.thumbnail {
	height: 100%;
}

.concert-info {
	margin-left: 5rem;
}

.concert-info-wrapper {
	position: relative;
	width: 100%;
}

.concert-info-title {
	font-weight: 500;
	font-size: 2rem;
	margin-bottom: 1.5rem;
}

.performers-info-label {
	margin-right: 2rem;
}

.performer-info {
	margin: 0 0.5rem;
}

.performer-info img {
	width: 50px;
}

.seat-info-wrapper {
	position: absolute;
	border: 2px solid #c3a2ed;
	right: 0;
	top: 40%;
	width: 200px;
	padding: 1rem;
	border-radius: 4px;
}

.rate-info {
	margin-left: 3rem;
}

.reservation-form {
	margin-top: 10rem;
}

.input-wrapper {
	margin: 0 1rem;
}

.input-wrapper button {
	margin-left: 4rem;
	border-radius: 4px;
	background-color: #c3a2ed;
	color: white;
	padding: 0.5rem 1.5rem;
}
</style>

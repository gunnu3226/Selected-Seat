<template>
	<div class="container px-4" style="width: 100%">
		<div class="mt-5" style="width: 100%">
			<ul class="nav justify-content-center">
				<li class="nav-item">
					<button
						class="btn select-btn"
						:class="isActive('R')"
						aria-current="page"
						href="#"
						@click="select('R')"
					>
						R석
					</button>
				</li>
				<li class="nav-item">
					<button
						class="btn select-btn"
						:class="isActive('S')"
						aria-current="page"
						href="#"
						@click="select('S')"
					>
						S석
					</button>
				</li>
				<li class="nav-item">
					<button
						class="btn select-btn"
						:class="isActive('A')"
						aria-current="page"
						href="#"
						@click="select('A')"
					>
						A석
					</button>
				</li>
				<li class="nav-item">
					<button
						class="btn select-btn reservation-ticket-btn"
						aria-current="page"
						href="#"
						@click="reservationSelectSeat"
					>
						예매하기
					</button>
				</li>
			</ul>

			<div class="seats-wrapper mt-5">
				<div class="row g-0 seat-row">
					<div
						class="seat"
						:class="isSelected(x)"
						v-for="(x, i) in concertInfo[selectRate]"
						:key="selectRate + x + i"
						@click.stop="selectSeat(x)"
					></div>
				</div>
			</div>
		</div>
	</div>
</template>

<script setup>
import { ref, reactive, computed } from 'vue';
const concertDate = ref(history.state.dateId);
const concertId = ref(history.state.concertId);

const concertInfo = reactive({
	row: 10,
	R: 1000,
	S: 2000,
	A: 2000,
});

const selectRate = ref('R');
const selectSeatNumber = ref('');
const selectNumber = ref('');

const isActive = computed(() => {
	return value => {
		return selectRate.value === value ? 'active' : '';
	};
});

const select = rate => {
	selectRate.value = rate;
	selectNumber.value = '';
	selectSeatNumber.value = '';
	console.log('좌석 요청', concertDate, concertId, selectRate);
};

const selectSeat = seatNum => {
	selectNumber.value = seatNum;
	selectSeatNumber.value =
		selectRate.value +
		' ' +
		Math.ceil(seatNum / concertInfo.row) +
		':' +
		(seatNum % concertInfo.row === 0
			? concertInfo.row
			: seatNum % concertInfo.row);
};

const isSelected = computed(() => {
	return value => {
		return selectNumber.value === value ? 'selected' : '';
	};
});

const reservationSelectSeat = () => {
	if (selectSeatNumber.value.trim() === '') {
		alert('좌석을 선택해 주세요.');
	}

	console.log('예약요청', selectSeatNumber.value);
};
</script>

<style scoped>
.select-btn {
	margin: 0 2rem;
	border-radius: 4px;
	background-color: #c3a2ed;
	color: white;
	padding: 0.5rem 1.5rem;
	width: 150px;
}

.select-btn.active {
	background-color: #7d44c7;
}

.seat {
	border: 1px solid;
	width: 20px !important;
	height: 20px !important;
	flex-shrink: 0;
	flex-grow: 0;
	cursor: pointer;
	border: 1px solid white;
	background-color: #c3a2ed;
}

.seat.selected {
	background-color: gray;
}

.reservation-ticket-btn {
	background-color: #db41f6;
	margin-left: 5rem;
}
</style>

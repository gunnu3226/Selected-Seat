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
              :class="reservationBtnActive"
              :disabled="reservationBtnActive === ''"
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
              :class="[isSelected(x), isAvailable(x)]"
              v-for="(x, i) in seats"
              :key="selectRate + x + i"
              @click.stop="selectSeat(x)"
          ></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {computed, ref} from 'vue';
import {getSeat} from '@/api/seat.js';
import {createReservation} from "@/api/reservation.js";
import {useRouter} from "vue-router";

const router = useRouter();
const concertId = ref(history.state.concertId);
const concertDate = ref(history.state.dateId);
const selectRate = ref('R');
const seatInfo = ref({});
const seats = ref([]);
const key = ref('');
const getSeats = async () => {
  const response = await getSeat({
    concert: concertId.value,
    concertDate: concertDate.value,
    ticketRating: selectRate.value
  });

  seatInfo.value = response.data.data.seats;
  seats.value = Object.keys(seatInfo.value).sort();
  key.value = response.data.data.key;
}

(async () => {
  await getSeats()
})();
const selectSeatNumber = ref('');
const selectNumber = ref('');
const selectTicketId = ref(-1);

const isActive = computed(() => {
  return value => {
    return selectRate.value === value ? 'active' : '';
  };
});

const select = async (rate) => {
  selectRate.value = rate;
  selectNumber.value = '';
  selectSeatNumber.value = '';

  const response = await getSeat({
    concert: concertId.value,
    concertDate: concertDate.value,
    ticketRating: selectRate.value
  });

  seatInfo.value = response.data.data.seats;
  seats.value = Object.keys(seatInfo.value).sort();
  key.value = response.data.data.key;
};

const selectSeat = seatNum => {
  const tokenize = seatNum.split(":");
  selectTicketId.value = tokenize[1];
  selectNumber.value = seatNum;
};

const isAvailable = computed(() => {
  return value => {
    return seatInfo.value[value] ? 'available' : '';
  }
})

const isSelected = computed(() => {
  return value => {
    return selectNumber.value === value ? 'selected' : '';
  };
});

const reservationBtnActive = computed(() => {
  return selectTicketId.value !== -1 ? 'active' : '';
})

const reservationSelectSeat = async () => {
  if (selectTicketId.value === -1) {
    alert('좌석을 선택해 주세요.');
  }

  try {
    const response = await createReservation({
      concertId: concertId.value,
      memberId: 1,
      ticketId: selectTicketId.value,
      concertDateId: concertDate.value
    });

    await router.push({
      name: 'TicketReservationView',
      state: {
        reservationId: response.data.data.reservationId,

      },
    });
  } catch (error) {
    selectTicketId.value = '';
    selectNumber.value = '';
    alert("이미 선점된 좌석 입니다.");
    await getSeats()
  }
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
  background-color: lightblue;
  pointer-events: none;
}

.seat.available {
  background-color: #c3a2ed;
  pointer-events: all;
  cursor: pointer;
}

.seat.selected {
  background-color: gray;
}

.reservation-ticket-btn {
  margin-left: 5rem;
  cursor: pointer;

}

.reservation-ticket-btn.active {
  background-color: #db41f6;
  cursor: pointer;
}

.reservation-ticket-btn:disabled {
  cursor: not-allowed;
}
</style>

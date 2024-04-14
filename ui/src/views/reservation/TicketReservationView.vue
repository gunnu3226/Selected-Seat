<template>
  <div class="container px-4" style="width: 100%">
    <div class="mt-5" style="width: 100%">
      <div class="card">
        <div class="row" style="width: 100%; height: 400px; max-height: 500px">
          <div class="col">
            <img :src="concertInfo.thumbnail" class="img-fluid rounded-start" alt="...">
          </div>
          <div class=" col-8 d-flex flex-column justify-content-between">
            <div class="col">
              <div class="card-body">
                <h5 class="concert-info-title">{{ concertInfo.name }}</h5>
                <p class="">장소: {{ concertInfo.hall }}</p>
                <p class="">공연 기간: {{ formatter(concertInfo.startedAt) }} ~
                  {{ formatter(concertInfo.endedAt) }}</p>
                <p class="">관람 연령: {{ concertRating }}</p>
                <p class="">좌석수: {{ concertInfo.ticketAmount }} 석</p>
              </div>
              <hr>
              <div
                  class="card-body ticket-info d-flex justify-content-center align-items-center">
                <p class="mb-0 py-3">예매 좌석: {{ ticketInfo.ticketRating }} 석
                  {{
                    seatFormatter(ticketInfo.ticketNumber)
                  }}
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="mt-5 d-flex justify-content-end">
        <button class="btn reservation-btn" @click="complete">예매 확정 하기</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import {computed, ref} from "vue";
import {completeReservation, getReservation} from "@/api/reservation.js"
import {useRouter} from "vue-router";
import {getConcertById, getConcertRatingById} from "@/api/concert.js";
import {getTicket} from "@/api/ticket.js";

const reservationId = ref(history.state.reservationId);
const router = useRouter();
const concertInfo = ref({});
const ticketInfo = ref({});
const concertRating = ref("");

(async () => {
  const response = await getReservation(reservationId.value);
  const concertId = response.data.data.concertId;
  const ticketId = response.data.data.ticketId;
  const concertResponse = await getConcertById(concertId);
  const ticketResponse = await getTicket(ticketId);
  const concertRatingResponse = await getConcertRatingById(
      concertResponse.data.data.ratingId);
  concertInfo.value = concertResponse.data.data;
  ticketInfo.value = ticketResponse.data.data;
  concertRating.value = concertRatingResponse.data.data.rating;

})();

const complete = async () => {
  await completeReservation(reservationId.value);
  alert("예매가 확정되었습니다");
  await router.push("/");
}

const formatter = computed(() => {
  return value => {
    return value != null && (value[0] + "." + value[1] + "." + value[2]);
  }
})

const seatFormatter = computed(() => {
  return value => {
    if (value != null && value.length > 0) {
      let words = value.split(":");
      return words[0] + " 행 " + words[1] + " 열";
    }
  }
})
</script>

<style scoped>
.reservation-btn {
  border-radius: 4px;
  background-color: #c3a2ed;
  color: white;
  padding: 0.5rem 1.5rem;
}

.ticket-info {
  border: 1px solid #c3a2ed;
  font-size: 1.2rem;
  font-weight: bold;
}
</style>

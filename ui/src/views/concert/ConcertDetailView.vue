<template>
  <div class="container px-4" style="width: 100%;">
    <div class="mt-5" style="width: 100%; height: 500px;">
      <div class="concert-wrapper" style="border-color: #dacced">
        <div
            class="concert-info-wrapper d-flex"
            style="height: 500px; width: 100%"
        >
          <div class="">
            <img
                :src="concertInfo.thumbnail"
                class="img-fluid rounded-start thumbnail"
                alt="..."
            />
          </div>
          <div class="concert-info">
            <h5 class="concert-info-title">{{ concertInfo.name }}</h5>
            <p class="">장소: {{ concertInfo.hall }}</p>
            <p class="">공연 기간: {{ formatter(concertInfo.startedAt) }} ~
              {{ formatter(concertInfo.endedAt) }}</p>
            <p class="">관람 연령: {{ concertRating }}</p>
            <p class="">좌석수: {{ concertInfo.ticketAmount }} 석</p>
            <p class="d-flex">
              <span class="performers-info-label"> 출연진 </span>
              <!--							<span-->
              <!--								class="performer-info"-->
              <!--								v-for="performer in info.performaers"-->
              <!--								:key="performer.name"-->
              <!--							>-->
              <!--								<div>-->
              <!--									<img src="/src/assets/unknown.jpeg" alt="" />-->
              <!--								</div>-->
              <!--							</span>-->
            </p>
            <p class="">
              <small class="text-body-secondary">Last updated 3 mins ago</small>
            </p>
          </div>
          <div class="seat-info-wrapper">
            <h6 class="mb-4">좌석 등급 정보</h6>
            <p>
              R석:
              <span class="rate-info">
                {{ convert(ticketPriceInfo.R) }} 원
              </span>
            </p>
            <p>
              S석:
              <span class="rate-info">
								{{ convert(ticketPriceInfo.S) }} 원
							</span>
            </p>
            <p>
              A석:
              <span class="rate-info">
                {{ convert(ticketPriceInfo.A) }} 원
              </span>
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
        >공연 날짜 선택</span
        >
        <select
            class="form-select"
            v-model="dateId"
            style="border-color: #dacced"
        >
          <option selected value="-1">날짜선택</option>
          <option :value="concertDate.concertDateId"
                  v-for="(concertDate) in concertDates">{{
              formatter(concertDate.concertDate)
            }}
          </option>
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
import {useRoute, useRouter} from 'vue-router';
import {computed, reactive, ref} from 'vue';
import {getConcertById, getConcertDates, getConcertRatingById} from '@/api/concert.js';
import {getTicketPrice} from "@/api/ticketPrice.js"

const router = useRouter();
const route = useRoute();
const concertRating = ref("");
let concertInfo = reactive({})
let concertDates = ref([]);
const ticketPriceInfo = reactive({
  R: 0,
  S: 0,
  A: 0,
});

(async () => {
  const response = await getConcertById(route.params.id);
  const ticketPriceResponse = await getTicketPrice(route.params.id);
  const datesResponse = await getConcertDates(route.params.id);
  const concertRatingResponse = await getConcertRatingById(
      response.data.data.regionId);

  concertRating.value = concertRatingResponse.data.data.rating;
  concertInfo = response.data.data;
  concertDates.value = datesResponse.data.data;
  ticketPriceInfo.R = ticketPriceResponse.data.data.filter(
      x => x.ticketRating === 'R')[0].price;
  ticketPriceInfo.S = ticketPriceResponse.data.data.filter(
      x => x.ticketRating === 'S')[0].price;
  ticketPriceInfo.A = ticketPriceResponse.data.data.filter(
      x => x.ticketRating === 'A')[0].price;
})();

const formatter = computed(() => {
  return value => {
    return value != null && (value[0] + "." + value[1] + "." + value[2]);
  }
})
const convert = price => {
  return price.toLocaleString();
};

const dateId = ref(-1);

const reservation = () => {
  if (dateId.value === -1) {
    alert("공연 날짜를 선택해 주세요");
    return
  }

  router.push({
    name: 'WaitingRoomView',
    state: {
      dateId: dateId.value,
      concertId: concertInfo.concertId,
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
  border-radius: 4px;
  object-fit: cover;
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
  min-width: 200px;
  max-width: 300px;
  padding: 1rem;
  border-radius: 4px;
}

.rate-info {
  margin-left: 2rem;
}

.reservation-form {
  margin-top: 5rem;
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

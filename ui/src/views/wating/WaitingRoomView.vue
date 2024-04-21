<template>
  <div class="waiting-room-wrapper">
    <div class="message">
      <p><span class="number-sub-title">좌석 선택 진입중</span></p>
      <h2 class="number-title">나의 대기순서 <span
          id="number">{{ parsingRank }}</span> 번째</h2>
      <p class="mb-1 ">현재 접속량이 많아 대기 중입니다. </p>
      <p class="mb-1 ">잠시만 기다려 주시면 다음 단계로 자동 접속됩니다.</p>
      <p class="mb-1 color-danger"> (주의) 새로 고침 혹은 창을 닫으면 대기순서가 초기화됩니다.</p>
      <p class="mb-1 color-danger">주의해주세요.</p>
      <br/>
    </div>
  </div>
</template>

<script setup>
import {useRouter} from "vue-router";
import {computed, onBeforeUnmount, ref} from "vue";
import {getRankForMember, isAllow} from "@/api/waiting.js";

const router = useRouter();
const concertId = ref(history.state.concertId);
const concertDate = ref(history.state.dateId);
const rank = ref(0);
const memberId = localStorage.getItem("memberId");

const timer = ref();
(async () => {
  await isAllow({
    queue: "seat",
    member_id: memberId
  }).then(response => {
    if (response.data) {
      gotoSeatSelectView();
    } else {
      getRank();
      timer.value = setInterval(getRank, 3000);
    }
  });
})();

const gotoSeatSelectView = () => {
  router.push({
    name: "TicketSelectView",
    state: {
      dateId: concertDate.value,
      concertId: concertId.value,
    },
  });
}

const getRank = async () => {
  const response = await getRankForMember({
    queue: "seat",
    member_id: memberId
  });

  rank.value = response.data;

  if (rank.value === -1) {
    gotoSeatSelectView();
  }
}

onBeforeUnmount(() => clearInterval(timer.value))
const parsingRank = computed(() => rank.value.toLocaleString());

</script>

<style scoped>
.waiting-room-wrapper {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: #292929;
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 100;
}

.message {
  text-align: center;
  padding: 2rem;
  background-color: #fff;
  border-radius: 4px;
}

.number-title {
  font-weight: bold;
  font-size: 2rem;
  color: #540cb3;
  margin: 2rem 0;
}

.number-sub-title {
  background-color: #540cb3;
  text-align: center;
  padding: 0.5rem;
  border-radius: 2px;
  font-weight: normal;
  color: white;
}

.color-danger {
  color: crimson;
}
</style>

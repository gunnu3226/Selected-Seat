<template>
  <section class="py-5">
    <div class="container px-4 px-lg-5">
      <div class="search-bar-wrapper">
        <span class="filter-title">키워드</span>
        <input type="text" v-model="keyword" class="search-input">
      </div>
      <div>
        <span class="filter-title">지역</span>
        <label class="check-label">서울</label>
        <input class="check-item" type="checkbox" value="서울" v-model="region">
        <label class="check-label">경기/인천</label>
        <input class="check-item" type="checkbox" value="경기/인천"
               v-model="region">
        <label class="check-label">대전/충청/강원</label>
        <input class="check-item" type="checkbox" value="대전/충청/강원"
               v-model="region">
        <label class="check-label">부산/대구/경상</label>
        <input class="check-item" type="checkbox" value="부산/대구/경상"
               v-model="region">
        <label class="check-label">광주/전라/제주</label>
        <input class="check-item" type="checkbox" value="광주/전라/제주"
               v-model="region">
      </div>
      <div>
        <span class="filter-title">판매 상태</span>
        <label class="check-label">판매 예정</label>
        <input class="check-item" type="checkbox" value="판매 예정"
               v-model="states">
        <label class="check-label">판매중</label>
        <input class="check-item" type="checkbox" value="판매중" v-model="states">
        <label class="check-label">판매 종료</label>
        <input class="check-item" type="checkbox" value="판매 종료"
               v-model="states">
      </div>
      <div>
        <span class="filter-title">관람 등급</span>
        <label class="check-label">전체 관람가</label>
        <input class="check-item" type="checkbox" value="전체 관람가"
               v-model="concertRating">
        <label class="check-label">미취학아동입장불가</label>
        <input class="check-item" type="checkbox" value="미취학아동입장불가"
               v-model="concertRating">
        <label class="check-label">15세 이상 관람가</label>
        <input class="check-item" type="checkbox" value="15세 이상 관람가"
               v-model="concertRating">
        <label class="check-label">19세 이상 관람가</label>
        <input class="check-item" type="checkbox" value="19세 이상 관람가"
               v-model="concertRating">
      </div>
      <div class="d-flex align-items-baseline">
        <span class="filter-title">카테고리</span>
        <span class="category-wrapper">
        <label class="check-label">아이돌</label>
        <input class="check-item" type="checkbox" value="아이돌"
               v-model="categories">
        <label class="check-label">팬클럽/팬미팅</label>
        <input class="check-item" type="checkbox" value="팬클럽/팬미팅"
               v-model="categories">
        <label class="check-label">발라드/R&B</label>
        <input class="check-item" type="checkbox" value="발라드/R&B"
               v-model="categories">
        <label class="check-label">힙합/EDM</label>
        <input class="check-item" type="checkbox" value="힙합/EDM"
               v-model="categories">
        <label class="check-label">페스티벌</label>
        <input class="check-item" type="checkbox" value="페스티벌"
               v-model="categories">
        <label class="check-label">인디/록</label>
        <input class="check-item" type="checkbox" value="인디/록"
               v-model="categories">
        <label class="check-label">내한</label>
        <input class="check-item" type="checkbox" value="내한"
               v-model="categories">
        <label class="check-label">그 외</label>
        <input class="check-item" type="checkbox" value="그 외"
               v-model="categories">
        </span>
      </div>
      <div class="search-btn-wrapper">
        <button class="btn btn-secondary reset-btn" @click="resetFilter">초기화</button>
        <button class="btn btn-primary" @click="searchConcert">검색</button>
      </div>
      <hr>
      <div class="row gx-4 gx-lg-5 row-cols-2 row-cols-md-3 row-cols-xl-4">
        <div v-for="concertInfo in concertList" :key="concertInfo.title">
          <span class="concert-info-wrapper">
						<ConcertItem
                :concertInfo="concertInfo"
                @click="goToDetail(concertInfo.id)"
            ></ConcertItem>
					</span>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import {useRoute, useRouter} from "vue-router";
import {computed, ref} from "vue";
import {searchKeyword} from "@/api/search.js";
import ConcertItem from "@/components/concert/ConcertItem.vue";

const router = useRouter();
const region = ref([]);
const states = ref([]);
const concertRating = ref([]);
const categories = ref([]);
const route = useRoute();
const keyword = ref("");
const concertList = ref([]);

(async () => {
  if (route.query.keyword.trim() !== '') {
  }
  await searchKeyword({
    text: route.query.keyword,
  }).then(response => {
    concertList.value = response.data.data.data;
  })
})();

const searchConcert = async () => {
  if (keyword.value.trim() === '') {
    alert("키워드는 필수 항목입니다")
    return;
  }

  let regionsList = Object.keys(region.value).map(key => region.value[key]);
  let statesList = Object.keys(states.value.values()).map(key => states.value[key]);
  let concertRatingsList = Object.keys(concertRating.value.values()).map(key => concertRating.value[key]);
  let categoriesList = Object.keys(categories.value.values()).map(key => categories.value[key]);
  await searchKeyword({
    text: keyword.value,
    regions: regionsList.length === 0? null : regionsList.join(","),
    states: statesList.length === 0 ? null: statesList.join(","),
    categories: categoriesList.length === 0 ? null : categoriesList.join(","),
    concertRatings: concertRatingsList.length === 0 ? null : concertRatingsList.join(",")
  }).then(response => {
    concertList.value = response.data.data.data;
  })
}

const formatter = computed(() => {
  return value => {
    return value[0] + "." + value[1] + "." + value[2];
  }
})

const resetFilter = () => {
  concertList.value = [];
  keyword.value = "";
  region.value = [];
  categories.value = [];
  concertRating.value = [];
  states.value = [];
  router.push("/search")
}

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

.filter-title {
  width: 100px;
  display: inline-block;
  font-weight: 500;
  margin-bottom: .5rem;
}

.check-label {
  margin-right: .5rem;
  flex-shrink: 0;
}

.check-item {
  margin-right: 1rem;
  width: 40px;
  border-right: 1px solid lightgray;
}

.search-btn-wrapper {
  margin-top: 1.5rem;
  display: flex;
  align-items: center;
  justify-content: flex-end;
}

.search-btn-wrapper button {
  width: 100px;
}

.search-input {
  padding-left: .5rem;
  width: 80%;
  flex-grow: 1;
  height: 40px;
}

.search-bar-wrapper {
  margin-bottom: 1rem;
}

.category-wrapper {
  width: 80%;
  display: inline-block;
}

.reset-btn {
  margin-right: 1rem;
}

.concert-info-wrapper {
  cursor: pointer;
}
</style>

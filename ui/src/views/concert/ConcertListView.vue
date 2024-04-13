<template>
  <section class="py-5">
    <CategoryList
        :selectedCategory="selectedCategory"
        :categories="categories"
        @selectCategory="selectCategory"
    >
    </CategoryList>
    <div class="container px-4 px-lg-5 mt-5">
      <div class="row gx-4 gx-lg-5 row-cols-2 row-cols-md-3 row-cols-xl-4">
        <div v-for="concertInfo in concertList" :key="concertInfo.title">
					<span class="concert-info-wrapper">
						<ConcertItem
                :concertInfo="concertInfo"
                @click="goToDetail(concertInfo.concertId)"
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
import {ref} from 'vue';
import {useRouter} from 'vue-router';
import {getConcertByCategory} from '@/api/concert.js'
import {getCategories} from "@/api/category.js";

const selectedCategory = ref('아이돌');
const concertList = ref([]);
const categories = ref({
  "아이돌": 0,
  "발라드": 0,
  "페스티벌": 0,
  "인디/록": 0,
  "투어": 0,
  "힙합": 0,
  "팬클럽": 0,
  "그외": 0,
});

(async () => {
  const categoryResponse = await getCategories();
  categoryResponse.data.data.forEach((category) => {
    console.log(category)

    categories.value[category.name] = category.categoryId;
  });

  const response = await getConcertByCategory(
      {category: categories.value[selectedCategory.value]});
  concertList.value = response.data.data;
})();

const selectCategory = async (categoryId, name) => {
  console.log(categoryId, name);
  selectedCategory.value = name;
  const response = await getConcertByCategory({category: categoryId});
  concertList.value = response.data.data;
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

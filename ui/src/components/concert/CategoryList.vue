<template>
  <ul class="nav container px-4 px-lg-5 d-flex justify-content-between">
    <li
        class="d-flex flex-grow-1"
        v-for="(id, category) in categories"
        :key="id"
    >
      <div
          class="item px-3"
          :class="isActive(id)"
          @click="$emit('selectCategory', id)"
      >
        {{ category }}
      </div>
      <div class="category-item">|</div>
    </li>
  </ul>
</template>

<script setup>
import {computed, ref} from 'vue';
import {getCategories} from "@/api/category.js"

const categories = ref({
  "콘서트": 0,
  "아이돌": 0,
  "발라드": 0,
  "페스티벌": 0,
  "인디/록": 0,
  "투어": 0,
  "힙합": 0,
  "팬클럽": 0,
});

const props = defineProps({
  selectedCategory: {
    type: Number,
    required: true,
  },
});

(async () => {
  const response = await getCategories({category: props.selectedCategory});
  response.data.data.forEach((category) => {
    categories.value[category.name] = category.categoryId;
  });
})();

const isActive = computed(() => {
  return value => {
    return props.selectedCategory === value ? 'active' : '';
  };
});
</script>

<style scoped>
.item {
  color: #540cb3;
  cursor: pointer;
}

.item.active {
  font-weight: 500;
  color: white;
  background-color: #9963e0;
}

.category-item {
  flex-grow: 1;
  flex-shrink: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  color: #c3a2ed;
}
</style>

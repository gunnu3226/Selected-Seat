<template>
  <ul class="nav container px-4 px-lg-5 d-flex justify-content-between">
    <div class="category-item">|</div>
    <li
        class="d-flex flex-grow-1"
        v-for="(id, category) in props.categories"
        :key="id"
    >
      <div
          class="item px-3"
          :class="isActive(category)"
          @click="$emit('selectCategory', id, category)"
      >
        {{ category }}
      </div>
      <div class="category-item">|</div>
    </li>
  </ul>
</template>

<script setup>
import {computed} from 'vue';

const props = defineProps({
  selectedCategory: {
    type: String,
    required: true,
  },
  categories: {
    type: Object,
    required: true,
  }
});

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
  border-radius: 4px;
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

<template>
  <nav class="navbar bg-body-tertiary px-4 py-1 border-bottom">
    <div class="container">
      <RouterLink class="navbar-brand" to="/">
        <img
            src="/src/assets/logo.svg"
            alt="Logo"
            width="100"
            height="100"
            class="d-inline-block align-text-top"
        />
      </RouterLink>
      <form  role="search" class="search-form keyword-search-form" @submit.prevent>
        <label for="search" class="search-icon-wrapper">
          <img src="/src/assets/search.png" alt="Logo" class="search-icon" />
        </label>
        <input
            class="search-input"
            type="text"
            name="search"
            placeholder="검색어를 입력해주세요"
            aria-label="Search"
            v-model="keyword"
            @keyup="searching"
            @keyup.enter="searching"
        />
        <ul class="keywords-wrapper" v-show="suggestionsKeyword.length > 0">
          <li class="keyword-item" v-for="(suggestion, i) in suggestionsKeyword" :key="i" @click="moveToSearchView(suggestion.suggestKeyword)">
            {{suggestion.suggestKeyword}}
          </li>
        </ul>
      </form>
      <div class="d-flex">
        <ul class="nav" v-if="!isAuthenticated">
          <li class="nav-item">
            <RouterLink to="/login" class="nav-link" active-class="active">
              로그인
            </RouterLink>
          </li>
          <li class="nav-item">
            <RouterLink to="/signup" class="nav-link" active-class="active">
              회원가입
            </RouterLink>
          </li>
        </ul>
        <ul class="nav" v-else>
          <li class="nav-item">
            <RouterLink to="/my-page" class="nav-link" active-class="active">
              마이페이지
            </RouterLink>
          </li>
          <li class="nav-item">
            <a class="nav-link cursor-pointer" @click="logout">
              로그아웃
            </a>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<script setup>
import {useAuthenticationStore} from '@/store/authenticated';
import {computed, onMounted, ref} from 'vue';
import {useRoute, useRouter} from "vue-router";
import {getSuggestions} from "@/api/search.js";
const route = useRoute();
const store = useAuthenticationStore();
const router = useRouter();
const keyword = ref('');
const isAuthenticated = computed(() => store.isAuthenticated)
const suggestionsKeyword = ref([]);

onMounted(() => {
  const formElement = document.querySelector('.keyword-search-form');
  const searchBar = document.querySelector(".search-input");
  const searchIcon = document.querySelector(".search-icon-wrapper");
  const suggestionList = document.querySelector(".keywords-wrapper");

  formElement.addEventListener("focusin", (e) => {
    searchBar.classList.add("search");
    searchIcon.classList.add("search");
    suggestionList.classList.add("show");
  })

  formElement.addEventListener("focusout", (e) => {
    setTimeout(() => {
      searchBar.classList.remove("search");
      searchIcon.classList.remove("search");
      suggestionList.classList.remove("show");
    }, 1000);
  })

  searchBar.addEventListener("keyup", async (e) => {
    let keyword = e.target.value;
    if (e.keyCode === 13){
      if (keyword.trim() === '') {
        return;
      }

      searchBar.classList.remove("search");
      searchIcon.classList.remove("search");
      suggestionList.classList.remove("show");

      moveToSearchView(keyword);
      return;
    }

    if (keyword.trim() === '') {
      return;
    }

    await getSuggestions({
      text:keyword,
    }).then(response => {
      suggestionsKeyword.value = response.data.data;
    })
  })
})

const moveToSearchView = (text) => {
  router.push({
    name: "SearchView",
    query: {text}
  }).catch(() =>{});
  keyword.value = "";
}

const logout = () => {
  store.setAuthenticated(false);
  localStorage.removeItem('token');
  localStorage.removeItem('memberId');
  isAuthenticated.value = false;
  router.push("/login");
}
</script>

<style scoped>
.search-form {
  width: 40%;
}

.cursor-pointer {
  cursor: pointer;
}

.keyword-search-form {
  display: flex;
  justify-content: center;
  position: relative;
}

.search-input {
  width: 100%;
  max-width: 300px;
  outline: none !important;
  height: 40px;
  padding: 1rem;
  padding-left: .5rem;
  border: 1px solid lightgray;
  border-radius: 4px;
  position: relative;
  border-left: none;
  border-top-left-radius: 0;
  border-bottom-left-radius: 0;
}

.search-input:focus{
  outline: none !important;
}

.search-input::placeholder{
  color: lightgray;
}

.search-input.search {
  border-bottom-right-radius: 0 !important;
  border-bottom-left-radius: 0 !important;
}

.keywords-wrapper {
  position: absolute;
  width: 100%;
  list-style: none;
  padding: 0;
  display: none;
  opacity: 0;
  top: 40px;
  z-index: -100;
  max-width: 340px;
}

.keyword-item {
  border-bottom: 1px solid lightgray;
  border-right: 1px solid lightgray;
  border-left: 1px solid lightgray;
  width: 100%;
  padding: .5rem;
  cursor: pointer;
  background-color: white;
}

.keywords-wrapper.show {
  display: block;
  opacity: 1;
  z-index: 100;
}

.keyword-item:last-of-type {
  border-bottom-right-radius: 4px;
  border-bottom-left-radius: 4px;
}

.search-icon-wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 40px;
  height: 40px;
  border: 1px solid lightgray;
  border-right: none;
  background-color: white;
  border-top-left-radius: 4px;
  border-bottom-left-radius: 4px;
}

.search-icon-wrapper.search {
  border-bottom-left-radius: 0;
  flex-shrink: 0;
}

.search-icon {
  width: 90%;
  height: 90%;
}
</style>

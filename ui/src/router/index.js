import {createRouter, createWebHistory} from 'vue-router';
import ConcertListView from '@/views/concert/ConcertListView.vue';
import NotFoundView from '@/views/NotFoundView.vue';
import LoginView from '@/views/auth/LoginView.vue';
import SignupView from '@/views/auth/SignupView.vue';
import MyPageView from '@/views/member/MyPageView.vue';
import TicketSelectView from '@/views/ticket/TicketSelectView.vue';
import ConcertDetailView from '@/views/concert/ConcertDetailView.vue';
import TicketReservationView
  from "@/views/reservation/TicketReservationView.vue";

import WaitingRoomView from "@/views/wating/WaitingRoomView.vue";
import SearchView from "@/views/search/SearchView.vue";

const routes = [
  {
    path: '/:pathMache(.*)*',
    name: 'NotFound',
    component: NotFoundView,
  },
  {
    path: '/',
    name: 'ConcertListView',
    component: ConcertListView,
    props: true,
  },
  {
    path: '/login',
    name: 'LoginView',
    component: LoginView,
  },
  {
    path: '/signup',
    name: 'SignupView',
    component: SignupView,
  },
  {
    path: '/my-page',
    name: 'MyPageView',
    component: MyPageView,
    meta: {requiresAuth: true}
  },
  {
    path: '/concerts/:id',
    name: 'ConcertDetailView',
    component: ConcertDetailView,
    props: true,
  },
  {
    path: '/ticket/select',
    name: 'TicketSelectView',
    component: TicketSelectView,
    props: true,
    meta: {requiresAuth: true}
  },
  {
    path: '/ticket/reservation',
    name: 'TicketReservationView',
    component: TicketReservationView,
    props: true,
    meta: {requiresAuth: true}
  }, {
    path: '/ticket/waiting',
    name: 'WaitingRoomView',
    component: WaitingRoomView,
    props: true,
    meta: {requiresAuth: true}
  },{
    path: '/search',
    name: 'SearchView',
    component: SearchView,
    props: true,
  }
];

const router = createRouter({
  history: createWebHistory('/'),
  routes: routes,
});

router.beforeEach((to, from) => {

  if (to.meta.requiresAuth && localStorage.getItem('token') === null) {
    alert("로그인이 필요한 서비스입니다")
    return {
      path: "/login",
      query: {redirect: to.fullPath},
    }
  }
});

export default router;

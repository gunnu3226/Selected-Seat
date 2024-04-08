import { createRouter, createWebHistory } from 'vue-router';
import ConcertListView from '@/views/concert/ConcertListView.vue';
import NotFoundView from '@/views/NotFoundView.vue';
import LoginView from '@/views/auth/LoginView.vue';
import SignupView from '@/views/auth/SignupView.vue';
import MyPageView from '@/views/member/MyPageView.vue';
import TicketSelectView from '@/views/ticket/TicketSelectView.vue';
import ConcertDetailView from '@/views/concert/ConcertDetailView.vue';

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
		path: '/mypage',
		name: 'MyPageView',
		component: MyPageView,
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
	},
];

const router = createRouter({
	history: createWebHistory('/'),
	routes: routes,
});

export default router;

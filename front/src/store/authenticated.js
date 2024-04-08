import { defineStore } from 'pinia';

export const useAuthenticationStore = defineStore('authenticated', {
	state: () => ({
		authenticated: localStorage.getItem('authenticated') === 'true',
	}),
	getters: {
		isAuthenticated: state => state.authenticated,
	},
	actions: {
		setAuthenticated(payload) {
			this.state.authenticated = payload;
			localStorage.setItem('authenticated', payload);
		},
	},
});

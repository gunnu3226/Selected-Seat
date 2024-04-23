import {defineStore} from 'pinia';

export const useAuthenticationStore = defineStore('authenticated', {
	state: () => ({
    authenticated: localStorage.getItem('token') !== null,
	}),
	getters: {
		isAuthenticated: state => state.authenticated,
	},
	actions: {
		setAuthenticated(payload) {
      this.authenticated = true;
		},
	},
});

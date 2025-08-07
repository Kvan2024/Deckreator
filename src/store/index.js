import { createStore as _createStore } from 'vuex';
import axios from 'axios';

export function createStore() {
    return _createStore({
  state: {
    username: localStorage.getItem('username') || null,
    token: localStorage.getItem('token') || null
  },
  getters: {
    isLoggedIn: state => !!state.token,
    getUsername: state => state.username
  },
  mutations: {
    LOGIN(state, { token, username }) {
        state.token = token;
        state.username = username;
        localStorage.setItem('token', token);
        localStorage.setItem('username', username);
      },
    LOGOUT(state) {
      state.username = null;
      state.token = null;
      localStorage.removeItem('username');
      localStorage.removeItem('token');
    }
  },
  actions: {
    async loginUser({ commit }, credentials) {
        const response = await axios.post('https://deckreator.onrender.com/auth/login', credentials);
        const token = response.data.token;
        const username = response.data.user.username;
      
        commit('LOGIN', { token, username });
      },
  
    async registerUser(_, userData) {
      await axios.post('https://deckreator.onrender.com/register', userData);
    },
  
    logout({ commit }) {
        commit('LOGOUT');
      }
  }
})
};
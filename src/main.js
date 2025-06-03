import { createApp } from 'vue'
import MyApp from './App.vue'
import { createRouter } from './router'
import { createStore } from './store'

const router = createRouter();
const store = createStore();

const app = createApp(MyApp);
app.use(store);
app.use(router);
app.mount('#app');
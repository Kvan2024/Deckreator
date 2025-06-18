import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vite.dev/config/
export default defineConfig({
  plugins: [vue()],
  server: {
    proxy: {
      '/api': 'https://deckreator.onrender.com',
      '/auth': 'https://deckreator.onrender.com',
      '/auth/login': 'https://deckreator.onrender.com',
      '/register': 'https://deckreator.onrender.com',
      '/cards': 'https://deckreator.onrender.com',
      '/cards/search': 'https://deckreator.onrender.com',
    }
  }
})

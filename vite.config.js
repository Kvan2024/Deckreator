import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vite.dev/config/
export default defineConfig({
  plugins: [vue()],
  server: {
    proxy: {
      '/api': 'http://localhost:8080',
      '/auth': 'http://localhost:8080',
      '/auth/login': 'http://localhost:8080',
      '/register': 'http://localhost:8080',
      '/cards': 'http://localhost:8080',
      '/cards/search': 'http://localhost:8080',
    }
  }
})

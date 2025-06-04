import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vite.dev/config/
export default defineConfig({
  plugins: [vue()],
  server: {
    proxy: {
      '/api': 'http://deckreator.netlify.app',
      '/auth': 'http://deckreator.netlify.app',
      '/auth/login': 'http://deckreator.netlify.app',
      '/register': 'http://deckreator.netlify.app',
      '/cards': 'http://deckreator.netlify.app',
      '/cards/search': 'http://deckreator.netlify.app',
    }
  }
})

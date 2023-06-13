import { fileURLToPath, URL } from 'node:url'
import { defineConfig, loadEnv } from 'vite'
import vue from '@vitejs/plugin-vue'
import * as process from 'node:process'
import Components from 'unplugin-vue-components/vite'
import { VantResolver } from 'unplugin-vue-components/resolvers'
// https://vitejs.dev/config/
export default defineConfig((envConfig) => {
  const env = loadEnv(envConfig.mode, process.cwd())
  return {
    plugins: [vue(), Components({ resolvers: [VantResolver()] })],
    resolve: {
      alias: {
        '@': fileURLToPath(new URL('./src', import.meta.url))
      }
    },
    server: {
      hmr: true,
      host: env.VITE_DEV_SERVER_HOST,
      port: env.VITE_DEV_SERVER_PORT,
      open: true,
      // 开发服务器代理配置
      proxy: {
        // http://localhost:8001/api/test -> http://localhost:8082/login-demo/test
        '^/api': {
          target: env.VITE_DEV_API_URL,
          changeOrigin: true,
          rewrite: (path) => path.replace(/^\/api/, '')
        }
      }
    }
  }
})

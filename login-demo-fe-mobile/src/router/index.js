import { createRouter, createWebHistory } from 'vue-router'
import Cookies from 'js-cookie'
import { TOKEN_COOKIE_KEY } from '@/const/security'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      redirect: { name: 'login' }
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('@/views/common/LoginView.vue')
    },
    {
      path: '/register',
      name: 'register',
      component: () => import('@/views/common/RegisterView.vue')
    },
    // 需要登录后才能访问的路由
    {
      path: '/home',
      name: 'home',
      component: () => import('@/views/main/HomeView.vue'),
      beforeEnter: (to, from, next) => {
        // 获取 Cookie 中的 token
        const token = Cookies.get(TOKEN_COOKIE_KEY)
        console.log('cookie 中的token', token)
        if (!token || !/\S/.test(token)) {
          // 没有登录，回到登录页面
          console.error('用户还未登录')
          next({ name: 'login' })
          return
        }
        next()
      }
    }
  ]
})

router.beforeEach((to, from, next) => {
  console.log(to)
  console.log(from)
  Cookies.set('test', 'test-cookie')
  next()
})

export default router

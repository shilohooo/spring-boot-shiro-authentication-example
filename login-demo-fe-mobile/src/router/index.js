import { createRouter, createWebHistory } from 'vue-router'

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
    }
  ]
})

export default router

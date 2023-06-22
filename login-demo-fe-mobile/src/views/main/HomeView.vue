<script setup>
import { onMounted, ref } from 'vue'
import Cookies from 'js-cookie'
import { TOKEN_COOKIE_KEY } from '@/const/security'
import http from '@/utils/http-request'
import { closeToast, showConfirmDialog, showLoadingToast } from 'vant'
import { showFailToast } from 'vant/es'
import { USER_SESSION_KEY } from '@/const/user'
import { useRouter } from 'vue-router'

onMounted(() => {
  console.log('welcome home')
  getUserInfo()
})

// 当前登录用户信息
const userInfo = ref({
  id: undefined,
  createdDate: undefined,
  modifiedDate: undefined,
  username: undefined
})
// 用户头像
import avatar from '@/assets/images/user/avatar.png'
/**
 * 获取当前登录的用户信息
 * @author shiloh
 * @date 2023/6/22 12:46
 */
const getUserInfo = async () => {
  showLoadingToast({ forbidClick: true, message: '加载中~' })
  const token = Cookies.get(TOKEN_COOKIE_KEY)
  console.log('token', token)
  const headers = {
    'Content-Type': 'application/json;charset=utf-8'
  }
  headers[TOKEN_COOKIE_KEY] = token
  const { data: res } = await http({
    url: '/sys-user/current',
    method: 'GET',
    headers
  })
  if (!res || res.code !== 0) {
    closeToast(false)
    showFailToast({ message: res?.msg || '获取用户信息失败' })
    return
  }
  closeToast(true)
  userInfo.value = res.data
  window.sessionStorage.setItem(USER_SESSION_KEY, JSON.stringify(userInfo.value))
}

/**
 * 退出登录
 * @author shiloh
 * @date 2023/6/22 13:10
 */
const router = useRouter()
const logout = async () => {
  try {
    await showConfirmDialog({
      message: '您确定要退出吗？',
      confirmButtonColor: '#1989FA'
    })
    Cookies.remove(TOKEN_COOKIE_KEY)
    await router.push({ name: 'login' })
  } catch (e) {
    console.error(e)
  }
}

const apps = [
  { name: 'QQ', icon: 'qq', iconColor: '#E61D15' },
  { name: '微信', icon: 'wechat', iconColor: '#28C445' },
  { name: '支付宝', icon: 'alipay', iconColor: '#00A0EA' },
  { name: '微博', icon: 'weibo', iconColor: '#E71F19' },
  { name: '购物', icon: 'shopping-cart', iconColor: '#E71F19' }
]

const activeTab = ref(0)
</script>

<template>
  <van-nav-bar left-arrow left-text="退出" @click-left="logout" title="主页" />
  <van-notice-bar left-icon="volume-o" scrollable color="#1989FA" background="#ecf9ff">
    今天是个好日子~今天是个好日子~今天是个好日子~今天是个好日子~
  </van-notice-bar>
  <div class="h-full bg-sky-300 p-2.5">
    <!--    user info-->
    <div class="bg-white rounded-2xl shadow-2xl p-2.5 gap-2.5 flex justify-center items-center">
      <van-image round width="10rem" height="10rem" fit="cover" :src="avatar" />
      <div class="flex flex-col gap-1 items-end">
        <span class="text-2xl">{{ userInfo.username }}</span>
        <span class="text-sm">{{ userInfo.createdDate }}</span>
        <span class="text-sm text-[#1989FA]">修改个人信息</span>
      </div>
    </div>
    <!--    apps-->
    <div class="flex justify-between gap-2.5 p-2.5 rounded-2xl shadow-2xl mt-2.5 bg-white">
      <div
        v-for="app in apps"
        :key="app.name"
        class="basis-1/4 flex flex-col items-center p-2.5 rounded-xl"
      >
        <van-icon :name="app.icon" size="2rem" :color="app.iconColor" />
        <span class="text-sm">{{ app.name }}</span>
      </div>
    </div>
  </div>
  <!--  menus-->
  <van-tabbar v-model="activeTab" safe-area-inset-bottom>
    <van-tabbar-item icon="home-o">主页</van-tabbar-item>
    <van-tabbar-item icon="chat-o" badge="99">消息</van-tabbar-item>
    <van-tabbar-item icon="user-o">我的</van-tabbar-item>
  </van-tabbar>
</template>

<style scoped></style>

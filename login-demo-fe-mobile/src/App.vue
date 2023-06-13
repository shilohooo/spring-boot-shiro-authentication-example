<script setup>
import { RouterView } from 'vue-router'
import { onMounted } from 'vue'
import http from '@/utils/http-request'

const sendTestRequest = async () => {
  const data = await http({
    url: '/test/greeting',
    params: { name: 'shiloh' }
  })

  console.log(data)
}

onMounted(() => {
  document.title = import.meta.env.VITE_APP_TITLE
  sendTestRequest()
})
</script>

<template>
  <RouterView />
</template>

<style scoped></style>

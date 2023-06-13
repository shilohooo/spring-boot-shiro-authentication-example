<script setup>
const version = import.meta.env.VITE_APP_VERSION
const versionStyle = {
  borderColor: '#909294'
}
</script>

<template>
  <div class="m-5">
    <van-divider :style="versionStyle">{{ version }}</van-divider>
  </div>
</template>

<style scoped></style>

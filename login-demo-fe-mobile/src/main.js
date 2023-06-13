import './assets/main.css'
import 'normalize.css/normalize.css'

// see https://vant-contrib.gitee.io/vant/#/zh-CN/quickstart#4.-yin-ru-han-shu-zu-jian-de-yang-shi
// Toast 样式
import 'vant/es/toast/style'
// Dialog 样式
import 'vant/es/dialog/style'
// Notify 样式
import 'vant/es/notify/style'
// ImagePreview 样式
import 'vant/es/image-preview/style'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import { Icon } from 'vant'

import App from './App.vue'
import router from './router'

const app = createApp(App)

app.use(createPinia())
app.use(router)
app.use(Icon)

app.mount('#app')

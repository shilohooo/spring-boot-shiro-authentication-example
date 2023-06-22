<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import LoginPageBg from '@/assets/images/bg/login-page-bg.png'
import MyVersion from '@/components/common/MyVersion.vue'
import { showFailToast } from 'vant/es'
import { nanoid } from 'nanoid'
import http from '@/utils/http-request'
import { closeToast, showLoadingToast, showSuccessToast } from 'vant'
import Cookies from 'js-cookie'
import { TOKEN_COOKIE_KEY } from '@/const/security'

// 登录参数
const loginData = reactive({
  username: undefined,
  password: undefined,
  captcha: undefined,
  captchaKey: undefined,
  userAgreement: true
})

onMounted(() => {
  const testCookie = Cookies.get('test')
  console.log('testCookie', testCookie)
  getCaptcha()
})

const captchaBase64 = ref('')

/**
 * 获取验证码
 * @author shiloh
 * @date 2023/6/10 16:23
 */
const getCaptcha = async () => {
  try {
    showLoadingToast({
      message: '正在获取验证码~',
      forbidClick: true
    })
    loginData.captchaKey = nanoid()
    const { data } = await http({ url: `/captcha/${loginData.captchaKey}` })
    captchaBase64.value = data
  } finally {
    closeToast(false)
  }
}

/**
 * 创建表单校验规则
 * @param {String} message 校验失败提示信息
 * @param {Boolean} required 是否必填，默认为 true
 * @returns {Object}
 * @author shiloh
 * @date 2023/6/10 11:04
 */
const createFormRule = (message = '请填写该必填项', required = true) => {
  return [{ message, required }]
}

const isShowPassword = ref(false)
/**
 * 显示密码
 * @author shiloh
 * @date 2023/6/10 12:04
 */
const showPassword = () => {
  isShowPassword.value = !isShowPassword.value
}
const passwordFieldType = computed(() => {
  return isShowPassword.value ? '' : 'password'
})

const loginFormRef = ref(null)
/**
 * 登录
 * @author shiloh
 * @date 2023/6/10 11:14
 */
const loginBtnLoading = ref(false)
const login = async () => {
  try {
    await loginFormRef.value.validate()
    if (!loginData.userAgreement) {
      return showFailToast('请先勾选同意用户协议再进行操作')
    }
    console.log('登录参数', loginData)
    showLoadingToast({ forbidClick: true, message: '正在登录中' })
    loginBtnLoading.value = true
    try {
      const { data: res } = await http({
        url: '/login',
        method: 'POST',
        data: loginData,
        headers: {
          'Content-Type': 'application/json; charset=utf-8'
        }
      })
      if (!res || res.code !== 0) {
        closeToast(false)
        showFailToast({
          message: res?.msg || '登录失败，请稍后再试',
          forbidClick: true,
          onClose: async () => {
            await getCaptcha()
          }
        })
        return
      }
      closeToast(false)
      showSuccessToast({
        forbidClick: true,
        message: '登录成功',
        duration: 1500,
        onClose: () => {
          // 登录成功后前往主页
          Cookies.set(TOKEN_COOKIE_KEY, res.data)
          router.replace({ name: 'home' })
        }
      })
    } finally {
      loginBtnLoading.value = false
    }
  } catch (e) {
    console.error(e)
  }
}

const router = useRouter()
/**
 * 跳转到用户注册页面
 * @author shiloh
 * @date 2023/6/10 11:04
 */
const toRegisterPage = () => {
  router.push({ name: 'register' })
}

const userAgreementVisible = ref(false)
/**
 * 弹出用户协议查看对话框
 * @author shiloh
 * @date 2023/6/10 11:05
 */
const viewUserAgreement = () => {
  userAgreementVisible.value = true
}
</script>

<template>
  <van-nav-bar title="登录" />
  <div class="flex justify-center bg-sky-300">
    <van-image :src="LoginPageBg" />
  </div>
  <div>
    <van-form validate-first ref="loginFormRef" label-width="50">
      <van-field
        v-model.trim="loginData.username"
        label="用户名"
        placeholder="请输入用户名"
        name="username"
        left-icon="user-o"
        required
        :rules="createFormRule('请输入用户名')"
      />
      <van-field
        v-model.trim="loginData.password"
        :type="passwordFieldType"
        label="密码"
        placeholder="请输入密码"
        name="password"
        left-icon="lock"
        :right-icon="isShowPassword ? 'closed-eye' : 'eye-o'"
        required
        :rules="createFormRule('请输入密码')"
        autocomplete="true"
        @click-right-icon.stop="showPassword"
      />
      <van-field
        v-model="loginData.captcha"
        label="验证码"
        placeholder="请输入验证码"
        left-icon="shield-o"
        required
        :rules="createFormRule('请输入验证码')"
      >
        <template #extra>
          <van-image :src="captchaBase64" width="100" @click="getCaptcha" height="30">
            <template v-slot:loading>
              <van-loading type="spinner" size="20" />
            </template>
          </van-image>
        </template>
      </van-field>
      <div class="m-5 flex flex-col gap-5">
        <van-button
          type="primary"
          text="登录"
          block
          round
          :disabled="loginBtnLoading"
          :loading="loginBtnLoading"
          @click="login"
        />
      </div>
      <div class="mt-5 flex justify-center items-center">
        <van-checkbox v-model="loginData.userAgreement" />
        <span class="ml-2 text-sm" @click.stop="viewUserAgreement">
          我已阅读<span class="text-[#1989FA] hover:cursor-pointer">用户协议</span>并同意
        </span>
      </div>
    </van-form>
    <div class="m-5">
      <div class="flex justify-center mt-5 text-[#1989FA]">
        <span class="hover:cursor-pointer" @click="toRegisterPage">用户注册 |</span>
        <span class="ml-2 hover:cursor-pointer">忘记密码 |</span>
        <span class="ml-2 hover:cursor-pointer">查看帮助</span>
      </div>
      <van-divider :style="{ borderColor: '#909294' }">第三方登录</van-divider>
      <div class="flex justify-around">
        <van-icon size="2rem" name="wechat" color="#51C332" />
        <van-icon size="2rem" name="alipay" color="#009FE9" />
        <van-icon size="2rem" name="weibo" color="#D12024" />
      </div>
    </div>
    <MyVersion />
  </div>
</template>

<style scoped></style>

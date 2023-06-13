<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import RegisterPageBg from '@/assets/images/bg/register-page-bg.png'
import MyVersion from '@/components/common/MyVersion.vue'
import { showFailToast } from 'vant/es'
import { nanoid } from 'nanoid'
import http from '@/utils/http-request'
import { closeToast, showLoadingToast, showSuccessToast } from 'vant'

const router = useRouter()

/**
 * 跳转到登录页
 * @author shiloh
 * @date 2023/6/13 23:30
 */
const toLoginPage = () => {
  router.push({ name: 'login' })
}

// 登录参数
const registerData = reactive({
  username: undefined,
  password: undefined,
  confirmPassword: undefined,
  captcha: undefined,
  captchaKey: undefined,
  userAgreement: true
})

onMounted(() => {
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
    registerData.captchaKey = nanoid()
    const { data } = await http({ url: `/captcha/${registerData.captchaKey}` })
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

const registerFormRef = ref(null)
const submitBtnLoading = ref(false)
/**
 * 用户注册
 * @author shiloh
 * @date 2023/6/10 11:14
 */
const register = async () => {
  try {
    await registerFormRef.value.validate()
    if (!registerData.userAgreement) {
      return showFailToast('请先勾选同意用户协议再进行操作')
    }
    showLoadingToast({ message: '正在注册中~', forbidClick: true })
    submitBtnLoading.value = true
    console.log('注册参数', registerData)
    const { data } = await http({
      url: '/sys-user-register',
      method: 'post',
      data: registerData,
      headers: {
        'Content-Type': 'application/json; charset=utf-8'
      }
    })
    if (!data || data.code !== 0) {
      closeToast(false)
      showFailToast({
        message: data?.msg || '注册失败，请稍后再试',
        forbidClick: true,
        duration: 2000,
        onClose: async () => {
          await getCaptcha()
        }
      })
      return
    }

    closeToast(false)
    showSuccessToast({
      message: '注册成功，即将跳转到登录页面',
      duration: 3000,
      onClose: () => {
        toLoginPage()
      }
    })
  } catch (e) {
    console.error(e)
  } finally {
    submitBtnLoading.value = false
  }
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
  <van-nav-bar title="注册" left-arrow left-text="返回" @click-left="toLoginPage" />
  <div class="flex justify-center bg-sky-300">
    <van-image :src="RegisterPageBg" />
  </div>
  <van-form validate-first ref="registerFormRef" label-width="70">
    <van-field
      v-model.trim="registerData.username"
      label="用户名"
      placeholder="请输入用户名"
      name="username"
      left-icon="user-o"
      required
      :rules="createFormRule('请输入用户名')"
      maxlength="50"
      show-word-limit
    />
    <van-field
      v-model.trim="registerData.password"
      :type="passwordFieldType"
      label="密码"
      placeholder="请输入密码"
      name="password"
      left-icon="lock"
      :right-icon="isShowPassword ? 'closed-eye' : 'eye-o'"
      required
      :rules="createFormRule('请输入密码')"
      autocomplete="true"
      maxlength="50"
      show-word-limit
      @click-right-icon.stop="showPassword"
    />
    <van-field
      v-model.trim="registerData.confirmPassword"
      :type="passwordFieldType"
      label="确认密码"
      placeholder="请输入确认密码"
      name="password"
      left-icon="checked"
      :right-icon="isShowPassword ? 'closed-eye' : 'eye-o'"
      required
      :rules="
        createFormRule('请输入确认密码').concat([
          {
            validator: (val) => val === registerData.password,
            message: '两次输入的密码不一致'
          }
        ])
      "
      autocomplete="true"
      maxlength="50"
      show-word-limit
      @click-right-icon.stop="showPassword"
    />
    <van-field
      v-model="registerData.captcha"
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
        block
        round
        :disabled="submitBtnLoading"
        :loading="submitBtnLoading"
        @click="register"
      >
        注册
      </van-button>
    </div>
    <div class="mt-5 flex justify-center items-center">
      <van-checkbox v-model="registerData.userAgreement" />
      <span class="ml-2 text-sm" @click.stop="viewUserAgreement">
        我已阅读<span class="text-[#1989FA] hover:cursor-pointer">用户协议</span>并同意
      </span>
    </div>
  </van-form>
  <MyVersion />
</template>

<style scoped></style>

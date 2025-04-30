<template>
  <div class="register-page">
    <van-nav-bar
      title="注册账号"
      left-arrow
      @click-left="$router.go(-1)"
    />
    
    <div class="content-container">
      <div class="form-header">
        <div class="title">欢迎注册</div>
        <div class="subtitle">立即加入拼途，享受便捷出行</div>
      </div>
      
      <van-form @submit="onSubmit">
        <van-cell-group inset>
          <van-field
            v-model="phone"
            name="phone"
            label="手机号"
            placeholder="请输入手机号"
            :rules="[{ required: true, message: '请填写手机号' }]"
          />
          
          <van-field
            v-model="smsCode"
            center
            clearable
            label="验证码"
            placeholder="请输入短信验证码"
            :rules="[{ required: true, message: '请填写验证码' }]"
          >
            <template #button>
              <van-button size="small" type="primary" @click="sendSmsCode" :disabled="cooldown > 0">
                {{ cooldown > 0 ? `${cooldown}秒后重试` : '获取验证码' }}
              </van-button>
            </template>
          </van-field>
          
          <van-field
            v-model="password"
            type="password"
            name="password"
            label="设置密码"
            placeholder="请设置登录密码"
            :rules="[{ required: true, message: '请设置密码' }]"
          />
          
          <van-field
            v-model="nickname"
            name="nickname"
            label="昵称"
            placeholder="请输入昵称"
            :rules="[{ required: true, message: '请填写昵称' }]"
          />
        </van-cell-group>
        
        <div class="checkbox-container">
          <van-checkbox v-model="agreeTerms" shape="square">
            我已阅读并同意<span class="link-text">《用户协议》</span>和<span class="link-text">《隐私政策》</span>
          </van-checkbox>
        </div>
        
        <div style="margin: 16px;">
          <van-button round block type="primary" native-type="submit" :disabled="!agreeTerms">
            立即注册
          </van-button>
        </div>
      </van-form>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Register',
  data() {
    return {
      phone: '',
      smsCode: '',
      password: '',
      nickname: '',
      agreeTerms: false,
      cooldown: 0
    }
  },
  methods: {
    onSubmit(values) {
      // 模拟注册成功
      console.log('form submitted', values);
      
      // 实际项目中这里会调用API
      localStorage.setItem('token', 'demo-token');
      this.$router.push('/');
    },
    sendSmsCode() {
      // 模拟发送验证码
      this.cooldown = 60;
      const timer = setInterval(() => {
        this.cooldown -= 1;
        if (this.cooldown <= 0) {
          clearInterval(timer);
        }
      }, 1000);
    }
  }
}
</script>

<style scoped>
.register-page {
  background-color: #f7f8fa;
  min-height: 100vh;
}

.content-container {
  padding: 20px 16px;
}

.form-header {
  text-align: center;
  margin-bottom: 30px;
}

.title {
  font-size: 24px;
  font-weight: bold;
  color: #1989fa;
  margin-bottom: 8px;
}

.subtitle {
  font-size: 14px;
  color: #666;
}

.checkbox-container {
  margin: 20px 16px;
}

.link-text {
  color: #1989fa;
}
</style> 
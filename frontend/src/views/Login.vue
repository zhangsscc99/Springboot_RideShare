<template>
  <div class="login-page">
    <div class="header">
      <div class="tabs">
        <div class="tab active">快速登录</div>
        <div class="tab">企业扫码</div>
      </div>
    </div>
    
    <div class="content-container">
      <div class="form-container">
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
              v-model="password"
              type="password"
              name="password"
              label="密码"
              placeholder="请输入密码"
              :rules="[{ required: true, message: '请填写密码' }]"
            />
          </van-cell-group>
          
          <div class="actions-row">
            <van-checkbox v-model="rememberMe">记住我</van-checkbox>
            <span class="forgot-password">忘记密码？</span>
          </div>
          
          <div style="margin: 16px;">
            <van-button round block type="primary" native-type="submit">
              立即登录
            </van-button>
          </div>
        </van-form>
        
        <div class="register-row">
          <span>没有账号？</span>
          <router-link to="/register" class="register-link">立即注册</router-link>
        </div>
        
        <div class="other-login">
          <div class="title-line">
            <span class="title-text">其他登录方式</span>
          </div>
          <div class="login-icons">
            <div class="login-icon">
              <i class="wechat-icon"></i>
              <div>微信</div>
            </div>
            <div class="login-icon">
              <i class="apple-icon"></i>
              <div>Apple</div>
            </div>
          </div>
        </div>
      </div>
    </div>
    
    <div class="agreement-text">
      登录即代表您已同意
      <span class="agreement-link">《用户协议》</span>和
      <span class="agreement-link">《隐私政策》</span>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Login',
  data() {
    return {
      phone: '',
      password: '',
      rememberMe: false
    }
  },
  methods: {
    onSubmit(values) {
      // 模拟登录成功
      console.log('form submitted', values);
      
      // 实际项目中这里会调用API
      localStorage.setItem('token', 'demo-token');
      this.$router.push('/');
    }
  }
}
</script>

<style scoped>
.login-page {
  height: 100vh;
  display: flex;
  flex-direction: column;
}

.header {
  background: #1989fa;
  padding-top: 20px;
  padding-bottom: 30px;
  border-bottom-left-radius: 20px;
  border-bottom-right-radius: 20px;
}

.tabs {
  display: flex;
  justify-content: center;
  background: rgba(255, 255, 255, 0.2);
  border-radius: 20px;
  width: 70%;
  margin: 0 auto;
  padding: 4px;
}

.tab {
  flex: 1;
  text-align: center;
  padding: 8px 0;
  font-size: 14px;
  color: white;
  border-radius: 16px;
}

.tab.active {
  background: white;
  color: #1989fa;
}

.form-container {
  margin-top: 20px;
}

.actions-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 16px;
}

.forgot-password {
  color: #1989fa;
  font-size: 14px;
}

.register-row {
  text-align: center;
  margin-top: 15px;
  font-size: 14px;
  color: #666;
}

.register-link {
  color: #1989fa;
  margin-left: 5px;
}

.other-login {
  margin-top: 40px;
}

.title-line {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.title-line::before,
.title-line::after {
  content: '';
  flex: 1;
  height: 1px;
  background: #ddd;
}

.title-text {
  padding: 0 10px;
  font-size: 14px;
  color: #999;
}

.login-icons {
  display: flex;
  justify-content: center;
  gap: 40px;
}

.login-icon {
  text-align: center;
  font-size: 12px;
  color: #666;
}

.wechat-icon,
.apple-icon {
  display: block;
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-bottom: 8px;
  background-color: #f5f5f5;
}

.agreement-text {
  text-align: center;
  font-size: 12px;
  color: #999;
  padding: 15px;
  margin-top: auto;
}

.agreement-link {
  color: #1989fa;
}
</style> 
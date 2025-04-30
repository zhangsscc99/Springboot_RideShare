<template>
  <div class="profile-page">
    <div class="profile-header">
      <div class="user-info">
        <img :src="user.avatar" alt="用户头像" class="avatar">
        <div class="user-details">
          <div class="username">{{ user.name }}</div>
          <div class="user-id">用户ID: {{ user.id }}</div>
        </div>
      </div>
      
      <div class="wallet-card">
        <div class="wallet-balance">
          <span class="balance-label">账户余额</span>
          <span class="balance-amount">${{ user.balance.toFixed(2) }}</span>
        </div>
        <div class="wallet-actions">
          <van-button size="small" type="primary" plain>充值</van-button>
          <van-button size="small" plain>提现</van-button>
        </div>
      </div>
    </div>
    
    <div class="menu-groups">
      <div class="menu-group">
        <div class="menu-title">出行服务</div>
        <van-cell-group inset>
          <van-cell title="我的行程" is-link to="/trips" />
          <van-cell title="我的车辆" is-link />
          <van-cell title="常用路线" is-link />
        </van-cell-group>
      </div>
      
      <div class="menu-group">
        <div class="menu-title">账户与安全</div>
        <van-cell-group inset>
          <van-cell title="个人资料" is-link />
          <van-cell title="账户安全" is-link />
          <van-cell title="支付管理" is-link />
        </van-cell-group>
      </div>
      
      <div class="menu-group">
        <div class="menu-title">其他服务</div>
        <van-cell-group inset>
          <van-cell title="消息通知" is-link />
          <van-cell title="帮助中心" is-link />
          <van-cell title="关于我们" is-link />
          <van-cell title="设置" is-link />
        </van-cell-group>
      </div>
    </div>
    
    <div class="logout-button">
      <van-button block round @click="handleLogout">退出登录</van-button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Profile',
  data() {
    return {
      user: {
        id: 0,
        name: '',
        avatar: '',
        balance: 0
      }
    }
  },
  created() {
    this.fetchUserInfo()
  },
  methods: {
    fetchUserInfo() {
      // 实际开发中会从store获取或调用API
      this.user = {
        id: 1001,
        name: '张三',
        avatar: 'https://img01.yzcdn.cn/vant/cat.jpeg',
        balance: 128.50
      }
    },
    handleLogout() {
      this.$store.dispatch('logout')
      this.$router.push('/login')
    }
  }
}
</script>

<style scoped>
.profile-page {
  min-height: 100vh;
  background-color: #f7f8fa;
  padding-bottom: 80px;
}

.profile-header {
  background: linear-gradient(135deg, #1989fa, #39bbff);
  padding: 20px 16px 30px;
  color: white;
  border-bottom-left-radius: 20px;
  border-bottom-right-radius: 20px;
}

.user-info {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.avatar {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  border: 2px solid rgba(255, 255, 255, 0.8);
}

.user-details {
  margin-left: 16px;
}

.username {
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 4px;
}

.user-id {
  font-size: 12px;
  opacity: 0.8;
}

.wallet-card {
  background: rgba(255, 255, 255, 0.1);
  border-radius: 10px;
  padding: 12px 16px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.balance-label {
  display: block;
  font-size: 12px;
  opacity: 0.8;
  margin-bottom: 4px;
}

.balance-amount {
  font-size: 20px;
  font-weight: bold;
}

.wallet-actions {
  display: flex;
  gap: 8px;
}

.menu-groups {
  padding: 16px;
}

.menu-group {
  margin-bottom: 24px;
}

.menu-title {
  font-size: 14px;
  color: #666;
  margin-bottom: 8px;
  margin-left: 12px;
}

.logout-button {
  padding: 16px;
  margin-top: 20px;
}
</style> 
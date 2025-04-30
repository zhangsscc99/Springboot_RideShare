<template>
  <div id="app">
    <router-view />
    
    <!-- 底部导航栏 -->
    <van-tabbar v-model="active" route fixed v-if="showTabbar">
      <van-tabbar-item to="/" icon="home-o">首页</van-tabbar-item>
      <van-tabbar-item to="/publish" icon="plus">发布</van-tabbar-item>
      <van-tabbar-item to="/trips" icon="fire-o">行程</van-tabbar-item>
      <van-tabbar-item to="/profile" icon="user-o">我的</van-tabbar-item>
    </van-tabbar>
  </div>
</template>

<script>
export default {
  name: 'App',
  data() {
    return {
      active: 0
    }
  },
  computed: {
    showTabbar() {
      // 登录和注册页面不显示底部导航
      return !['Login', 'Register'].includes(this.$route.name)
    }
  }
}
</script>

<style>
#app {
  font-family: 'PingFang SC', 'Helvetica Neue', Helvetica, 'Microsoft YaHei', sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  min-height: 100vh;
  background-color: #f7f8fa;
}

body {
  margin: 0;
  padding: 0;
}

.page-container {
  padding-bottom: 50px;
}

.content-container {
  padding: 16px;
}

.card {
  background: #fff;
  border-radius: 8px;
  padding: 16px;
  margin-bottom: 12px;
  box-shadow: 0 2px 12px rgba(100, 101, 102, 0.08);
}

.primary-color {
  color: #1989fa;
}

.bg-primary {
  background-color: #1989fa;
}
</style> 
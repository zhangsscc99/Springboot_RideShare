<template>
  <div class="home-page">
    <!-- 顶部轮播通知 -->
    <div class="banner">
      <div class="banner-text">校园拼车，安全便捷，香槟到芝加哥首选出行方式！</div>
    </div>
    
    <!-- 将下载按钮移到这里 - 页面最上方 -->
    <div class="download-section top-position">
      <van-button class="download-button" @click="handleDownload">
        <div class="download-content">
          <van-icon name="down" class="download-icon" />
          <div class="download-text">马上启程 - 下载客户端</div>
        </div>
      </van-button>
    </div>
    
    <!-- 主要内容 -->
    <div class="content-container">
      <!-- 主标语 -->
      <div class="headline">
        <h1>校园省钱<br/>出行省时</h1>
        <p>校园拼车就用拼途，超值！</p>
        <van-button type="primary" size="large" class="action-button" @click="handleStartNow">
          免费试用
        </van-button>
      </div>
      
      <!-- 使用本地图片资源替换在线图片 -->
      <div class="scene-image">
        <img src="@/assets/images/pintu_blue.png" alt="拼车场景" />
      </div>
      
      <!-- 选项卡 -->
      <div class="mode-tabs">
        <div :class="['tab', activeMode === 'carpool' ? 'active' : '']" @click="activeMode = 'carpool'">顺路拼车</div>
        <div :class="['tab', activeMode === 'rideshare' ? 'active' : '']" @click="activeMode = 'rideshare'">专车直达</div>
      </div>
      
      <!-- 热门路线 -->
      <div class="hot-routes">
        <h3>热门路线</h3>
        <div class="route-cards">
          <div class="route-card" v-for="(route, index) in hotRoutes" :key="index" @click="selectRoute(route)">
            <div class="route-info">
              <div class="route-name">{{ route.name }}</div>
              <div class="route-cities">{{ route.from }} → {{ route.to }}</div>
              <div class="route-price">{{ route.price }}</div>
            </div>
            <van-icon name="arrow" class="route-arrow" />
          </div>
        </div>
      </div>
      
      <!-- 功能区域 - 前三个功能并列 -->
      <div class="feature-section">
        <div class="feature-item" v-for="(feature, index) in features" :key="index">
          <van-icon :name="feature.icon" size="40" class="feature-icon" color="#1989fa" />
          <div class="feature-title">{{ feature.title }}</div>
          <div class="feature-desc">{{ feature.desc }}</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Home',
  data() {
    return {
      activeMode: 'carpool',
      hotRoutes: [
        { 
          name: '校园到机场',
          from: '香槟 UIUC', 
          to: '芝加哥 ORD机场', 
          price: '￥25/人',
          seats: 3
        },
        { 
          name: '周末购物',
          from: '香槟 校区', 
          to: '芝加哥 市中心', 
          price: '￥30/人',
          seats: 4
        },
        { 
          name: '华埠美食',
          from: '香槟 UIUC', 
          to: '芝加哥 唐人街', 
          price: '￥28/人',
          seats: 2
        }
      ],
      features: [
        {
          icon: 'gift-o',
          title: '拼车特惠',
          desc: '车费分摊更划算'
        },
        {
          icon: 'coupon-o',
          title: '限时特惠',
          desc: '首单立减$5'
        },
        {
          icon: 'balance-o',
          title: '商旅省钱',
          desc: '企业用户专享'
        }
      ]
    }
  },
  methods: {
    handleStartNow() {
      if (this.$store.getters.isLoggedIn) {
        this.$router.push('/publish')
      } else {
        this.$router.push('/login')
      }
    },
    selectRoute(route) {
      this.$store.commit('setSelectedRoute', route)
      this.$router.push('/publish')
    },
    handleDownload() {
      alert('正在准备下载客户端...')
    }
  }
}
</script>

<style scoped>
/* 为主容器添加一些基础样式 */
.home-page {
  padding-bottom: 50px;
  background-color: #f8f8f8; /* 添加浅灰背景色增强层次感 */
}

/* 调整内容容器，添加标准边距 */
.content-container {
  width: 100%;
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 30px; /* 添加基础边距 */
  box-sizing: border-box;
}

/* 修改图片容器样式，添加圆角 */
.scene-image {
  width: calc(100% + 40px); /* 扩展宽度以适应左右边距 */
  margin-left: -20px; /* 负边距匹配内容容器的padding */
  margin-right: -20px;
  margin-bottom: 20px;
  min-height: 220px;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: white;
  overflow: hidden;
  border-radius: 12px; /* 添加圆角 */
}

/* 为图片本身也添加圆角，确保完整效果 */
.scene-image img {
  width: 100%;
  height: auto;
  max-height: 300px;
  object-fit: contain;
  padding: 0;
  border-radius: 8px; /* 图片本身也添加圆角 */
}

/* 将其他内容区域的左右内边距调整为统一值 */
.headline, .mode-tabs, .hot-routes, .feature-section {
  padding-left: 4px;
  padding-right: 4px;
}

/* 调整下载按钮区域的边距 */
.download-section.top-position {
  padding: 0 12px;
  margin-bottom: 15px;
}

/* 调整响应式设置 */
@media (min-width: 768px) {
  .content-container {
    padding: 0 24px; /* 大屏幕上增加边距 */
  }
  
  .scene-image {
    width: calc(100% + 48px);
    margin-left: -24px;
    margin-right: -24px;
    border-radius: 16px; /* 大屏幕上增加圆角 */
  }
  
  .headline, .mode-tabs, .hot-routes, .feature-section {
    padding-left: 8px;
    padding-right: 8px;
  }
}

/* 特大屏幕调整 */
@media (min-width: 1240px) {
  .scene-image {
    width: 100%;
    margin-left: 0;
    margin-right: 0;
    border-radius: 20px; /* 特大屏幕使用更大的圆角 */
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
  }
}

.banner {
  background-color: #fff1e6;
  padding: 10px 16px;
  font-size: 14px;
  color: #e76e3c;
  text-align: center;
}

.headline {
  padding: 20px 0;
  text-align: center;
}

.headline h1 {
  font-size: 26px;
  font-weight: bold;
  margin-bottom: 8px;
  color: #333;
}

.headline p {
  font-size: 16px;
  color: #666;
  margin-bottom: 16px;
}

.scene-image img {
  width: 100%;
  height: auto;
  max-height: 300px;
  object-fit: contain;
  padding: 0;
  border-radius: 8px; /* 图片本身也添加圆角 */
}

.action-button {
  width: 80%;
  height: 48px; /* 与下载按钮一致的高度 */
  border-radius: 24px !important; /* 添加圆角 */
  background-color: #1989fa !important; /* 明确设置背景色 */
  border: none !important; /* 移除边框 */
  box-shadow: 0 6px 12px rgba(25, 137, 250, 0.25); /* 添加阴影效果 */
  transition: all 0.3s ease; /* 平滑过渡效果 */
  font-size: 16px; /* 调整字体大小 */
  font-weight: 500; /* 稍微加粗字体 */
  letter-spacing: 0.5px; /* 调整字符间距 */
}

.action-button:active {
  transform: translateY(2px);
  box-shadow: 0 3px 6px rgba(25, 137, 250, 0.2);
}

.mode-tabs {
  display: flex;
  margin-bottom: 20px;
  background: white;
  border-radius: 8px;
  overflow: hidden;
}

.tab {
  flex: 1;
  text-align: center;
  padding: 12px 0;
  font-size: 15px;
  color: #666;
  transition: all 0.3s;
}

.tab.active {
  color: #1989fa;
  font-weight: bold;
  position: relative;
}

.tab.active::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 50%;
  transform: translateX(-50%);
  width: 20px;
  height: 3px;
  background-color: #1989fa;
  border-radius: 3px;
}

.hot-routes {
  margin-bottom: 20px;
}

.hot-routes h3 {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 12px;
}

.route-cards {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.route-card {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: white;
  padding: 12px 16px;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.route-name {
  font-weight: bold;
  margin-bottom: 4px;
}

.route-cities {
  color: #666;
  font-size: 14px;
  margin-bottom: 4px;
}

.route-price {
  color: #f56c6c;
  font-size: 13px;
}

.route-arrow {
  color: #999;
}

.feature-section {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 15px;
  margin-top: 20px;
}

.feature-item {
  background: white;
  border-radius: 8px;
  padding: 15px;
  text-align: center;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.feature-icon {
  width: 40px;
  height: 40px;
  margin-bottom: 8px;
}

.feature-title {
  font-weight: bold;
  margin-bottom: 4px;
  font-size: 14px;
}

.feature-desc {
  color: #999;
  font-size: 12px;
}

.download-button {
  width: 100%;
  max-width: 350px;
  height: 48px;
  border-radius: 24px !important;
  background-color: #1890ff !important;
  border: none !important;
  box-shadow: 0 6px 12px rgba(24, 144, 255, 0.25);
  overflow: hidden;
  transition: all 0.3s ease;
}

.download-button:active {
  transform: translateY(2px);
  box-shadow: 0 3px 6px rgba(24, 144, 255, 0.2);
}

.download-content {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
}

.download-icon {
  font-size: 20px;
  margin-right: 8px;
  color: white;
}

.download-text {
  color: white;
  font-size: 16px;
  font-weight: 500;
  letter-spacing: 0.5px;
  font-family: 'PingFang SC', 'Microsoft YaHei', -apple-system, BlinkMacSystemFont, sans-serif;
}

@media (max-width: 480px) {
  .download-button {
    max-width: 100%;
  }
}
</style> 
<template>
  <div class="home-page">
    <!-- 顶部轮播通知 -->
    <div class="banner">
      <div class="banner-text">校园拼车，安全便捷，香槟到芝加哥首选出行方式！</div>
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
      
      <!-- 使用在线图片资源 -->
      <div class="scene-image">
        <img src="https://img.freepik.com/free-vector/car-sharing-concept-illustration_114360-7511.jpg" alt="拼车场景" />
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
      
      <!-- 功能区域 - 使用图标代替图片 -->
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
        },
        {
          icon: 'down',
          title: '马上启程',
          desc: '下载客户端'
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
    }
  }
}
</script>

<style scoped>
.home-page {
  padding-bottom: 50px;
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

.scene-image {
  width: 100%;
  height: 200px;
  margin-bottom: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #f0f9ff;
  border-radius: 8px;
  overflow: hidden;
}

.scene-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.action-button {
  width: 80%;
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
  grid-template-columns: repeat(2, 1fr);
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
</style> 
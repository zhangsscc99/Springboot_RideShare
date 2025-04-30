<template>
  <div class="trips-page">
    <van-nav-bar title="我的行程" />
    
    <div class="content-container">
      <!-- 行程分类选项卡 -->
      <van-tabs v-model:active="activeTab" sticky>
        <van-tab title="全部行程">
          <div class="empty-state" v-if="allTrips.length === 0">
            <van-icon name="todo-list-o" size="48" color="#ddd" />
            <p class="empty-text">您还没有行程记录</p>
            <van-button type="primary" size="small" round @click="$router.push('/publish')">发布行程</van-button>
          </div>
          
          <trip-card 
            v-for="trip in allTrips" 
            :key="trip.id" 
            :trip="trip" 
            @view="viewTrip"
          />
        </van-tab>
        
        <van-tab title="我发布的">
          <div class="empty-state" v-if="publishedTrips.length === 0">
            <van-icon name="add-o" size="48" color="#ddd" />
            <p class="empty-text">您还没有发布行程</p>
            <van-button type="primary" size="small" round @click="$router.push('/publish')">去发布</van-button>
          </div>
          
          <trip-card 
            v-for="trip in publishedTrips" 
            :key="trip.id" 
            :trip="trip" 
            @view="viewTrip"
          />
        </van-tab>
        
        <van-tab title="我参与的">
          <div class="empty-state" v-if="joinedTrips.length === 0">
            <van-icon name="friends-o" size="48" color="#ddd" />
            <p class="empty-text">您还没有参与行程</p>
            <van-button type="primary" size="small" round @click="$router.push('/')">浏览行程</van-button>
          </div>
          
          <trip-card 
            v-for="trip in joinedTrips" 
            :key="trip.id" 
            :trip="trip" 
            @view="viewTrip"
          />
        </van-tab>
      </van-tabs>
    </div>
    
    <!-- 行程详情弹窗 -->
    <van-popup
      v-model:show="showTripDetail"
      position="bottom"
      round
      closeable
      :style="{ height: '70%' }"
    >
      <div class="trip-detail" v-if="selectedTrip">
        <div class="detail-header">
          <h3>行程详情</h3>
        </div>
        
        <div class="detail-content">
          <div class="route-info">
            <div class="location from">
              <div class="dot from"></div>
              <div class="location-text">{{ selectedTrip.from }}</div>
            </div>
            <div class="route-line"></div>
            <div class="location to">
              <div class="dot to"></div>
              <div class="location-text">{{ selectedTrip.to }}</div>
            </div>
          </div>
          
          <div class="info-section">
            <div class="info-item">
              <van-icon name="clock-o" />
              <span>{{ selectedTrip.date }} {{ selectedTrip.time }}</span>
            </div>
            <div class="info-item">
              <van-icon name="friends-o" />
              <span>{{ selectedTrip.seats }}个座位</span>
            </div>
            <div class="info-item">
              <van-icon name="gold-coin-o" />
              <span>￥{{ selectedTrip.price }}/人</span>
            </div>
          </div>
          
          <div class="people-section" v-if="selectedTrip.role === 'publisher'">
            <h4>乘客信息</h4>
            <div class="passenger-list" v-if="selectedTrip.passengers && selectedTrip.passengers.length">
              <div class="passenger-item" v-for="passenger in selectedTrip.passengers" :key="passenger.id">
                <img :src="passenger.avatar" :alt="passenger.name" class="passenger-avatar">
                <span class="passenger-name">{{ passenger.name }}</span>
              </div>
            </div>
            <div class="empty-passengers" v-else>
              <p>暂无乘客加入</p>
            </div>
          </div>
          
          <div class="people-section" v-else>
            <h4>车主信息</h4>
            <div class="publisher-info">
              <img :src="selectedTrip.publisher.avatar" :alt="selectedTrip.publisher.name" class="publisher-avatar">
              <span class="publisher-name">{{ selectedTrip.publisher.name }}</span>
            </div>
          </div>
          
          <div class="action-buttons">
            <van-button v-if="selectedTrip.status === 'upcoming'" type="danger" block @click="cancelTrip">取消行程</van-button>
            <van-button v-else-if="selectedTrip.status === 'completed'" type="primary" block @click="rateTrip">评价行程</van-button>
          </div>
        </div>
      </div>
    </van-popup>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

// 行程卡片组件
const TripCard = {
  props: {
    trip: {
      type: Object,
      required: true
    }
  },
  computed: {
    statusText() {
      const statusMap = {
        'upcoming': '即将出发',
        'ongoing': '进行中',
        'completed': '已完成',
        'cancelled': '已取消'
      }
      return statusMap[this.trip.status] || this.trip.status
    },
    statusClass() {
      return `status-${this.trip.status}`
    }
  },
  methods: {
    viewTripDetail() {
      this.$emit('view', this.trip)
    }
  },
  template: `
    <div class="trip-card" @click="viewTripDetail">
      <div class="trip-status">
        <span :class="['status-tag', statusClass]">{{ statusText }}</span>
        <span class="role-tag">{{ trip.role === 'publisher' ? '我是车主' : '我是乘客' }}</span>
      </div>
      
      <div class="trip-route">
        <div class="location from">
          <div class="dot from"></div>
          <div class="location-text">{{ trip.from }}</div>
        </div>
        <div class="route-line"></div>
        <div class="location to">
          <div class="dot to"></div>
          <div class="location-text">{{ trip.to }}</div>
        </div>
      </div>
      
      <div class="trip-info">
        <div class="info-item">
          <van-icon name="clock-o" />
          <span>{{ trip.date }} {{ trip.time }}</span>
        </div>
        <div class="info-item">
          <van-icon name="friends-o" />
          <span>{{ trip.seats }}个座位</span>
        </div>
        <div class="info-item">
          <van-icon name="gold-coin-o" />
          <span>￥{{ trip.price }}/人</span>
        </div>
      </div>
      
      <div class="trip-people">
        <template v-if="trip.role === 'publisher'">
          <div class="people-label">乘客：</div>
          <div class="people-avatars" v-if="trip.passengers && trip.passengers.length">
            <img 
              v-for="passenger in trip.passengers" 
              :key="passenger.id" 
              :src="passenger.avatar" 
              :alt="passenger.name" 
              class="avatar"
            >
          </div>
          <div class="empty-people" v-else>暂无乘客</div>
        </template>
        <template v-else>
          <div class="people-label">车主：</div>
          <div class="people-avatars">
            <img 
              :src="trip.publisher.avatar" 
              :alt="trip.publisher.name" 
              class="avatar"
            >
            <span class="name">{{ trip.publisher.name }}</span>
          </div>
        </template>
      </div>
    </div>
  `
}

export default {
  name: 'Trips',
  components: {
    TripCard
  },
  data() {
    return {
      activeTab: 0,
      showTripDetail: false,
      selectedTrip: null
    }
  },
  computed: {
    ...mapGetters(['publishedTrips', 'joinedTrips']),
    allTrips() {
      return this.$store.state.trips
    }
  },
  created() {
    this.$store.dispatch('fetchTrips')
  },
  methods: {
    viewTrip(trip) {
      this.selectedTrip = trip
      this.showTripDetail = true
    },
    cancelTrip() {
      // 模拟取消行程
      alert('行程已取消')
      this.showTripDetail = false
    },
    rateTrip() {
      // 模拟评价行程
      alert('感谢您的评价')
      this.showTripDetail = false
    }
  }
}
</script>

<style scoped>
.trips-page {
  min-height: 100vh;
  background-color: #f7f8fa;
  padding-bottom: 60px;
}

.content-container {
  padding-bottom: 16px;
}

.empty-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 40px 0;
}

.empty-image {
  width: 120px;
  height: 120px;
  margin-bottom: 16px;
}

.empty-text {
  color: #999;
  margin-bottom: 16px;
}

/* Trip Card Styles */
.trip-card {
  background: white;
  border-radius: 8px;
  margin: 12px;
  padding: 16px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.trip-status {
  display: flex;
  justify-content: space-between;
  margin-bottom: 12px;
}

.status-tag {
  padding: 2px 8px;
  border-radius: 10px;
  font-size: 12px;
}

.status-upcoming {
  background-color: #e6f7ff;
  color: #1989fa;
}

.status-ongoing {
  background-color: #e6fff7;
  color: #07c160;
}

.status-completed {
  background-color: #f5f5f5;
  color: #666;
}

.status-cancelled {
  background-color: #ffece6;
  color: #f56c6c;
}

.role-tag {
  font-size: 12px;
  color: #999;
}

.trip-route {
  margin-bottom: 16px;
}

.location {
  display: flex;
  align-items: center;
  margin-bottom: 8px;
}

.dot {
  width: 10px;
  height: 10px;
  border-radius: 50%;
  margin-right: 8px;
}

.dot.from {
  background-color: #1989fa;
}

.dot.to {
  background-color: #f56c6c;
}

.route-line {
  width: 2px;
  height: 20px;
  background-color: #ddd;
  margin-left: 4px;
  margin-bottom: 8px;
}

.location-text {
  font-size: 14px;
}

.trip-info {
  display: flex;
  justify-content: space-between;
  margin-bottom: 16px;
  padding-bottom: 16px;
  border-bottom: 1px solid #f5f5f5;
}

.info-item {
  display: flex;
  align-items: center;
  color: #666;
  font-size: 13px;
}

.info-item .van-icon {
  margin-right: 4px;
}

.trip-people {
  display: flex;
  align-items: center;
}

.people-label {
  font-size: 13px;
  color: #666;
  margin-right: 8px;
}

.people-avatars {
  display: flex;
  align-items: center;
}

.avatar {
  width: 24px;
  height: 24px;
  border-radius: 50%;
  margin-right: 8px;
}

.name {
  font-size: 13px;
}

.empty-people {
  font-size: 13px;
  color: #999;
}

/* Trip Detail Styles */
.trip-detail {
  padding: 20px;
}

.detail-header {
  text-align: center;
  margin-bottom: 20px;
}

.detail-header h3 {
  font-size: 18px;
  font-weight: bold;
  margin: 0;
}

.detail-content {
  padding: 0 10px;
}

.route-info {
  margin-bottom: 24px;
}

.info-section {
  display: flex;
  justify-content: space-between;
  margin-bottom: 24px;
  padding-bottom: 16px;
  border-bottom: 1px solid #f5f5f5;
}

.people-section {
  margin-bottom: 24px;
}

.people-section h4 {
  font-size: 16px;
  margin-bottom: 12px;
}

.passenger-list, .publisher-info {
  display: flex;
  flex-wrap: wrap;
}

.passenger-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-right: 16px;
  margin-bottom: 12px;
}

.passenger-avatar, .publisher-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-bottom: 4px;
}

.passenger-name, .publisher-name {
  font-size: 12px;
}

.empty-passengers {
  color: #999;
  text-align: center;
  padding: 20px 0;
}

.action-buttons {
  margin-top: 20px;
}
</style> 
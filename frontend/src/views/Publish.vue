<template>
  <div class="publish-page">
    <van-nav-bar
      title="发布行程"
      left-arrow
      @click-left="$router.go(-1)"
    />
    
    <div class="content-container">
      <!-- 模式选择 -->
      <div class="mode-select">
        <div :class="['mode-button', tripMode === 'carpool' ? 'active' : '']" @click="tripMode = 'carpool'">
          <span class="mode-name">顺路拼车</span>
          <span class="mode-desc">车费分摊，互助出行</span>
        </div>
        <div :class="['mode-button', tripMode === 'rideshare' ? 'active' : '']" @click="tripMode = 'rideshare'">
          <span class="mode-name">专车直达</span>
          <span class="mode-desc">司机接单，高效直达</span>
        </div>
      </div>
      
      <!-- 行程表单 -->
      <van-form @submit="onSubmit">
        <div class="form-card">
          <div class="location-inputs">
            <div class="location-row">
              <van-icon name="location-o" color="#1989fa" />
              <van-field
                v-model="formData.fromLocation"
                placeholder="出发地（如：UIUC校区）"
                :border="false"
              />
            </div>
            <div class="divider"></div>
            <div class="location-row">
              <van-icon name="location" color="#f56c6c" />
              <van-field
                v-model="formData.toLocation"
                placeholder="目的地（如：芝加哥机场）"
                :border="false"
              />
            </div>
          </div>
        </div>
        
        <div class="form-card">
          <van-cell title="出发日期" is-link @click="showDatePicker = true">
            <template #value>
              <span :class="{ 'placeholder-text': !formData.date }">{{ formData.date || '请选择日期' }}</span>
            </template>
          </van-cell>
          
          <van-cell title="出发时间" is-link @click="showTimePicker = true">
            <template #value>
              <span :class="{ 'placeholder-text': !formData.time }">{{ formData.time || '请选择时间' }}</span>
            </template>
          </van-cell>
          
          <van-cell title="座位数" is-link @click="showSeatsPicker = true">
            <template #value>
              <span :class="{ 'placeholder-text': !formData.seats }">{{ formData.seats ? `${formData.seats}个座位` : '请选择座位数' }}</span>
            </template>
          </van-cell>
          
          <template v-if="tripMode === 'carpool'">
            <van-cell title="每人价格" is-link @click="showPricePicker = true">
              <template #value>
                <span :class="{ 'placeholder-text': !formData.price }">{{ formData.price ? `$${formData.price}` : '请设置价格' }}</span>
              </template>
            </van-cell>
          </template>
          
          <template v-else>
            <van-cell title="车型">
              <template #input>
                <van-field v-model="formData.carModel" placeholder="请输入车型" :border="false" />
              </template>
            </van-cell>
            <van-cell title="总价">
              <template #input>
                <div class="price-input">
                  <span class="dollar-sign">$</span>
                  <van-field v-model="formData.totalPrice" type="number" placeholder="请输入总价" :border="false" />
                </div>
              </template>
            </van-cell>
          </template>
        </div>
        
        <div class="form-card">
          <van-field
            v-model="formData.notes"
            rows="3"
            autosize
            label="备注信息"
            type="textarea"
            maxlength="100"
            placeholder="添加行程备注信息（选填）"
            show-word-limit
          />
        </div>
        
        <div style="margin: 24px 16px;">
          <van-button round block type="primary" native-type="submit" size="large">
            {{ tripMode === 'carpool' ? '发布拼车信息' : '发布接单信息' }}
          </van-button>
        </div>
      </van-form>
      
      <!-- 日期选择器 -->
      <van-popup v-model:show="showDatePicker" position="bottom">
        <van-datetime-picker
          type="date"
          title="选择出发日期"
          :min-date="new Date()"
          :max-date="new Date(new Date().setMonth(new Date().getMonth() + 3))"
          @confirm="onConfirmDate"
          @cancel="showDatePicker = false"
        />
      </van-popup>
      
      <!-- 时间选择器 -->
      <van-popup v-model:show="showTimePicker" position="bottom">
        <van-datetime-picker
          type="time"
          title="选择出发时间"
          :min-hour="0"
          :max-hour="23"
          @confirm="onConfirmTime"
          @cancel="showTimePicker = false"
        />
      </van-popup>
      
      <!-- 座位数选择器 -->
      <van-popup v-model:show="showSeatsPicker" position="bottom">
        <van-picker
          title="选择座位数"
          :columns="[1, 2, 3, 4, 5, 6, 7]"
          @confirm="onConfirmSeats"
          @cancel="showSeatsPicker = false"
        />
      </van-popup>
      
      <!-- 价格选择器 -->
      <van-popup v-model:show="showPricePicker" position="bottom">
        <van-picker
          title="选择每人价格"
          :columns="[10, 15, 20, 25, 30, 35, 40, 45, 50].map(price => `$${price}`)"
          @confirm="onConfirmPrice"
          @cancel="showPricePicker = false"
        />
      </van-popup>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Publish',
  data() {
    return {
      tripMode: 'carpool',
      formData: {
        fromLocation: '',
        toLocation: '',
        date: '',
        time: '',
        seats: '',
        price: '',
        carModel: '',
        totalPrice: '',
        notes: ''
      },
      showDatePicker: false,
      showTimePicker: false,
      showSeatsPicker: false,
      showPricePicker: false
    }
  },
  mounted() {
    // 如果从首页选择了路线，自动填充
    const selectedRoute = this.$store.state.selectedRoute;
    if (selectedRoute) {
      this.formData.fromLocation = selectedRoute.from;
      this.formData.toLocation = selectedRoute.to;
    }
  },
  methods: {
    onSubmit(values) {
      console.log('trip published', this.formData);
      
      // 模拟API调用
      setTimeout(() => {
        this.$router.push('/trips');
      }, 1000);
    },
    onConfirmDate(date) {
      this.formData.date = `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}-${String(date.getDate()).padStart(2, '0')}`;
      this.showDatePicker = false;
    },
    onConfirmTime(time) {
      this.formData.time = time;
      this.showTimePicker = false;
    },
    onConfirmSeats(seats) {
      this.formData.seats = seats;
      this.showSeatsPicker = false;
    },
    onConfirmPrice(price) {
      this.formData.price = price.replace('$', '');
      this.showPricePicker = false;
    }
  }
}
</script>

<style scoped>
.publish-page {
  background-color: #f7f8fa;
  min-height: 100vh;
  padding-bottom: 20px;
}

.content-container {
  padding: 16px;
}

.mode-select {
  display: flex;
  margin-bottom: 20px;
  background: white;
  border-radius: 8px;
  overflow: hidden;
}

.mode-button {
  flex: 1;
  text-align: center;
  padding: 15px 0;
  cursor: pointer;
  display: flex;
  flex-direction: column;
}

.mode-button.active {
  background-color: #e6f7ff;
  color: #1989fa;
  position: relative;
}

.mode-button.active::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 25%;
  width: 50%;
  height: 3px;
  background-color: #1989fa;
  border-radius: 3px;
}

.mode-name {
  font-weight: bold;
  margin-bottom: 4px;
}

.mode-desc {
  font-size: 12px;
  color: #999;
}

.form-card {
  background: white;
  border-radius: 8px;
  margin-bottom: 12px;
  overflow: hidden;
}

.location-inputs {
  padding: 5px 0;
}

.location-row {
  display: flex;
  align-items: center;
  padding: 10px 16px;
}

.location-row .van-icon {
  margin-right: 10px;
}

.divider {
  height: 1px;
  background-color: #f5f5f5;
  margin-left: 40px;
}

.placeholder-text {
  color: #999;
}

.price-input {
  display: flex;
  align-items: center;
}

.dollar-sign {
  margin-right: 5px;
  color: #666;
}
</style> 
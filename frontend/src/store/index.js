import { createStore } from 'vuex'

export default createStore({
  state: {
    user: null,
    selectedRoute: null,
    trips: []
  },
  getters: {
    isLoggedIn(state) {
      return !!state.user
    },
    publishedTrips(state) {
      return state.trips.filter(trip => trip.role === 'publisher')
    },
    joinedTrips(state) {
      return state.trips.filter(trip => trip.role === 'passenger')
    }
  },
  mutations: {
    setUser(state, user) {
      state.user = user
    },
    setSelectedRoute(state, route) {
      state.selectedRoute = route
    },
    clearSelectedRoute(state) {
      state.selectedRoute = null
    },
    setTrips(state, trips) {
      state.trips = trips
    },
    addTrip(state, trip) {
      state.trips.push(trip)
    }
  },
  actions: {
    async fetchUserInfo({ commit }) {
      // 模拟API请求
      const user = {
        id: 1,
        name: '张三',
        phone: '176****5678',
        avatar: 'https://img01.yzcdn.cn/vant/cat.jpeg',
        balance: 128.5
      }
      commit('setUser', user)
    },
    async fetchTrips({ commit }) {
      // 模拟API请求
      const trips = [
        {
          id: 1,
          from: '香槟 UIUC',
          to: '芝加哥 ORD机场',
          date: '2023-10-15',
          time: '14:30',
          seats: 3,
          price: 25,
          status: 'upcoming',
          role: 'publisher',
          passengers: [
            { id: 2, name: '李四', avatar: 'https://img01.yzcdn.cn/vant/cat.jpeg' },
            { id: 3, name: '王五', avatar: 'https://img01.yzcdn.cn/vant/cat.jpeg' }
          ]
        },
        {
          id: 2,
          from: '芝加哥 华埠',
          to: '香槟 UIUC',
          date: '2023-10-18',
          time: '10:00',
          seats: 1,
          price: 30,
          status: 'upcoming',
          role: 'passenger',
          publisher: { id: 4, name: '赵六', avatar: 'https://img01.yzcdn.cn/vant/cat.jpeg' }
        },
        {
          id: 3,
          from: '香槟 UIUC',
          to: '芝加哥 市中心',
          date: '2023-09-25',
          time: '09:00',
          seats: 2,
          price: 20,
          status: 'completed',
          role: 'publisher',
          passengers: [
            { id: 5, name: '孙七', avatar: 'https://img01.yzcdn.cn/vant/cat.jpeg' }
          ]
        }
      ]
      commit('setTrips', trips)
    },
    logout({ commit }) {
      try {
        localStorage.removeItem('token');
      } catch (e) {
        console.error('无法访问localStorage:', e);
      }
      commit('setUser', null);
    },
    publishTrip({ commit }, tripData) {
      // 模拟API请求
      const newTrip = {
        id: Date.now(),
        ...tripData,
        status: 'upcoming',
        role: 'publisher',
        passengers: []
      }
      commit('addTrip', newTrip)
      return Promise.resolve(newTrip)
    }
  }
}) 
import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import { 
  Button, Tabbar, TabbarItem, Icon, Cell, CellGroup, Field, 
  Form, NavBar, Tag, Popup, Picker, DatetimePicker
} from 'vant'
import 'vant/lib/index.css'

const app = createApp(App)

// 注册 Vant 组件
;[
  Button, Tabbar, TabbarItem, Icon, Cell, CellGroup, 
  Field, Form, NavBar, Tag, Popup, Picker, DatetimePicker
].forEach(component => {
  app.use(component)
})

app.use(store).use(router).mount('#app') 
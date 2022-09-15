// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import qs from 'qs'
import axios from 'axios'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import router from './router'
import './utils/funcutil'

const coreHttp = axios.create({
  timeout: 10000 * 12,
  baseURL: 'https://push.dayutec.cn', // bitmail消息通知服务接口
})

Vue.config.productionTip = false
Vue.prototype.$http = axios
Vue.prototype.$coreHttp = coreHttp
Vue.prototype.$qs = qs
Vue.prototype.$json = JSON
Vue.use(ElementUI)

axios.defaults.baseURL = 'https://aaa.bbb.com' //  TODO 换成自己的服务端访问域名 或 ip:port


axios.interceptors.request.use(
  function (config) {
    config.headers.token = localStorage.getItem('token')
    
    config.headers["CONTENT-TYPE"] = "application/json;charset=UTF-8"

    return config
  },
  function (error) {
    return Promise.reject(error)
  }
)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})


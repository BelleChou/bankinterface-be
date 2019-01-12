// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import Element from 'element-ui' 
import 'element-ui/lib/theme-chalk/index.css' 
import axios from "axios"
import util from '@/assets/js/util.js'

Vue.use(Element)

Vue.config.productionTip = false
Vue.prototype.$axios = axios;

axios.defaults.baseURL='http://127.0.0.1:8088/'
axios.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8';
axios.defaults.timeout =  6000;



//自定义过滤器过滤时间    作用：将1415674835697的时间戳转为 yyyy/MM/dd hh:mm:ss 格式时间
Vue.filter("timeForm",function(time){
	time =(typeof time=='string')?parseInt(time) : time
	let date=new Date(time)
	return util.formatDate(date,'yyyy/MM/dd hh:mm:ss');
});



/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})

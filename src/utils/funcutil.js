import { Message } from "element-ui";
import Vue from "vue";

Vue.prototype.isMobile = function() {
  var userAgentInfo = navigator.userAgent;
  var Agents = new Array(
    "Android",
    "iPhone",
    "SymbianOS",
    "Windows Phone",
    "iPad",
    "iPod"
  );
  var flag = false;
  for (var v = 0; v < Agents.length; v++) {
    if (userAgentInfo.indexOf(Agents[v]) > 0) {
      flag = true;
      break;
    }
  }
  return flag;
};

Vue.prototype.isLogin = function(res) {
  const code = res.data.code || 0;
  if (code === 100 || code === 200) {
    Message({
      message: res.data.msg,
      type: "error"
    });
    this.$router.push({ name: "Login" });
    this.logout()
  }
};


Vue.prototype.logout = function() {
  let userData = {
    token: localStorage.getItem('token'),
  }
  this.$http.post('/admin/logout', this.$json.stringify(userData)).then(res => {
    let rsp = res.data
    if (rsp.code == 0) {
      alert("退出登录成功！")
    }
  })
  this.$store.dispatch("CLEAR_USER")
  this.$store.dispatch("CLEAR_ROUTE")
  this.$router.push({ name: "Login" })
  this.$emit('LogOut')
};

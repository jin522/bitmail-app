<template>
  <div class="movecar-page">
    <div class="top-bar">挪车通知</div>
    <div class="func-part">
      <div class="banner">
        <div class="slogin slogin1">临时停靠</div>
        <div class="slogin slogin2">请多关照</div>
        <div class="licence">
          <div class="dot dot1" />
          <div class="dot dot2" />
          <div class="dot dot3" />
          <div class="dot dot4" />
          {{ remindInfo.licenceNum }}
        </div>
      </div>
      <div v-if="!sendMsgSuccess && !hasSendCurrently" class="cause">
        <div class="sub-title">请选择挪车原因 (必填)：</div>
        <div class="choose-boxs">
          <div :class="['each-box', clickDiv == index ? 'each-box-active' : '']" v-for="(item, index) in eventReason"
            :key="item" @click="chooseReason(index)">
            {{ item }}
          </div>
        </div>

        <el-form ref="form" :model="submitPara" label-width="80px">
          <div class="sub-title">联系电话 (非必填)：</div>
          <el-input v-model="submitPara.phoneNum" placeholder="可留下联系电话，让车主回拨。" clearable></el-input>

          <div class="submit-box">
            <button  class="submit-butt" type="button" v-on:click="onSubmit">
              通知车主
            </button>
          </div>
        </el-form>
      </div>
      <div v-if="sendMsgSuccess && !hasSendCurrently" class="cause tips-box">
        <div class="tips">{{ remindInfo.tips }}</div>
      </div>
      <div v-if="hasSendCurrently" class="cause repeatTips-box">
        <div class="tips">
          当前已通知车主挪车，下次通知需间隔10分钟以上，当前剩余:{{
              remindInfo.repeatInterval
          }}秒，请稍后尝试再次联系车主挪车！
        </div>
      </div>
    </div>
    <div class="foot-bar">
      <a href="https://bit.dayutec.cn/" target="_blank">bitmail · 制作我的挪车码</a>
    </div>
  </div>
</template>

<script>
export default {
  name: "ShowMessage",
  data() {
    return {
      carToken: "",
      //最近已经发送过一次
      hasSendCurrently: false,
      //通知发送状态
      sendMsgSuccess: false,
      clickDiv: -1,
      eventReason: [
        "占用别人的停车位",
        "挡住道路，无法通行",
        "车窗或车灯没关",
        "此地禁止停车",
        "车辆碰撞事故",
        "其他紧急原因"
      ],
      submitPara: {
        reason: "",
        phoneNum: ""
      },
      remindInfo: {
        licenceNum: "",
        tips: `挪车通知已发送到车主手机，请耐心等待。本服务仅作为挪车通知，若车主长时间未前来挪车，请寻求其它解决方式。`,
        repeatInterval: 0,
        sutmibTime: "",
        process: "" //"已通知车主前来移车"，"车主已确认收到移车信息并"
      }
    };
  },

  mounted() {
    // TODO 获取url参数中的carToken
    this.carToken = this.$route.query.carToken
    this.getSendRate();
  },
  methods: {
    getSendRate() {
      this.$http
        .get(
          "/car/getInform?" + this.$qs.stringify({ carToken: this.carToken })
        )
        .then(res => {
          let rsp = res.data;
          if (res.status == 200) {
            if (rsp.code == 0) {
              this.hasSendCurrently = false;
            } else if(rsp.code == -1) {//请求fail
              this.hasSendCurrently = true;
            }else{//请求error
              this.$message({
                message: rsp.message,
                type: "warn",
                center: true
              });
            }
            this.remindInfo.repeatInterval = rsp.data.informTime;
            this.remindInfo.licenceNum = rsp.data.licenceNum;
          }
        });
    },
    getLicence() {

    },
    chooseReason(index) {
      if (this.clickDiv == index) {
        this.clickDiv = -1;
        this.submitPara.reason = "";
      } else {
        this.clickDiv = index;
        this.submitPara.reason = this.eventReason[index];
      }
    },
    onSubmit() {
      if (-1 == this.clickDiv) {
        this.$message({
          message: "请选择挪车原因",
          type: "warn",
          center: true
        });
        return;
      }

      this.$http.post("/car/inform?carToken=" + this.carToken,this.$json.stringify(this.submitPara)).then(res => {
        const rsp = res.data;
        if (res.status == 200) {
          if (rsp.code == 0 && rsp.data) {
            this.sendMsgSuccess = true;
          } else {
            this.sendMsgSuccess = false;
            this.$message({
              message: rsp.message,
              type: "warn",
              center: true
            });
          }
        }
      });
    }
  }
};
</script>

<style scoped>
.movecar-page {
  width: 100vw;
  min-height: 100vh;
}

.top-bar {
  height: 50px;
  width: 100vw;
  background-color: #f2f2f2;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 24px;
}

.func-part {
  min-height: calc(100vh - 70px - 50px);
  width: 100vw;
  display: flex;
  flex-direction: column;
  align-items: center;
  /* justify-content: center; */
}

.banner {
  background-color: #5082F5;
  height: 240px;
  width: 100vw;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.slogin {
  font-size: 40px;
  color: white;
  font-weight: 750;
  letter-spacing: 5px;
  font-style: italic;
  width: calc(100% - 100px);
  padding: 5px 50px;
}

.slogin2 {
  display: flex;
  justify-content: flex-end;
}

.licence {
  position: relative;
  margin-top: 20px;
  font-size: 30px;
  color: white;
  font-weight: 750;
  letter-spacing: 5px;
  width: calc(100% - 100px);
  padding: 10px 0;
  text-align: center;
  border: 2px solid #FFF;
  border-radius: 5px;
}

.dot {
  background-color: white;
  height: 6px;
  width: 6px;
  border-radius: 50%;
}

.dot1 {
  position: absolute;
  top: 6px;
  left: 6px;
}

.dot2 {
  position: absolute;
  top: 6px;
  right: 6px;
}

.dot3 {
  position: absolute;
  bottom: 6px;
  left: 6px;
}

.dot4 {
  position: absolute;
  bottom: 6px;
  right: 6px;
}

.cause {
  margin-top: 10px;
  width: 90%;
}

.sub-title {
  display: flex;
  align-items: center;
  font-size: 16px;
  font-weight: 750;
  margin: 35px 0 5px 0;
}

.choose-boxs {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
}

.each-box {
  white-space: nowrap;
  padding: 5px 10px;
  margin: 5px 5px;
  border-radius: 5px;
  border: 1px solid grey;
}

.each-box-active {
  border: transparent;
  border: 1px solid rgb(214, 214, 214);
  background-color: rgb(214, 214, 214);
  color: red;
}

.tips-box {
  border: 1px solid green;
  padding: 30px 15px;
  border-radius: 15px;
  font-size: 20px;
  color: green;
  width: calc(90% - 30px);
}

.repeatTips-box {
  border: 1px solid red;
  padding: 30px 15px;
  border-radius: 15px;
  font-size: 20px;
  color: red;
  width: calc(90% - 30px);
}

.submit-box {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.submit-butt {
  margin: 80px 0;
  height: 100px;
  width: 100px;
  border-radius: 50%;
  font-size: 18px;
  font-weight: 750;
  color: #FFF;
  border: transparent;
  background-color: #5082F5;
}

.foot-bar {
  font-size: 16px;
  height: 50px;
  width: 100vw;
  background-color: #f2f2f2;
  display: flex;
  align-items: center;
  justify-content: center;
}

.foot-bar>a {
  color: #5082F5;
  text-decoration: none;
}
</style>

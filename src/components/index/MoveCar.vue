<template lang="html">
  <div class="casesetting-page">
    <div :class="isMobile() ? 'content-mobile' : 'content'">
      <el-form class="el-form" ref="form" :model="carInfo" label-width="80px">
        <el-form-item label="PushToken">
          <el-input placeholder="请输入PushToken" v-model="carInfo.pushToken"></el-input>
        <div class="re-text" v-if="showConfButt">
          <button @click="quickTo">去配置PushToken</button>
        </div>
        </el-form-item>
        <el-form-item label="车牌号">
          <el-input placeholder="请输入车牌号，如京A·12345" v-model="carInfo.licence"></el-input>
        </el-form-item>
        <el-form-item label="通道选择">
          <el-select v-model="carInfo.channelType" :value=0>
            <el-option v-for="item in channelList" :key="item.channelType" :label="item.cnName" :value="item.channelType">
            </el-option>
          </el-select>
        </el-form-item>
        <el-button class="submit-butt" type="button" @click="setInfoAndSaveQRCode" :disabled="showQRCode">生成并下载二维码
        </el-button>
      </el-form>
      <div v-if="showQRCode" class="qrcode-boders">
        <div class="qrcode-box">
          <div id="qrcode" ref="qrCode"></div>
          <div class="qr-tips">微信扫码，联系车主</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import QRCode from "qrcodejs2";
import html2canvas from "html2canvas";
import download from "downloadjs";

export default {
  name: "movecar",
  data() {
    return {
      channelList: [],
      carInfo: {
        pushToken: "",
        licence: "",
        channelType: "",
      },
      webHook: "https://app.dayutec.cn/#/movecarmsg?carToken=",
      // webHook: "http://localhost:22426/#/movecar?carToken=",
      showConfButt:false,
      showQRCode: false
    };
  },

  mounted() {
    this.getChannelMeta();
  },
  methods: {
    getChannelMeta() {
      this.$coreHttp.get('/public/api/channelMeta').then(res => {
        if (res.status == 200) {
          this.channelList = JSON.parse(res.data.data);
        }
      })
    },
    changeRadio(val) {
      this.carInfo.channelType = val
    },
    quickTo() {
      window.open("https://bit.dayutec.cn/#/list");
    },
    setInfoAndSaveQRCode() {
      //参数校验
      if ("" == this.carInfo.pushToken) {
        this.$message({
          message: "pushToken不能为空",
          type: "error",
          center: true
        });
        return;
      }
      if ("" == this.carInfo.licence) {
        this.$message({
          message: "车牌号不能为空",
          type: "error",
          center: true
        });
        return;
      }
      //获取carToken
      this.getCarToken();
    },
    getCarToken() {
      this.$http
        .post("/car/genCarToken", this.$json.stringify(this.carInfo))
        .then(res => {
          let rsp = res.data;
          if (res.status == 200) {
            if (rsp.code == 0) {
              this.showConfButt=false
              // 生成二维码
              this.createQrCode(rsp.data);
            } else {
              this.showConfButt=true
              this.$message({
                message: rsp.message,
                type: "error",
                center: true
              });
              return;
            }
          }
        });
    },
    createQrCode(carToken) {
      //生成二维码
      if (!this.showQRCode) {
        this.showQRCode = true;
        setTimeout(() => {
          var qrcode = new QRCode(document.getElementById("qrcode"), {
            text: this.webHook + carToken,
            colorDark: "black",
            colorLight: "white",
            correctLevel: QRCode.CorrectLevel.H
          });
          //下载二维码
          this.dlQRCode();
        }, 0);
      }
    },

    dlQRCode: function () {
      let qrcodeBox = document.querySelector(".qrcode-boders");
      html2canvas(qrcodeBox, {
        logging: false,
        useCORS: true
      }).then(canvas => {
        let dataurl = canvas.toDataURL("image/jpeg");
        download(
          dataurl,
          `停车通知二维码${new Date().getTime()}`,
          "image/jpeg"
        );
      });
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.casesetting-page {
  min-height: calc(100vh - 140px);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.reminder {
  width: 100%;
  background-color: rgb(209, 209, 209);
  padding: 10px 0;
  display: flex;
  justify-content: center;
}

.reminder-content {
  width: 60%;
  /* border:1px solid red; */
}

.re-text {
  font-size: 14px;
  text-align: center;
}

.reminder-content-mobile {
  width: 95%;
}

span {
  text-decoration: underline;
  color: #551a8b;
}

.content {
  min-height: calc(100vh - 140px);
  width: 60%;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.content-mobile {
  min-height: calc(100vh - 140px);
  width: 90%;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.el-form {
  margin-top: 150px;
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

/deep/ .el-form-item {
  width: 60%;
}

/deep/ .el-input__inner {
  color: black;
  letter-spacing: 1px;
  border: 2px solid #409eff;
}

.submit-butt {
  margin-top: 30px;
  height: 40px;
  width: 200px;
  background-color: #409EFF;
  color: #FFF;
}

.qrcode-box {
  margin: 30px 30px;
  padding: 30px;
  border: 2px solid #409eff;
  border-radius: 15px;
}

#qrcode {
  height: 250px;
  width: 250px;
}

.qr-tips {
  width: 100%;
  text-align: center;
  margin-top: 20px;
  font-size: 24px;
}
</style>

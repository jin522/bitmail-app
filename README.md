# bitmail-app
比特信-bitmail(https://bit.dayutec.cn)场景应用合集，长期补充更新中...



### 应用 及相关 配置要求：

#### 1、一键扫码挪车

```shell
# pigeon-cases/src/main/resources/application.yml文件配置：
# 【】以及里面的内容，替换为自己集群对应的参数
url: jdbc:postgresql://【127.0.0.1】:【5432】/【bitmail】
username: 【root】
password: 【root】

# pigeon-cases/src/main/resources/platform.properties文件配置：
# 【】以及里面的内容，替换为自己集群对应的参数
url=jdbc:postgresql://【127.0.0.1】:【5432】/【bitmail】
username=【root】
password=【root】

# 上面配置完成后，运行如下类文件中的main函数，进行数据库相关表的自动创建
pigeon-cases/src/test/java/cn/dayutec/pigeon/db/ver100/BuildDB.java
```
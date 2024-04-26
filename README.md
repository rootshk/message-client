### 使用方法

#### 发布Maven仓库
```
mvn clean deploy -P release
```
#### 添加Maven依赖
```xml
<dependencies>
    <dependency>
        <groupId>top.roothk.message</groupId>
        <artifactId>client</artifactId>
        <version>0.0.1</version>
    </dependency>
</dependencies>
```

#### 项目配置
```yaml
fast-message:
  # 服务器地址
  host: http://192.168.123.7:8858
  # 请求密钥
  auth-key: 1691051199123
  # 请求超时 缺省5000
  timeout: 5000
  # 链接超时 缺省5000
  connection-timeout: 5000
```

#### 启动配置
```java
// 开启消息客户端
@EnableMessageClient
@SpringBootApplication
public class AutoApplication {
    public static void main(String[] args) {
        // ...
    }
}
```

#### 使用
```java
public class Message {
    @Resource
    private MessageService messageService;

    public void send() {
        messageService.send("主题", "消息体", "备注");
    }
}

```

package top.roothk.message.client;

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component("root-hk-message-client")
public class MessageService {

    private static final Logger log = LoggerFactory.getLogger(MessageService.class);

    private static final String CHARSET = "UTF-8";
    private static final String AUTH_KEY_HEADER = "Auth-Key";
    private static final String CHANNEL_KEY_QUERY = "channel";
    private static final String MESSAGE_KEY_QUERY = "message";
    private static final String REMARK_KEY_QUERY = "remark";
    private static final String SEND_URL = "/api/v1/message/send";

    private final ServerProperties serverProperties;

    public MessageService(ServerProperties serverProperties) {
        this.serverProperties = serverProperties;
    }

    public String send(String channel, String message, String remark) {
        try {
            return sendExec(channel, message, remark);
        } catch (Exception e) {
            log.trace("Message Send Client Error", e);
            return "-1";
        }
    }

    public String sendExec(String channel, String message, String remark) {
        log.debug("Message Send Client Request [Host: {}] [AuthKey: {}] [Channel: {}] [Message: {}] [Remark: {}]",
                serverProperties.getHost(), serverProperties.getAuthKey(), channel, message, remark);
        long l = System.currentTimeMillis();
        HttpResponse response = HttpRequest.get(serverProperties.getHost() + SEND_URL)
                .contentTypeJson()
                .acceptJson()
                .timeout(serverProperties.getTimeout())
                .connectionTimeout(serverProperties.getConnectionTimeout())
                .header(AUTH_KEY_HEADER, serverProperties.getAuthKey())
                .query(CHANNEL_KEY_QUERY, channel == null ? "" : URLEncoder.encode(channel, StandardCharsets.UTF_8))
                .query(MESSAGE_KEY_QUERY, message == null ? "" : URLEncoder.encode(message, StandardCharsets.UTF_8))
                .query(REMARK_KEY_QUERY, remark == null ? "" : URLEncoder.encode(remark, StandardCharsets.UTF_8))
                .charset(CHARSET)
                .send();
        String result = response.charset(CHARSET).bodyText();
        log.debug("Message Send Client Response [Result: {}] [Time: {}ms]", result, System.currentTimeMillis() - l);
        log.info("Message Send Client Success: {} {} {} {}", channel, message, remark, result);
        return result;
    }

}

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
    private static final String LEVEL_KEY_QUERY = "level";
    private static final String TITLE_KEY_QUERY = "title";
    private static final String SUB_TITLE_KEY_QUERY = "subTitle";
    private static final String MESSAGE_KEY_QUERY = "message";
    private static final String REMARK_KEY_QUERY = "remark";
    private static final String ONLY_KEY_QUERY = "only";
    private static final String SEND_URL = "/api/v1/message/send";

    private final ServerProperties serverProperties;

    public MessageService(ServerProperties serverProperties) {
        this.serverProperties = serverProperties;
    }

    public String info(String message) {
        return info(null, message);
    }

    public String info(String channel, String message) {
        return info(channel, message, null);
    }

    public String info(String channel, String message, String remark) {
        return info(channel, null, null, message, remark);
    }

    public String info(String channel, String title, String subTitle, String message, String remark) {
        return send("info", channel, title, subTitle, message, remark);
    }

    public String infoOnly(String message) {
        return infoOnly(null, message);
    }

    public String infoOnly(String channel, String message) {
        return infoOnly(channel, message, null);
    }

    public String infoOnly(String channel, String message, String remark) {
        return infoOnly(channel, null, null, message, remark);
    }

    public String infoOnly(String channel, String title, String subTitle, String message, String remark) {
        return send("info", channel, title, subTitle, message, remark, true);
    }

    public String success(String message) {
        return success(null, message);
    }

    public String success(String channel, String message) {
        return success(channel, message, null);
    }

    public String success(String channel, String message, String remark) {
        return success(channel, null, null, message, remark);
    }

    public String success(String channel, String title, String subTitle, String message, String remark) {
        return send("success", channel, title, subTitle, message, remark);
    }

    public String successOnly(String message) {
        return successOnly(null, message);
    }

    public String successOnly(String channel, String message) {
        return successOnly(channel, message, null);
    }

    public String successOnly(String channel, String message, String remark) {
        return successOnly(channel, null, null, message, remark);
    }

    public String successOnly(String channel, String title, String subTitle, String message, String remark) {
        return send("success", channel, title, subTitle, message, remark, true);
    }

    public String warn(String message) {
        return warn(null, message);
    }

    public String warn(String channel, String message) {
        return warn(channel, message, null);
    }

    public String warn(String channel, String message, String remark) {
        return warn(channel, null, null, message, remark);
    }

    public String warn(String channel, String title, String subTitle, String message, String remark) {
        return send("warn", channel, title, subTitle, message, remark);
    }

    public String warnOnly(String message) {
        return warnOnly(null, message);
    }

    public String warnOnly(String channel, String message) {
        return warnOnly(channel, message, null);
    }

    public String warnOnly(String channel, String message, String remark) {
        return warnOnly(channel, null, null, message, remark);
    }

    public String warnOnly(String channel, String title, String subTitle, String message, String remark) {
        return send("warn", channel, title, subTitle, message, remark, true);
    }

    public String error(String message) {
        return error(null, message);
    }

    public String error(String channel, String message) {
        return error(channel, message, null);
    }

    public String error(String channel, String message, String remark) {
        return error(channel, null, null, message, remark);
    }

    public String error(String channel, String title, String subTitle, String message, String remark) {
        return send("error", channel, title, subTitle, message, remark);
    }

    public String errorOnly(String message) {
        return errorOnly(null, message);
    }

    public String errorOnly(String channel, String message) {
        return errorOnly(channel, message, null);
    }

    public String errorOnly(String channel, String message, String remark) {
        return errorOnly(channel, null, null, message, remark);
    }

    public String errorOnly(String channel, String title, String subTitle, String message, String remark) {
        return send("error", channel, title, subTitle, message, remark, true);
    }

    public String send(String message) {
        return send(null, message, null);
    }

    public String send(String channel, String message) {
        return send(channel, message, null);
    }

    public String send(String channel, String message, String remark) {
        return send(null, channel, null, null, message, remark);
    }

    public String send(String level, String channel, String title, String subTitle, String message, String remark) {
        try {
            return sendExec(level, channel, title, subTitle, message, remark, null);
        } catch (Exception e) {
            log.trace("Message Send Client Error", e);
            return "-1";
        }
    }

    public String send(String level, String channel, String title, String subTitle, String message, String remark, Boolean only) {
        try {
            return sendExec(level, channel, title, subTitle, message, remark, only);
        } catch (Exception e) {
            log.trace("Message Send Client Error", e);
            return "-1";
        }
    }

    public String sendExec(String level, String channel, String title, String subTitle, String message, String remark, Boolean only) {
        log.debug("Message Send Client Request [Host: {}] [AuthKey: {}] [Channel: {}] [Message: {}] [Remark: {}]",
                serverProperties.getHost(), serverProperties.getAuthKey(), channel, message, remark);
        long l = System.currentTimeMillis();
        HttpResponse response = HttpRequest.get(serverProperties.getHost() + SEND_URL)
                .contentTypeJson()
                .acceptJson()
                .timeout(serverProperties.getTimeout())
                .connectionTimeout(serverProperties.getConnectionTimeout())
                .header(AUTH_KEY_HEADER, serverProperties.getAuthKey())
                .query(LEVEL_KEY_QUERY, level == null ? "" : URLEncoder.encode(level, StandardCharsets.UTF_8))
                .query(TITLE_KEY_QUERY, title == null ? "" : URLEncoder.encode(title, StandardCharsets.UTF_8))
                .query(SUB_TITLE_KEY_QUERY, subTitle == null ? "" : URLEncoder.encode(subTitle, StandardCharsets.UTF_8))
                .query(CHANNEL_KEY_QUERY, channel == null ? "" : URLEncoder.encode(channel, StandardCharsets.UTF_8))
                .query(MESSAGE_KEY_QUERY, message == null ? "" : URLEncoder.encode(message, StandardCharsets.UTF_8))
                .query(REMARK_KEY_QUERY, remark == null ? "" : URLEncoder.encode(remark, StandardCharsets.UTF_8))
                .query(ONLY_KEY_QUERY, only)
                .charset(CHARSET)
                .send();
        String result = response.charset(CHARSET).bodyText();
        log.debug("Message Send Client Response [Result: {}] [Time: {}ms]", result, System.currentTimeMillis() - l);
        log.info("Message Send Client Success: {} {} {} {} {} {} {}", level, channel, title, subTitle, message, remark, result);
        return result;
    }

}

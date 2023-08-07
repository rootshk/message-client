package top.roothk.message.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "fast-message")
public class ServerProperties {

    private String host;
    private Integer timeout;
    private Integer connectionTimeout;
    private String authKey;

}

package top.roothk.message.client;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@EnableConfigurationProperties({ServerProperties.class})
@ComponentScan({"top.roothk.message.client.**"})
public class AutoConfig {
}

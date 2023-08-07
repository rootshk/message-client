package top.roothk.message.client;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(AutoConfig.class)
public @interface EnableMessageClient {
}

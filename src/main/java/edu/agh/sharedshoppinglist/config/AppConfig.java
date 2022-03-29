package edu.agh.sharedshoppinglist.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class AppConfig {

    @Value("${user.login.session.timeout}")
    Integer sessionTimeout;

    @Value("${user.login.session.id.length}")
    Integer sessionIdLength;
}

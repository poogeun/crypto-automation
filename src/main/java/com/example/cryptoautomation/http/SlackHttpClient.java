package com.example.cryptoautomation.http;

import com.slack.api.Slack;
import com.slack.api.webhook.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SlackHttpClient {
    public void send(String message) {
        try {
            Slack instance = Slack.getInstance();
            Payload payload = Payload.builder() // payload -> 요청을 보낼 데이터
                    .text(message)
                    .build();
            instance.send("https://hooks.slack.com/services/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", payload);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

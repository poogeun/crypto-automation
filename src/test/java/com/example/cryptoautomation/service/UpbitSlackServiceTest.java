package com.example.cryptoautomation.service;

import com.example.cryptoautomation.entity.ReportHistory;
import com.example.cryptoautomation.http.SlackHttpClient;
import com.example.cryptoautomation.http.UpbitHttpClient;
import com.example.cryptoautomation.http.UpbitTickerDto;
import com.example.cryptoautomation.repository.ReportHistoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpbitSlackServiceTest {
    @Mock
    private SlackHttpClient slackHttpClient;

    @Mock
    private UpbitHttpClient upbitHttpClient;

    @Mock
    private ReportHistoryRepository reportHistoryRepository;

    @InjectMocks
    private UpbitSlackService upbitSlackService;

    @Test
    void test() {
        // given
        String market = "KRW-BTC";
        when(upbitHttpClient.getTickerByMarket(market))
                .thenReturn(UpbitTickerDto.builder()
                        .trade_price(10.0)
                        .build());

        // when
        upbitSlackService.execute(market);

        // then
        // upbitHttpClient 의 getTickerByMarket(market) 메서드가 위의 흐름상에서 최대 한번 실행 되어야 함.
        verify(upbitHttpClient, atMostOnce()).getTickerByMarket(market);
        verify(slackHttpClient, atMostOnce()).send(any());
        verify(reportHistoryRepository, atMostOnce()).save(any(), any());
    }
}
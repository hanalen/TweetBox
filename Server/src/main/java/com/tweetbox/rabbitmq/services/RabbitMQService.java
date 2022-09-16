package com.tweetbox.rabbitmq.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tweetbox.logger.services.LoggerService;
import com.tweetbox.uploader.dtos.RequestUnZipDto;
import com.tweetbox.uploader.services.UploaderService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQService {
  private final LoggerService loggerService;
  private final RabbitTemplate rabbitTemplate;

  public RabbitMQService(LoggerService loggerService, RabbitTemplate rabbitTemplate) {
    this.loggerService = loggerService;
    this.rabbitTemplate = rabbitTemplate;
  }

  public void SendUnzip(RequestUnZipDto requestUnZipDto) {
    this.rabbitTemplate.convertAndSend("tweetbox.exchange.unzip", "", requestUnZipDto);
  }
}

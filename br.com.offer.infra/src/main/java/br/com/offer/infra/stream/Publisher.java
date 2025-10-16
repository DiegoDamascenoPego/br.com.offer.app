package br.com.offer.infra.stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class Publisher {

    private final String routingKey = "br.com.offer";

    private static final Logger logger = LoggerFactory.getLogger(Publisher.class);

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void dispacth(Object message) {
        try {
            logger.info("Enviando mensagem com routing key: {} - Mensagem: {}", routingKey, message);
            rabbitTemplate.convertAndSend(MessagingRabbitmqConfiguration.topicExchangeName, routingKey, message);
            logger.info("Mensagem enviada com sucesso");
        } catch (Exception e) {
            logger.error("Erro ao enviar mensagem: {}", e.getMessage(), e);
            throw new RuntimeException("Falha ao enviar mensagem", e);
        }
    }

}

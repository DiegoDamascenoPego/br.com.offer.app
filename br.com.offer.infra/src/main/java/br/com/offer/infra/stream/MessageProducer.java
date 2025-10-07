package br.com.offer.infra.stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {

    private static final Logger logger = LoggerFactory.getLogger(MessageProducer.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(String routingKey, Object message) {
        try {
            logger.info("Enviando mensagem com routing key: {} - Mensagem: {}", routingKey, message);
            rabbitTemplate.convertAndSend(MessagingRabbitmqConfiguration.topicExchangeName, routingKey, message);
            logger.info("Mensagem enviada com sucesso");
        } catch (Exception e) {
            logger.error("Erro ao enviar mensagem: {}", e.getMessage(), e);
            throw new RuntimeException("Falha ao enviar mensagem", e);
        }
    }

    public void sendOfferMessage(Object message) {
        sendMessage("br.com.offer.created", message);
    }

    public void sendBidMessage(Object message) {
        sendMessage("br.com.offer.bid", message);
    }

    public void sendAuctionMessage(Object message) {
        sendMessage("br.com.offer.auction", message);
    }
}

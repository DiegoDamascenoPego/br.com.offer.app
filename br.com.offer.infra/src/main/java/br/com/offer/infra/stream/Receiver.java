package br.com.offer.infra.stream;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

@Component
public class Receiver {

    private static final Logger logger = LoggerFactory.getLogger(Receiver.class);
    private CountDownLatch latch = new CountDownLatch(1);

    @RabbitListener(queues = MessagingRabbitmqConfiguration.queueName)
    public void receiveMessage(@Payload String message,
                              @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag,
                              @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String routingKey,
                              Channel channel) {
        try {
            logger.info("Mensagem recebida - Routing Key: {} - Conteúdo: {}", routingKey, message);

            // Processar a mensagem baseado no routing key
            processMessage(routingKey, message);

            // Confirmar o processamento da mensagem (acknowledge manual)
            channel.basicAck(deliveryTag, false);
            logger.info("Mensagem processada com sucesso");

            latch.countDown();
        } catch (Exception e) {
            logger.error("Erro ao processar mensagem: {}", e.getMessage(), e);
            try {
                // Rejeitar a mensagem e reenviar para a fila (requeue = true)
                channel.basicNack(deliveryTag, false, true);
            } catch (Exception ackException) {
                logger.error("Erro ao fazer nack da mensagem: {}", ackException.getMessage(), ackException);
            }
        }
    }

    private void processMessage(String routingKey, String message) {
        switch (routingKey) {
            case "br.com.offer.created":
                logger.info("Processando criação de oferta: {}", message);
                // Implementar lógica específica para criação de oferta
                break;
            case "br.com.offer.bid":
                logger.info("Processando lance: {}", message);
                // Implementar lógica específica para lances
                break;
            case "br.com.offer.auction":
                logger.info("Processando leilão: {}", message);
                // Implementar lógica específica para leilões
                break;
            default:
                logger.warn("Routing key não reconhecida: {}", routingKey);
        }
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}

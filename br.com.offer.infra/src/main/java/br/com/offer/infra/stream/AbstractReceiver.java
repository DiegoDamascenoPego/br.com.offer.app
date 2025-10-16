package br.com.offer.infra.stream;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;

@Component
public abstract class AbstractReceiver<T> implements Receiver<T> {

    private static final Logger logger = LoggerFactory.getLogger(AbstractReceiver.class);
    private CountDownLatch latch = new CountDownLatch(1);

    Class<T> clazz;

    @Autowired
    private ObjectMapper objectMapper;

    public AbstractReceiver(final Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public void receiveMessage(@Payload Message<T> message,
        @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag,
        @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String routingKey,
        Channel channel) {
        try {
            logger.info("Mensagem recebida - Routing Key: {} - Conteúdo: {}", routingKey, message);
            final byte[] bytes = objectMapper.writeValueAsBytes(message.getPayload());
            final T object = objectMapper.readValue(bytes, clazz);
            receiveMessage(object);

            channel.basicAck(deliveryTag, false);
            logger.info("Mensagem processada com sucesso");

            latch.countDown();
        } catch (Exception e) {
            logger.error("Erro ao processar mensagem: {}", e.getMessage(), e);
            try {
                channel.basicNack(deliveryTag, false, false);
            } catch (Exception ackException) {
                logger.error("Erro ao fazer nack da mensagem: {}", ackException.getMessage(), ackException);
            }
        }
    }

}

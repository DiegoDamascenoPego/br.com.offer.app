package br.com.offer.infra.stream;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import com.rabbitmq.client.Channel;

public interface Receiver<T> {

   @RabbitListener(queues = MessagingRabbitmqConfiguration.queueName)
   void receiveMessage(@Payload Message<T> message,
        @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag,
        @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String routingKey,
        Channel channel);

    void receiveMessage(@Payload T message);
}

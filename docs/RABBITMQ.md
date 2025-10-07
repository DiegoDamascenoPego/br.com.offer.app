# Configuração do RabbitMQ no Projeto Offer

## Visão Geral

O RabbitMQ está configurado no projeto para gerenciar mensagens assíncronas entre diferentes componentes do sistema de ofertas e leilões.

## Configuração

### 1. Dependências

O projeto já possui a dependência `spring-boot-starter-amqp` nos módulos `service` e `infra`.

### 2. Configuração no application.yml

```yaml
spring:
  rabbitmq:
    host: localhost
    port: 5672
    username: guest
    password: guest
    virtual-host: /
    connection-timeout: 30000
    publisher-confirm-type: correlated
    publisher-returns: true
    listener:
      simple:
        acknowledge-mode: manual
        retry:
          enabled: true
          initial-interval: 1000
          max-attempts: 3
          multiplier: 2
```

### 3. Componentes Principais

#### MessagingRabbitmqConfiguration
- Define exchanges, filas e bindings
- Configura conversores JSON
- Define filas de dead letter para mensagens com erro

#### MessageProducer
Service para envio de mensagens com métodos específicos:
- `sendOfferMessage()` - Para mensagens de ofertas
- `sendBidMessage()` - Para mensagens de lances
- `sendAuctionMessage()` - Para mensagens de leilões
- `sendMessage()` - Para mensagens customizadas

#### Receiver
Componente que processa mensagens recebidas com:
- Acknowledgment manual
- Tratamento de erros
- Reprocessamento automático

#### MessageController
Controller REST para testar o envio de mensagens via API.

## Execução

### 1. Iniciar o RabbitMQ

```bash
cd devops
docker-compose up -d offer-rabbitmq
```

### 2. Acessar o Management UI

- URL: http://localhost:15672
- Usuário: guest
- Senha: guest

### 3. Testar via API

#### Enviar mensagem de oferta:
```bash
curl -X POST http://localhost:8080/api/messaging/send-offer \
  -H "Content-Type: application/json" \
  -d '{"id": 1, "produto": "Produto Teste", "preco": 100.0}'
```

#### Enviar mensagem de lance:
```bash
curl -X POST http://localhost:8080/api/messaging/send-bid \
  -H "Content-Type: application/json" \
  -d '{"ofertaId": 1, "valor": 150.0, "usuario": "teste"}'
```

#### Enviar mensagem personalizada:
```bash
curl -X POST "http://localhost:8080/api/messaging/send-custom?routingKey=br.com.offer.custom" \
  -H "Content-Type: application/json" \
  -d '{"tipo": "custom", "dados": "teste"}'
```

## Routing Keys

- `br.com.offer.created` - Criação de ofertas
- `br.com.offer.bid` - Lances em ofertas
- `br.com.offer.auction` - Eventos de leilão
- `br.com.offer.dlq.#` - Dead Letter Queue

## Monitoramento

1. **Management UI**: Visualize filas, exchanges e mensagens
2. **Logs**: Acompanhe o processamento via logs da aplicação
3. **Health Check**: Docker Compose inclui health check automático

## Características da Configuração

- **Acknowledgment Manual**: Controle preciso sobre o processamento
- **Retry**: Tentativas automáticas em caso de erro
- **Dead Letter Queue**: Mensagens com falha são redirecionadas
- **Conversão JSON**: Mensagens são automaticamente convertidas
- **Filas Duráveis**: Mensagens persistem após restart
- **Connection Pooling**: Otimização de conexões

## Troubleshooting

1. **RabbitMQ não conecta**: Verifique se o Docker está rodando
2. **Mensagens não são processadas**: Verifique logs da aplicação
3. **Erro de conversão**: Verifique formato JSON das mensagens
4. **Fila cheia**: Monitore via Management UI

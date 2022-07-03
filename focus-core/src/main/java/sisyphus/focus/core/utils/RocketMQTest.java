package sisyphus.focus.core.utils;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class RocketMQTest {

    // 异步 批量数据的异步处理
    // 解耦 串行任务的并行化
    // 削峰 高负载任务的负载均衡
    // 广播 一对多的通信

    // 订阅（Consumer）/发布（Producer）

    // AMQP（Advanced Message Queue Protocol）协议
    // JMS(Java Message Service) binding java
    // Connection channel(virtual connection, multiple)

    // Exchange
    // 1.Direct Exchange
    // EXCHANGE_NAME, ROUTING_KEY, MESSAGE

    // 2.Topic Exchange Subject, most use
    // ROUTING_KEY, REGEX(#:>=0,*:JUST ONE WORD)

    // 3.Fanout Exchange, Multicast

    public static void main(String[] args) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        // rabbitTemplate.send();
    }

}

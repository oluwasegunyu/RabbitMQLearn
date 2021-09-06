package com.yxy.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConsumerTopic2 {
    public static void main(String[] args) throws IOException, TimeoutException {
        //1. 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //2. 设置参数
        factory.setVirtualHost("/myhost");
        factory.setUsername("admin");
        factory.setPassword("admin");
        //3. 创建连接
        Connection connection = factory.newConnection();
        //4. 创建channel
        Channel channel = connection.createChannel();

        String queueName1 = "test_topic_queue1";
        String queueName2 = "test_topic_queue2";

       //6. 接收消息
        //String basicConsume(String queue, boolean autoAck, Consumer callback) throws IOException;
        /*
            queue 队列名称
            autoAck 是否自动确认
            callback 回调函数
         */
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                /*
                    回调方法 收到消息会自动执行该方法
                    consumerTag 标识
                    envelope 获取一些信息：交换机， 路由key
                    properties 配置信息
                    body 数据
                 */

                /*
                System.out.println("consumerTag: " + consumerTag);
                System.out.println("exchange: " + envelope.getExchange());
                System.out.println("RoutingKey: " + envelope.getRoutingKey());
                System.out.println("properties: " + properties);
                */
                System.out.println("body: " + new String(body));
                System.out.println("将日志信息打印到屏幕");
            }
        };

        channel.basicConsume(queueName2, true, consumer);

    }
}

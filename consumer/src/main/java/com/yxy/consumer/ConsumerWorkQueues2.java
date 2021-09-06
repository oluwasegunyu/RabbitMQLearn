package com.yxy.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConsumerWorkQueues2 {
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
        //5. 创建Queue
        //channel.queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments);
        /*
            queue 队列名称
            durable 是否持久化
            exclusive是否独占
            autodelete是否自动删除
            argument参数
         */
        channel.queueDeclare("work_queues", true, false, false, null);
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
            }
        };

        channel.basicConsume("work_queues", true, consumer);

    }
}

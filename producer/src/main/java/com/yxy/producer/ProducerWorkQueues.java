package com.yxy.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ProducerWorkQueues {
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
        //6. 发送消息
        //void basicPublish(String exchange, String routingKey, BasicProperties props, byte[] body) throws IOException;
        /*
            exchange 交换机名称 默认交换机
            routingKey 路由名称
            props 配置信息
            body 消息数据
         */
        for (int i = 0; i < 10; i++) {
            String body = i+"hello rabbitmq ...";
            channel.basicPublish("", "work_queues", null, body.getBytes());
        }

        //7. 释放资源
        channel.close();
        connection.close();
    }
}

package com.yxy.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ProducerPubliserSubscribe {
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
        //5. 创建交换机
        /*
        exchangeDeclare(String exchange,
        BuiltinExchangeType type,
        boolean durable,
        boolean autoDelete,
        boolean internal,
        Map<String, Object> arguments)

        参数：
        exchange  交换机名称
        type 交换机类型
        durable 是否持久化
        autoDelete是否自动删除
        internal 内部使用 一般false
        arguments 参数
         */
        String exchangeName = "test_fanout";
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.FANOUT, true, false, false, null);
        //6. 创建两个队列
        String queueName1 = "test_fanout_queue1";
        String queueName2 = "test_fanout_queue2";
        channel.queueDeclare(queueName1, true, false, false, null);
        channel.queueDeclare(queueName2, true, false, false, null);
        //7. 绑定队列和交换机
        /*
        queueBind(String queue, String exchange, String routingKey)

        参数:
        queue 队列名称
        exchange 交换机名称
        routingKey 路由键，绑定规则
         */
        channel.queueBind(queueName1, exchangeName, "");
        channel.queueBind(queueName2, exchangeName, "");
        //8. 发送消息
        String body = "日志信息";
        channel.basicPublish(exchangeName, "", null, body.getBytes());
        //9. 释放资源
        channel.close();
        connection.close();
    }
}

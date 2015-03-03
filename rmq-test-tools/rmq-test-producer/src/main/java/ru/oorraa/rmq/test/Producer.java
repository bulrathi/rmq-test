package ru.oorraa.rmq.test;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;

/**
 * Created by s.vinogradov on 03.03.15.
 */
public class Producer {
    public static void main(String[] args) throws IOException {
        String host = args[0];
        String exchange = args[1];
        String queue = args[2];
        Long count = Long.valueOf(args[3]);

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(queue, false, false, false, null);
        //channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        String message = "test";
        long bts = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            //channel.basicPublish(exchange, "", null, message.getBytes());
            channel.basicPublish("", queue, null, message.getBytes());
            //channel.basicPublish("", queue, MessageProperties.TEXT_PLAIN, message.getBytes());
        }
        long ets = System.currentTimeMillis() - bts;
        double avg = ets / (double) count;
        //System.out.println(" [x] Sent '" + message + "'");
        System.out.println("Total time: " + ets + "; Avg: " + avg);
        channel.close();
        connection.close();
    }
}

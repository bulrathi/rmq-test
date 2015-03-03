package ru.oorraa.rmq.test;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

/**
 * Created by s.vinogradov on 03.03.15.
 */
public class Consumer {
    public static void main(String[] argv) throws java.io.IOException, java.lang.InterruptedException {
        String host = argv[0];
        String exchange = argv[1];
        String queue = argv[2];
        int qos = Integer.valueOf(argv[3]);

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        //channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        //String queueName = channel.queueDeclare().getQueue();

        System.out.println("Queue: " + queue);
        channel.queueBind(queue, exchange, "");

        System.out.println("Waiting for messages...");

        QueueingConsumer consumer = new QueueingConsumer(channel);
        //QueueingConsumer consumer1 = new QueueingConsumer(channel);
        //QueueingConsumer consumer2 = new QueueingConsumer(channel);
        //QueueingConsumer consumer3 = new QueueingConsumer(channel);

        //channel.basicQos(qos, true);

        channel.basicConsume(queue, true, consumer);
        //channel.basicConsume(queue, true, consumer1);
        //channel.basicConsume(queue, true, consumer2);
        //channel.basicConsume(queue, true, consumer3);

        long count = 0;
        long bts = System.currentTimeMillis();
        try {
            while (true) {
                QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                //QueueingConsumer.Delivery delivery1 = consumer1.nextDelivery();
                //QueueingConsumer.Delivery delivery2 = consumer2.nextDelivery();
                //QueueingConsumer.Delivery delivery3 = consumer3.nextDelivery();
                count++;
                //String message = new String(delivery.getBody());

                //System.out.println(" [x] Received '" + message + "'");
            }
        } catch (InterruptedException ex) {
        }
        long ets = System.currentTimeMillis() - bts;
        double avg = ets / (double) count;
        System.out.println("Total time: " + ets + "; Avg: " + avg);
        //System.out.println(" [x] Sent '" + message + "'");
        channel.close();
        connection.close();
        System.exit(0);
    }
}

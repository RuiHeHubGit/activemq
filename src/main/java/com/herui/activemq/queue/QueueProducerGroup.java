package com.herui.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by HeRui on 2019/2/17.
 */
public class QueueProducerGroup {
    public static void main(String[] args) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("test_queue");
        MessageProducer producer = session.createProducer(queue);
        TextMessage textMessageA = session.createTextMessage("hello A");
        textMessageA.setStringProperty("group", "A");
        producer.send(textMessageA);
        TextMessage textMessageB = session.createTextMessage("hello B");
        textMessageB.setStringProperty("group", "B");
        producer.send(textMessageB);
        producer.close();
        session.close();
        connection.close();
    }
}

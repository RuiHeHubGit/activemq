package com.herui.activemq.topic;

import com.herui.activemq.model.Book;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by HeRui on 2019/2/16.
 */
public class TopicProducer {
    public static void main(String[] args) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic("test_topic");
        MessageProducer producer = session.createProducer(topic);
        TextMessage message = session.createTextMessage("topic_test_message");
        producer.send(message);
        ObjectMessage objectMessage = session.createObjectMessage(new Book(0, "test_book", "test"));
        producer.send(objectMessage);
        producer.close();
        session.close();
        connection.close();
    }
}

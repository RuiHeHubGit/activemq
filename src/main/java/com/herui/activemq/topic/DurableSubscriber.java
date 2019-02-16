package com.herui.activemq.topic;

import org.apache.activemq.*;

import javax.jms.*;
import javax.jms.Message;
import java.io.IOException;

/**
 * Created by HeRui on 2019/2/16.
 */
public class DurableSubscriber {
    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
        Connection connection = null;
        Session session = null;
        TopicSubscriber subscriber = null;
        try {
            connection = connectionFactory.createConnection();
            connection.setClientID("c1");
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic topic = session.createTopic("test_topic");
            subscriber = session.createDurableSubscriber(topic, "test");
            subscriber.setMessageListener(new MessageListener() {
                public void onMessage(Message message) {
                    if(message instanceof TextMessage) {
                        try {
                            System.out.println(((TextMessage) message).getText());
                        } catch (JMSException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            System.in.read();
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(connection != null) {
                if(subscriber != null) {
                    try {
                        subscriber.close();
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
                if(session != null) {
                    try {
                        session.close();
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}

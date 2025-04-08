package com.whhxz.blogexample.mqttclient;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Application {
    public static void main(String[] args) throws Exception {
        String broker = "tcp://192.168.88.1:1883";

        Subscribe sub1 = new Subscribe("sub-1", broker);
        sub1.run();
        Subscribe sub2 = new Subscribe("sub-2", broker);
        sub2.run();


        Publish pub1 = new Publish("pub-1", broker, "testtopic/1");
        Publish pub2 = new Publish("pub-2", broker, "testtopic/2");

        pub1.start();
        pub2.start();
    }


    static class Subscribe implements Runnable, MqttCallback {
        private Logger log;
        //通配符订阅,每个订阅者都会收到
        private String topic = "testtopic/#";
        //共享订阅 只有一个订阅者会收到
//        private String topic = "$queue/testtopic/#";
        private MqttClient client;

        public Subscribe(String name, String broker) throws MqttException {
            log = LoggerFactory.getLogger(name);
            client = new MqttClient(broker, name, new MemoryPersistence());
        }

        @Override
        public void run() {
            try {
                client.setCallback(this);
                client.connect();
                client.subscribe(topic, 0);
            } catch (MqttException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void connectionLost(Throwable throwable) {
            log.error("connection lost");
        }

        @Override
        public void messageArrived(String s, MqttMessage mqttMessage) {
            log.info("msg:{} {}", s, new String(mqttMessage.getPayload()));
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
            log.info("deliveryComplete:{}", iMqttDeliveryToken.isComplete());
        }
    }

    static class Publish extends Thread {
        private Logger log;
        private String topic;
        private MqttClient client;
        private String name;

        public Publish(String name, String broker, String topic) throws MqttException {
            this.topic = topic;
            this.name = name;
            log = LoggerFactory.getLogger(name);
            client = new MqttClient(broker, name, new MemoryPersistence());
        }

        @Override
        public void run() {
            try {
                client.connect();
                while (true) {
                    String content = "i'am " + name + new Random().nextInt(10000);
                    log.info("send smg:{}", content);
                    MqttMessage message = new MqttMessage(content.getBytes());
                    client.publish(topic, message);
                    TimeUnit.MILLISECONDS.sleep(3000 + new Random().nextInt(1000));
                }
            } catch (MqttException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

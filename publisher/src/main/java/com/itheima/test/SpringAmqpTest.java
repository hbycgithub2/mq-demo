package com.itheima.test;


import com.itheima.publisher.PublisherApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.HashMap;
import java.util.UUID;
@Slf4j
@SpringBootTest(classes = PublisherApplication.class)  // 明确指定主配置类
public class SpringAmqpTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    void testSendMessage2Queue() {
        String queueName = "simple.queue";
        String msg = "hello,amqp";
        rabbitTemplate.convertAndSend(queueName, msg);
    }

    /**
     * workQueue
     * 向队列中不停发送消息，模拟消息堆积。
     */

    @Test
    void testWorkQueue() throws InterruptedException {
        String queueName = "work.queue";
        for (int i = 0; i <= 50; i++) {
            String msg = "work.queue,message_" + i;
            rabbitTemplate.convertAndSend(queueName, msg);
            Thread.sleep(20);
        }
    }

    @Test
    void testSendFanout() {
        String exchangeName = "hmall.fanout";
        String msg = "hello, everyone!";
        rabbitTemplate.convertAndSend(exchangeName, null, msg);
    }
    @Test
    void testSendDirect() {
        String exchangeName = "hmall.direct";
       // String msg = "红色警报，由于日本排放核污水，惊现哥斯拉!";
        String msg = "蓝色通知，警报解除，哥斯拉是放的气球!";
        rabbitTemplate.convertAndSend(exchangeName, "blue", msg);
    }

    @Test
    void testSendTopic() {
        String exchangeName = "hmall.topic";
        //String msg = "蓝色通知，警报解除，哥斯拉是放的气球!";
        String msg = "今天天气挺不错，我的心情的挺好的";
        //rabbitTemplate.convertAndSend(exchangeName, "japan.news", msg);
        //rabbitTemplate.convertAndSend(exchangeName, "china.news", msg);
        rabbitTemplate.convertAndSend(exchangeName, "china.weather", msg);
    }

    @Test
    void testSendObject() {
        HashMap<Object, Object> msg = new HashMap<>(2);
        msg.put("name", "jack");
        msg.put("age", 21);
        rabbitTemplate.convertAndSend("object.queue",msg);
    }

    @Test
    void testConfirmCallback() throws InterruptedException {
        // 1.创建cd
        CorrelationData cd = new CorrelationData(UUID.randomUUID().toString());
        // 2.添加ConfirmCallback
        cd.getFuture().addCallback(new ListenableFutureCallback<CorrelationData.Confirm>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("消息回调失败", ex);
            }

            @Override
            public void onSuccess(CorrelationData.Confirm result) {
                log.debug("收到confirm callback回执");
                if (result.isAck()) {
                    // 消息发送成功
                    log.debug("消息发送成功,收到ack");
                } else {
                    // 消息发送失败
                    log.error("消息发送失败，收到nack,原因：{}", result.getReason());
                }
            }
        });
        rabbitTemplate.convertAndSend("hmall.direct", "red2", "hello", cd);
        Thread.sleep(2000);
    }
}

package com.itheima.test;

import com.itheima.consumer.ConsumerApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MqListener {
    @RabbitListener(queues = "simple.queue")
    public void listenSimpleQueue(String msg) {
        System.out.println("消费者收到了smiple.queue的消息：[" + msg + "]");
    }

    @RabbitListener(queues = "work.queue")
    public void listenSimpleQueue1(String msg) throws InterruptedException {
        System.out.println("消费者1 收到了smiple.queue的消息：[" + msg + "]");
        Thread.sleep(20);
    }

    @RabbitListener(queues = "work.queue")
    public void listenSimpleQueue2(String msg) throws InterruptedException {
        System.err.println("消费者2 收到了smiple.queue的消息......................：[" + msg + "]");
        Thread.sleep(200);
    }

    @RabbitListener(queues = "fanout.queue1")
    public void listenFanoutQueue1(String msg) throws InterruptedException {
        System.out.println("消费者1 收到了fanout.queue1的消息：[" + msg + "]");
        Thread.sleep(20);
    }

    @RabbitListener(queues = "fanout.queue2")
    public void listenFanoutQueue2(String msg) throws InterruptedException {
        System.out.println("消费者2 收到了fanout.queue2的消息：[" + msg + "]");
        Thread.sleep(200);
    }

    @RabbitListener(queues = "direct.queue1")
    public void listenDirectQueue1(String msg) throws InterruptedException {
        System.out.println("消费者1 收到了direct.queue1的消息：[" + msg + "]");
        Thread.sleep(20);
    }

    @RabbitListener(queues = "direct.queue2")
    public void listenDirectQueue2(String msg) throws InterruptedException {
        System.out.println("消费者2 收到了direct.queue2的消息：[" + msg + "]");
        Thread.sleep(200);
    }

}

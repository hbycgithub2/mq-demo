package com.itheima.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class MqConfirmConfig implements ApplicationContextAware {
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        RabbitTemplate rabbitTemplate = applicationContext.getBean(RabbitTemplate.class);
        // 配置回调
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            //message 获取消息内容
            //i int replyCode返回的响应码，表示消息退回的原因
            //String s：返回的文本描述  String replyTextRabbitMQ 返回的文本描述，说明退回的具体原因
            //String s1:交换机 String exchange 消息发送时指定的交换器名称。
            //String s2：路由键 String routingKey 消息发送时指定的路由键。
            @Override
            public void returnedMessage(Message message, int i, String s, String s1, String s2) {
                log.debug("收到消息的return callback,exchange:{},key:{},msg:{} code:{},text:{}",
                        s1, s2, message, i, s);
            }
        });
    }
}

package com.example.rabbitmq.tut5;

import org.springframework.amqp.core.AnonymousQueue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"tut5","topics"})
public class QueueConfig5 {
	
	@Bean
	public TopicExchange topic() {
		return new TopicExchange("tut.topic");
	}
	
	@Profile("receiver5")
    private static class ReceiverConfig {
		
		@Bean
	    public Tut5Receiver receiver() {
	        return new Tut5Receiver();
	    }
		
		@Bean
        public Queue autoDeleteQueue1() {
            return new AnonymousQueue();
        }

        @Bean
        public Queue autoDeleteQueue2() {
            return new AnonymousQueue();
        }
        
        @Bean
        public Binding binding1 (TopicExchange topic, Queue autoDeleteQueue1) {
        		return BindingBuilder.bind(autoDeleteQueue1).to(topic).with("*.orange.*");
        }
        
        @Bean
        public Binding binding2 (TopicExchange topic, Queue autoDeleteQueue1) {
        		return BindingBuilder.bind(autoDeleteQueue1).to(topic).with("*.*.rabbit");
        }
        
        @Bean
        public Binding binding3 (TopicExchange topic, Queue autoDeleteQueue2) {
        		return BindingBuilder.bind(autoDeleteQueue2).to(topic).with("lazy.#");
        }
	}
	
	@Profile("sender5")
    @Bean
    public Tut5Sender sender() {
        return new Tut5Sender();
    }

}

package com.didispace;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author 翟永超
 * @create 2017/4/15.
 * @blog http://blog.didispace.com
 */
@RestController
public class DcController {

    @Autowired
    ConsumerService consumerService;

    @GetMapping("/consumer")
    public String dc(Boolean delay) {
        return consumerService.consumer(delay);
    }

    @Service
    class ConsumerService {

        @Autowired
        RestTemplate restTemplate;

        @HystrixCommand(fallbackMethod = "fallback")
        public String consumer(Boolean delay) {
            System.out.println("consumer delay :" + delay);
            return restTemplate.getForObject("http://eureka-client/dc" + (null == delay ? "" : "?delay=" + delay), String.class);
        }

        public String fallback(Boolean delay) {
            System.out.println("fallback delay :" + delay);
            return "fallbck22";
        }

    }

}

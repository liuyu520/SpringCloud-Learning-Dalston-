package com.didispace;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 翟永超
 * @create 2017/4/15.
 * @blog http://blog.didispace.com<br />
 * 断路器参考:https://blog.csdn.net/hry2015/article/details/78554846
 */
@RestController
public class DcController {

    @Autowired
    ConsumerService consumerService;

    @GetMapping("/consumer")
    public String dc(Boolean delay,
                     HttpServletRequest request,
                     HttpServletResponse response) {
        return consumerService.consumer(delay,
                request,
                response);
    }

    @Service
    class ConsumerService {

        @Autowired
        RestTemplate restTemplate;

        @HystrixCommand(fallbackMethod = "fallback")
        public String consumer(Boolean delay,
                               HttpServletRequest request,
                               HttpServletResponse response) {
            System.out.println("consumer delay :" + delay);
            return restTemplate.getForObject("http://eureka-client/dc" + (null == delay ? "" : "?delay=" + delay), String.class);
        }

        public String fallback(Boolean delay,
                               HttpServletRequest request,
                               HttpServletResponse response) {
            System.out.println("fallback delay :" + delay);
            try {
                response.sendRedirect("error2.jsp");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }

}

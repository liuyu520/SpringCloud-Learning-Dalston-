package com.didispace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 翟永超
 * @create 2017/4/15.
 * @blog http://blog.didispace.com
 */
@RestController
public class DcController {

    @Autowired
    DiscoveryClient discoveryClient;

    /***
     *
     * @param delay : 是否延迟,为了人为制造服务故障
     * @return
     */
    @GetMapping("/dc")
    public String dc(Boolean delay) {
        System.out.println("delay :" + delay);
        if (null != delay && delay) {
            try {
                Thread.sleep(6000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        String services = "Services: " + discoveryClient.getServices();
        System.out.println(services);
        return services;
    }

}

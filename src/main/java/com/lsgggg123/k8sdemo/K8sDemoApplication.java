/**
 * Alipay.com Inc. Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.lsgggg123.k8sdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 *
 * @author ls
 * @version $Id: K8sDemoApplication.java, v 0.1 2019年11月08日 2:16 PM ls Exp $
 */
@SpringBootApplication
@EnableConfigurationProperties
public class K8sDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(K8sDemoApplication.class, args);
    }
}
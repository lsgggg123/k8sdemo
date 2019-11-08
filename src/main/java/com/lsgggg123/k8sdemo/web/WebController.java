/**
 * Alipay.com Inc. Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.lsgggg123.k8sdemo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ls
 * @version $Id: DeploymentController.java, v 0.1 2019年06月17日 9:48 PM ls Exp $
 */
@Controller
@RequestMapping("/k8sdemo/api/v1")
public class WebController {

    @RequestMapping("web")
    @ResponseBody
    public Object agent1() {
        Map<String, Object> map = new HashMap<>();
        map.put("ok", 200);
        map.put("web", "WebController");
        return map;
    }
}
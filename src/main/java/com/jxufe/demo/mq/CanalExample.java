package com.jxufe.demo.mq;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

/**
 * @Author wlb
 * @Date 2023/9/16 12:07
 **/
@Component
@ConfigurationProperties(prefix = "canal")
public class CanalExample implements Serializable {

    private String server;

    private Integer port;

    private List<Example> examples;

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public List<Example> getExamples() {
        return examples;
    }

    public void setExamples(List<Example> examples) {
        this.examples = examples;
    }

    /**
     * @Author wlb
     * @Date 2023/9/16 12:08
     **/
    public static class Example implements Serializable {
        private String name;

        private String subscribe;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSubscribe() {
            return subscribe;
        }

        public void setSubscribe(String subscribe) {
            this.subscribe = subscribe;
        }
    }
}

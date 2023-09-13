//package com.jxufe.demo.config;
//
//import co.elastic.clients.elasticsearch.ElasticsearchClient;
//import co.elastic.clients.json.jackson.JacksonJsonpMapper;
//import co.elastic.clients.transport.ElasticsearchTransport;
//import co.elastic.clients.transport.rest_client.RestClientTransport;
//import org.apache.http.HttpHost;
//import org.elasticsearch.client.RestClient;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @Author wlb
// * @Date 2023/9/12 23:52
// **/
//@Configuration
//public class ElasticSearchClientConfig {
//    //配置RestHighLevelClient依赖到spring容器中待用
//    @Bean
//    public ElasticsearchClient restHighLevelClient() {
//        RestClient restClient = RestClient.builder(
//                new HttpHost("127.0.0.1", 9200)
//        ).build();
//        ElasticsearchTransport elasticsearchTransport = new RestClientTransport(restClient,new JacksonJsonpMapper());
//
//        return new ElasticsearchClient(elasticsearchTransport);
//    }
//}

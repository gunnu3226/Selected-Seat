package io.nbc.selectedseat.elasticsearch.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.TransportUtils;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import javax.net.ssl.SSLContext;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchConfig {

    String host = "43.203.215.243";
    int port = 9200;
    String fingerprint = "8cd17c4ea86a4f2ace14ea2eb819c0ff60177f8f89969323ad8d9f4fdd2f8921";
    String id = "elastic";
    String password = "VJ7T39ui382KA-qY-ldQ";

    @Bean(name = "esClient")
    public ElasticsearchClient esClient() {

        SSLContext sslContext = TransportUtils
            .sslContextFromCaFingerprint(fingerprint);

        BasicCredentialsProvider credsProv = new BasicCredentialsProvider();
        credsProv.setCredentials(
            AuthScope.ANY, new UsernamePasswordCredentials(id, password)
        );

        RestClient restClient = RestClient
            .builder(new HttpHost(host, port, "https"))
            .setHttpClientConfigCallback(hc -> hc
                .setSSLContext(sslContext)
                .setDefaultCredentialsProvider(credsProv)
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
            )
            .build();

        ElasticsearchTransport transport = new RestClientTransport(
            restClient, new JacksonJsonpMapper());

        ElasticsearchClient esClient = new ElasticsearchClient(transport);
        return esClient;
    }

    @Bean(name = "restHighLevelClient")
    public RestHighLevelClient restHighLevelClient() {

        SSLContext sslContext = TransportUtils
            .sslContextFromCaFingerprint(fingerprint);

        BasicCredentialsProvider credsProv = new BasicCredentialsProvider();
        credsProv.setCredentials(
            AuthScope.ANY, new UsernamePasswordCredentials(id, password)
        );

        RestClientBuilder restClientBuilder = RestClient
            .builder(new HttpHost(host, port, "https"))
            .setHttpClientConfigCallback(hc -> hc
                .setSSLContext(sslContext)
                .setDefaultCredentialsProvider(credsProv)
            );

        return new RestHighLevelClient(restClientBuilder);
    }
}

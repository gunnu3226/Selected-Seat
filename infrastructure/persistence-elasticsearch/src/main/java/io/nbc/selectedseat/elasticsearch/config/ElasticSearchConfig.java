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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "io.nbc.selectedseat.elasticsearch")
public class ElasticSearchConfig {

    @Value("${es.host}")
    private String host;

    @Value("${es.port}")
    private int port;

    @Value("${es.fingerprint}")
    private String fingerprint;

    @Value("${es.id}")
    private String id;

    @Value("${es.password}")
    private String password;

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
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
            );

        return new RestHighLevelClient(restClientBuilder);
    }
}

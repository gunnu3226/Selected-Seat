package io.nbc.selectedseat.mail.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsSesConfig {

    @Value("${aws.ses.access-key}")
    public String accessKey;

    @Value("${aws.ses.secret-key}")
    public String secretKey;

    @Bean
    public AmazonSimpleEmailService amazonSimpleEmailService() {
        final BasicAWSCredentials basicAWSCredentials
            = new BasicAWSCredentials(accessKey, secretKey);
        final AWSStaticCredentialsProvider awsStaticCredentialsProvider
            = new AWSStaticCredentialsProvider(basicAWSCredentials);

        return AmazonSimpleEmailServiceClientBuilder.standard()
            .withCredentials(awsStaticCredentialsProvider)
            .withRegion(Regions.AP_NORTHEAST_2)
            .build();
    }
}

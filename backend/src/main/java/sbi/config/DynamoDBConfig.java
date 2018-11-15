package sbi.config;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

@Configuration
@EnableDynamoDBRepositories(basePackages = "sbi.repositories")
public class DynamoDBConfig
{    
   @Value("${amazon.aws.accesskey}")
   private String amazonAWSAccessKey;

   @Value("${amazon.aws.secretkey}")
   private String amazonAWSSecretKey;

   @Autowired
   private ApplicationContext context;

   @Bean
   public AmazonDynamoDB amazonDynamoDB()
   {
      BasicAWSCredentials awsCreds = new BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey);
      AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
            .withRegion(Regions.US_WEST_2)
            .build(); 
      
      return amazonDynamoDB;
   }

  
   @Bean(name = "mvcHandlerMappingIntrospector")
   public HandlerMappingIntrospector mvcHandlerMappingIntrospector()
   {
      return new HandlerMappingIntrospector(context);
   }
}

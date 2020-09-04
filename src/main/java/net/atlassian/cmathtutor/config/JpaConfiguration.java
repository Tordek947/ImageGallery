package net.atlassian.cmathtutor.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "net.atlassian.cmathtutor.repository")
@EnableTransactionManagement
public class JpaConfiguration {

}

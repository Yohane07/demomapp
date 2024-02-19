package fr.oli.pres.demo.eip;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import fr.oli.pres.demo.entity.Author;
import fr.oli.pres.demo.entity.Book;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.Splitter;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.ExecutorChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.jdbc.JdbcPollingChannelAdapter;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableIntegration
public class SplitterAgreggatorConfig {

//    @Bean
//    public DataSource dataSource() {
//        // Configure your DataSource, for example, using HikariCP
//        HikariConfig config = new HikariConfig();
//        config.setJdbcUrl("your_database_url");
//        config.setUsername("your_database_username");
//        config.setPassword("your_database_password");
//        return new HikariDataSource(config);
//    }

        @Bean
        public MessageChannel splitterOutputChannel() {
            ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
            taskExecutor.setCorePoolSize(5);
            taskExecutor.setMaxPoolSize(10);
            taskExecutor.setQueueCapacity(25);
            taskExecutor.setThreadNamePrefix("splitter-aggregator-thread-");
            taskExecutor.initialize();

            return new ExecutorChannel(taskExecutor); // Utilisation de l'Executor pour le traitement asynchrone
        }

        @Splitter
        public List<Message<?>> customSplitter(Message<?> message) {
            // Logique de votre splitter
            // Divisez le message en éléments individuels et retournez une liste de messages
            // Par exemple, vous pouvez extraire une liste d'éléments d'un message de collection
            List<Message<?>> splitMessages = new ArrayList<>();
            Object payload = message.getPayload();
            if (payload instanceof Collection<?>) {
                for (Object item : (Collection<?>) payload) {
                    splitMessages.add(MessageBuilder.withPayload(item).copyHeaders(message.getHeaders()).build());
                }
            }
            return splitMessages;
        }
//
//        @Bean
//        public IntegrationFlow splitterFlow() {
//            return IntegrationFlow.from("inputChannel")
//                    .split(this,"customSplitter" )
//                    .channel("splitterOutputChannel")
//                    .handle(message -> {
//                        // Logique de traitement à effectuer sur chaque élément divisé
//                        // Vous pouvez effectuer un traitement supplémentaire ici
//                    })
//                    .aggregate(aggregatorSpec -> aggregatorSpec
//                            .correlationStrategy(message -> message.getHeaders().get("correlationId"))
//                            .releaseStrategy(group -> group.size() >= 5)
//                            .expireGroupsUponCompletion(true)
//                    )
//                    .handle(message -> {
//                        // Logique de traitement des messages agrégés
//                        List<Message<?>> messages = (List<Message<?>>) message.getPayload();
//                        for (Message<?> msg : messages) {
//                            // Effectuez ici le traitement des messages agrégés
//                        }
//                    })
//                    .get();
//        }


        @Bean
        public MessageChannel input() {
            ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
            taskExecutor.setCorePoolSize(5);
            taskExecutor.setMaxPoolSize(10);
            taskExecutor.setQueueCapacity(25);
            taskExecutor.setThreadNamePrefix("splitter-aggregator-thread-");
            taskExecutor.initialize();

            return new ExecutorChannel(taskExecutor); // Utilisation de l'Executor pour le traitement asynchrone
        }

        @Bean
        public MessageChannel inputChannel() {

            return new QueueChannel(10); // Utilisation de l'Executor pour le traitement asynchrone
           //  return new DirectChannel(); // Utilisation de l'Executor pour le traitement asynchrone

        }

        @Bean
        public MessageChannel pubsubChannel() {
            // return new QueueChannel(100); // Utilisation de l'Executor pour le traitement asynchrone
            return new PublishSubscribeChannel(); // Utilisation de l'Executor pour le traitement asynchrone
        }


        @Bean
        public ThreadPoolExecutor threadPoolExecutor() {
            return (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        }

        @Bean
        public MessageChannel executorChannel() {
            return new ExecutorChannel(threadPoolExecutor());
        }




        @Splitter
        public List<Message<Book>> splitter(Message<Author> message) {
            // Logique de votre splitter
            // Divisez le message en éléments individuels et retournez une liste de messages
            // Par exemple, vous pouvez extraire une liste d'éléments d'un message de collection
            List<Message<Book>> splitMessages = new ArrayList<>();
            Author author = message.getPayload();
            author.getBooks();

            for (Book book : author.getBooks()) {
                splitMessages.add(MessageBuilder.withPayload(book).copyHeaders(message.getHeaders()).build());
            }

            return splitMessages;
        }

    @Bean
    public JdbcPollingChannelAdapter jdbcPollingChannelAdapter(DataSource dataSource) {
        JdbcPollingChannelAdapter adapter =
                new JdbcPollingChannelAdapter(
                        dataSource,
                        "SELECT * FROM demande_differe WHERE en_cours = false FOR UPDATE SKIP LOCKED");
        adapter.setUpdateSql("UPDATE demande_differe SET en_cours = true WHERE id = :id");
        adapter.setMaxRows(1); // You may limit the number of rows to update at once
        return adapter;
    }

    @Bean
    public PollerMetadata poller() {
        return Pollers.fixedRate(10000)  // Poll every 10 seconds
                .maxMessagesPerPoll(1).get();  // Maximum messages to poll in a single poll

    }


    @Bean
        public IntegrationFlow testFlow() {
//            return IntegrationFlow.from(jdbcPollingChannelAdapter(dataSource()), spec ->
//                            spec.poller(poller()))
//                    .channel("inputChannel")
        return IntegrationFlows.from("inputChannel")
                    .handle((payload, headers) -> {
                        try {
                            Thread.sleep(2000);
                            System.out.println("Gestion du message "+ payload);
                            Thread.sleep(2000);

                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        return payload;
                    }
                    ).split(this,"splitter")
                    .channel("executorChannel")
                    .handle((payload, headers) -> {
                                try {
                                    Thread.sleep(2000);

                                    System.out.println("Thread.currentThread() "+ Thread.currentThread().getId());
                                    System.out.println("Livre : "+ payload);
                                    Thread.sleep(2000);

                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                                return payload;
                            }
                    )
                    .channel("pubsubChannel")
                    .get();
        }





}

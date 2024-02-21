package fr.oli.pres.demo.poller;
import org.springframework.context.annotation.Configuration;
@Configuration
public class JdbcIntegrationConfig {


//    @Bean
//    public DataSource dataSource(DataSource originalDataSource) {
//        ChainListener listener = new ChainListener();
//        SLF4JQueryLoggingListener loggingListener = new SLF4JQueryLoggingListener();
//        //loggingListener.setQueryLogEntryCreator(new InlineQueryLogEntryCreator());
//        listener.addListener(loggingListener);
//        listener.addListener(new DataSourceQueryCountListener());
//        return ProxyDataSourceBuilder
//                .create(originalDataSource)
//                .name("DS-Proxy")
//                .listener(listener)
//                .build();
//    }

//    @Bean
//    public JdbcPollingChannelAdapter jdbcPollingChannelAdapter() {
//        JdbcPollingChannelAdapter adapter = new JdbcPollingChannelAdapter(this.dataSource, "SELECT * FROM votre_table");
//        adapter.setRowMapper(new BeanPropertyRowMapper<>(Author.class)); // Remplacez "VotreObjet" par votre classe de modèle
//        return adapter;
//    }
//
//    @Bean
//    public MessageChannel jdbcInputChannel() {
//        return new DirectChannel();
//    }
//
//    @Bean
//    public IntegrationFlow jdbcInboundFlow() {
//        return IntegrationFlow.from(jdbcPollingChannelAdapter(),
//                        e -> e.poller(Pollers.fixedRate(5000))) // Changez la fréquence de polling selon vos besoins
//                .channel(jdbcInputChannel())
//                .get();
//    }
}






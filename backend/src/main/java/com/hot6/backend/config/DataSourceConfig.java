package com.hot6.backend.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    // ✅ Master Hikari 설정 바인딩
    @Bean
    @ConfigurationProperties("spring.datasource.master.hikari")
    public HikariConfig hikariConfigMaster() {
        return new HikariConfig();
    }

    @Bean(name = "masterDataSource")
    public DataSource masterDataSource(@Qualifier("hikariConfigMaster") HikariConfig config) {
        return new HikariDataSource(config);
    }

    // ✅ Slave Hikari 설정 바인딩
    @Bean
    @ConfigurationProperties("spring.datasource.slave.hikari")
    public HikariConfig hikariConfigSlave() {
        return new HikariConfig();
    }

    @Bean(name = "slaveDataSource")
    public DataSource slaveDataSource(@Qualifier("hikariConfigSlave") HikariConfig config) {
        return new HikariDataSource(config);
    }

    // ✅ 라우팅 데이터소스 구성
    @Bean
    @DependsOn({"masterDataSource", "slaveDataSource"})
    public DataSource routingDataSource(
            @Qualifier("masterDataSource") DataSource master,
            @Qualifier("slaveDataSource") DataSource slave) {

        DynamicRoutingDataSource routingDataSource = new DynamicRoutingDataSource();
        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put("MASTER", master);
        dataSourceMap.put("SLAVE", slave);

        routingDataSource.setTargetDataSources(dataSourceMap);
        routingDataSource.setDefaultTargetDataSource(master);
        return routingDataSource;
    }

    // ✅ 최종 프록시용 데이터소스 (Lazy Proxy)
    @Primary
    @Bean
    @DependsOn("routingDataSource")
    public DataSource dataSource(DataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }

    // ✅ 트랜잭션 매니저
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager tm = new JpaTransactionManager();
        tm.setEntityManagerFactory(emf);
        return tm;
    }
}

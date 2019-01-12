package com.yihuisoft.yihuisoftservice.ds;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
/**
 * 从数据源配置类
 * @author tonywu
 * @version v1.0.0
 */
@Configuration
@PropertySource("classpath:oracle-dev.properties")
// 扫描 Mapper 接口并容器管理
@MapperScan(basePackages = ClusterDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "clusterSqlSessionFactory")
public class ClusterDataSourceConfig {

    /**包扫描配置项*/
    // 精确到 cluster 目录，以便跟其他数据源隔离
    //static final String PACKAGE = "com.yihuisoft.bankinterfacebiz.mapper.cluster";
    static final String PACKAGE = "com.yihuisoft.*biz*.mapper.*";
    /**xml扫描配置项*/
    static final String MAPPER_LOCATION = "classpath*:mybatis*/*/*.xml";

    /**url*/
    @Value("${cluster.datasource.url}")
    private String url;

    /**用户名*/
    @Value("${cluster.datasource.username}")
    private String user;
    /**密码*/
    @Value("${cluster.datasource.password}")
    private String password;
    /**驱动类*/
    @Value("${cluster.datasource.driverClassName}")
    private String driverClass;

    /**mybatis配置文件**/
    @Value("${mybatis.configLocation}")
    private String configLocation;

    /**
     * 从数据源
     * @return
     */
    @Bean(name = "clusterDataSource")
    public DataSource clusterDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        if(driverClass.contains("oracle")){
            dataSource.setValidationQuery("select 1 from dual");
        }else {
            dataSource.setValidationQuery("select 1");
        }
        return dataSource;
    }

    /**
     * 从事务管理
     * @return
     */
    @Bean(name = "clusterTransactionManager")
    public DataSourceTransactionManager clusterTransactionManager() {
        return new DataSourceTransactionManager(clusterDataSource());
    }

    /**
     * 从sql session工厂
     * @param clusterDataSource
     * @return
     * @throws Exception
     */
    @Bean(name = "clusterSqlSessionFactory")
    public SqlSessionFactory clusterSqlSessionFactory(@Qualifier("clusterDataSource") DataSource clusterDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(clusterDataSource);
        sessionFactory.setConfigLocation(new DefaultResourceLoader().getResource(configLocation));
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(ClusterDataSourceConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }
}
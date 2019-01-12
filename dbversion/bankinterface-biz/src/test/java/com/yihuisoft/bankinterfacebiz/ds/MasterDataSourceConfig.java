package com.yihuisoft.bankinterfacebiz.ds;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * 主数据源配置类
 *
 * @author tonywu
 * @version v1.0.0
 */
@Configuration
@PropertySource("classpath:oracle-dev.properties")

// 扫描 Mapper 接口并容器管理
@MapperScan(basePackages = MasterDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "masterSqlSessionFactory")
public class MasterDataSourceConfig {
    /**
     * 包扫描配置项
     */
    // 精确到 master 目录，以便跟其他数据源隔离
    //static final String PACKAGE = "com.yihuisoft.bankinterfacebiz.mapper.master";
    static final String PACKAGE = "com.yihuisoft.*biz*.mapper.*";
    /**
     * xml扫描配置项
     */
    //static final String MAPPER_LOCATION = "classpath:mybatiscom/master/*.xml";
    static final String MAPPER_LOCATION = "classpath*:mybatis*/*/*.xml";

    /**
     * url
     */
    @Value("${master.datasource.url}")
    private String url;
    /**
     * 用户名
     */
    @Value("${master.datasource.username}")
    private String user;
    /**
     * 密码
     */
    @Value("${master.datasource.password}")
    private String password;
    /**
     * 驱动程序类
     */
    @Value("${master.datasource.driverClassName}")
    private String driverClass;

    /**
     * mybatis配置文件
     **/
    @Value("${mybatis.configLocation}")
    private String configLocation;

    /**
     * 主数据源
     *
     * @return
     */
    @Bean(name = "masterDataSource")
    @Primary
    public DataSource masterDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        if (driverClass.contains("oracle")) {
            dataSource.setValidationQuery("select 1 from dual");
        } else {
            dataSource.setValidationQuery("select 1");
        }
        return dataSource;
    }

    /**
     * 主事务管理
     *
     * @return
     */
    @Bean(name = "masterTransactionManager")
    @Primary
    public DataSourceTransactionManager masterTransactionManager() {
        return new DataSourceTransactionManager(masterDataSource());
    }

    /**
     * 主sql session工厂
     *
     * @param masterDataSource
     * @return
     * @throws Exception
     */
    @Bean(name = "masterSqlSessionFactory")
    @Primary
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("masterDataSource") DataSource masterDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(masterDataSource);
        sessionFactory.setConfigLocation(new DefaultResourceLoader().getResource(configLocation));
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(MasterDataSourceConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }
}
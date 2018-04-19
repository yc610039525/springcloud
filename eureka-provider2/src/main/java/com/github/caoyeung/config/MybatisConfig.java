package com.github.caoyeung.config;

import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.LoggerFactory;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageHelper;

@Configuration
@PropertySource(value = "classpath:db.properties")
public class MybatisConfig implements EnvironmentAware {
	private RelaxedPropertyResolver propertyResolver;
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(MybatisConfig.class);
    private static String regexp = "execution (* com.github.caoyeung.service..*Impl.*(..))";
    private static String sqlMapLocation = "classpath:/sql/mapper/*.xml";
    @Override
    public void setEnvironment(Environment environment) {
        this.propertyResolver = new RelaxedPropertyResolver(environment, "");
    }
 
    @Bean(name = "druidDataSourceTnms", initMethod = "init", destroyMethod = "close")
    @Scope(value = BeanDefinition.SCOPE_SINGLETON)
    @Primary
    public DruidDataSource dataSource() throws SQLException {
        DruidDataSource druidDataSource = new DruidDataSource();
 
        try {
            druidDataSource.setDriverClassName(propertyResolver.getProperty("driverClassName"));
            druidDataSource.setUrl(propertyResolver.getProperty("url"));
            druidDataSource.setUsername(propertyResolver.getProperty("db.username"));
            druidDataSource.setPassword(propertyResolver.getProperty("db.password"));
            druidDataSource.setInitialSize(Integer.parseInt(propertyResolver.getProperty("initialSize")));
            druidDataSource.setMinIdle(Integer.parseInt(propertyResolver.getProperty("minIdle")));
            druidDataSource.setMaxActive(Integer.parseInt(propertyResolver.getProperty("maxActive")));
            druidDataSource.setMaxWait(Integer.parseInt(propertyResolver.getProperty("maxWait")));
            druidDataSource.setTimeBetweenEvictionRunsMillis(Long.parseLong(propertyResolver.getProperty("timeBetweenEvictionRunsMillis")));
            druidDataSource.setMinEvictableIdleTimeMillis(Long.parseLong(propertyResolver.getProperty("minEvictableIdleTimeMillis")));
            druidDataSource.setValidationQuery(propertyResolver.getProperty("validationQuery"));
            druidDataSource.setTestWhileIdle(Boolean.parseBoolean(propertyResolver.getProperty("testWhileIdle")));
            druidDataSource.setTestOnBorrow(Boolean.parseBoolean(propertyResolver.getProperty("testOnBorrow")));
            druidDataSource.setTestOnReturn(Boolean.parseBoolean(propertyResolver.getProperty("testOnReturn")));
            druidDataSource.setPoolPreparedStatements(Boolean.parseBoolean(propertyResolver.getProperty("poolPreparedStatements")));
            druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(Integer.parseInt(propertyResolver.getProperty("maxPoolPreparedStatementPerConnectionSize")));
        } catch (Throwable throwable) {
            logger.error("datasource init failed.", throwable);
            throw throwable;
        }
        return druidDataSource;
    }
    
    @Bean("sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("druidDataSourceTnms")DataSource ds) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(ds);
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("reasonable", "true");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("returnPageInfo", "check");
        properties.setProperty("params", "count=countSql");
        pageHelper.setProperties(properties);
        sqlSessionFactoryBean.setPlugins(new Interceptor[]{pageHelper});
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources(sqlMapLocation));
//        sqlSessionFactoryBean.setConfigLocation(resolver.getResources("classpath:mybatis-config.xml")[0]);
        return sqlSessionFactoryBean.getObject();
    }
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage(propertyResolver.getProperty("mapper.package"));
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        return mapperScannerConfigurer;
    }
    //
    @Bean(name = "txManager")
    public PlatformTransactionManager annotationDrivenTransactionManager(@Qualifier("druidDataSourceTnms")DataSource ds) throws SQLException {
        return new DataSourceTransactionManager(ds);
    }
    
    @Bean(name = "txInterceptor")
    @Primary
    public TransactionInterceptor transactionInterceptor(@Qualifier("txManager")PlatformTransactionManager transactionManager) throws SQLException {
        Properties props = new Properties();
        props.setProperty("select*", "PROPAGATION_SUPPORTS,-Throwable,readOnly");
        props.setProperty("insert*", "PROPAGATION_REQUIRED,-java.lang.Exception");
        props.setProperty("update*", "PROPAGATION_REQUIRED,-java.lang.Exception");
        props.setProperty("delete*", "PROPAGATION_REQUIRED,-java.lang.Exception");
        props.setProperty("*", "PROPAGATION_REQUIRED");
        TransactionInterceptor txAdvice = new TransactionInterceptor(transactionManager, props);
        return txAdvice;
    }
    @Bean
    public AspectJExpressionPointcutAdvisor pointcutAdvisor(@Qualifier("txInterceptor")TransactionInterceptor txInterceptor){
      AspectJExpressionPointcutAdvisor pointcutAdvisor = new AspectJExpressionPointcutAdvisor();
      pointcutAdvisor.setAdvice(txInterceptor);
      pointcutAdvisor.setExpression(regexp);
      return pointcutAdvisor;
    }
    
    /*@Bean  
    public BeanNameAutoProxyCreator transProxy() {
        BeanNameAutoProxyCreator creator = new BeanNameAutoProxyCreator();
        creator.setProxyTargetClass(true);
        creator.setBeanNames("*Impl","*Service","*BOImpl","*BO");
        creator.setInterceptorNames("txInterceptor");
        return creator;
 
    }*/
    
    @Bean("sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory")SqlSessionFactory sqlSessionFactory) {
    	SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
		return sqlSessionTemplate;
    }
}

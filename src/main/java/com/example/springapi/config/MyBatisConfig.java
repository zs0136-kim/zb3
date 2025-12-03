package com.example.springapi.config;

import org.apache.ibatis.session.LocalCacheScope;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * MyBatis設定クラス
 * XML設定をJava Configに変換
 */
@Configuration
@MapperScan("com.example.springapi.**.infrastructure.mapper")
public class MyBatisConfig {

    /**
     * SqlSessionFactoryBeanを設定
     * MyBatisの設定（mapUnderscoreToCamelCase、localCacheScope）を適用
     * 
     * @param dataSource データソース（Spring Bootが自動設定）
     * @return SqlSessionFactoryBean
     * @throws Exception 設定エラー時
     */
    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        
        // データソースを設定
        sessionFactory.setDataSource(dataSource);
        
        // MyBatis設定オブジェクトを作成（完全修飾名を使用してimport衝突を回避）
        org.apache.ibatis.session.Configuration mybatisConfig = new org.apache.ibatis.session.Configuration();
        
        // アンダースコアをキャメルケースに自動変換（例: user_name → userName）
        mybatisConfig.setMapUnderscoreToCamelCase(true);
        
        // ローカルキャッシュスコープをSTATEMENTに設定（各SQL文ごとにキャッシュをクリア）
        mybatisConfig.setLocalCacheScope(LocalCacheScope.STATEMENT);
        
        // 設定を適用
        sessionFactory.setConfiguration(mybatisConfig);
        
        // マッパーXMLファイルの場所を設定（application.propertiesの設定を補完）
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolver.getResources("classpath:mapper/**/*.xml"));
        
        return sessionFactory;
    }
}

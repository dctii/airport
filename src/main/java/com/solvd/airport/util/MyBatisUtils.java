package com.solvd.airport.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;

public class MyBatisUtils {
    private static final Logger LOGGER = LogManager.getLogger(MyBatisUtils.class);
    private static SqlSessionFactory sqlSessionFactory;

    static {
        InputStream inputStream;
        try {
            LOGGER.info("'mybatis-config.xml' filepath (stored in the 'resources' directory: " + ConfigConstants.MYBATIS_XML_CONFIG_FILEPATH);
            inputStream = Resources.getResourceAsStream(ConfigConstants.MYBATIS_XML_CONFIG_FILEPATH);
            LOGGER.info("InputStream: " + inputStream.toString());
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            LOGGER.info ("sqlSessionFactory: " + sqlSessionFactory.toString());
        } catch (IOException e) {
            throw new RuntimeException("Error initializing SqlSessionFactory: " + e.getMessage(), e);
        }
    }

    public static SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

    private MyBatisUtils() {
        ExceptionUtils.preventUtilityInstantiation();
    }
}

package clonecoder.springLover;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.io.IOException;

@Configuration
@EnableTransactionManagement
@ComponentScan(value = "clonecoder.springLover")
public class AppConfig {

    static Logger logger = LogManager.getLogger(AppConfig.class);

    public AppConfig() throws IOException {
        // Mybatis 설정 파일을 로딩할 때 사용할 입력 스트림 준비
        logger.info("[Appconfig.java] :: constructor called");
    }

//    application.yml로 처리
//    @Bean
//    public ViewResolver viewResolver() {
//        InternalResourceViewResolver vr = new InternalResourceViewResolver( //
//                "/WEB-INF/templates/", // prefix
//                ".html" // suffix
//        );
//        vr.setOrder(1);
//        AppConfig.logger.info("[viewResolver] : " + vr.toString());
//        return vr;
//    }

//    @Bean
//    public MultipartResolver multipartResolver() {
//        CommonsMultipartResolver mr = new CommonsMultipartResolver();
//        mr.setMaxUploadSize(100000000);
//        mr.setMaxInMemorySize(20000000);
//        mr.setMaxUploadSizePerFile(100000000); // 업로드 파일 용량 95.4MB로 수정
//        return mr;
//    }
}
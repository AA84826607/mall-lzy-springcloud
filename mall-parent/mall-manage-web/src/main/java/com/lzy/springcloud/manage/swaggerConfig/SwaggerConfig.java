package com.lzy.springcloud.manage.swaggerConfig;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2//开启swagger2
@ConditionalOnProperty(prefix = "swagger", name = "open", havingValue = "true")
public class SwaggerConfig {

    //配置swagger的docket的bean实例
    @Bean
    public Docket createRestApi(Environment environment) {
        //设置要显示的swagger环境
        Profiles profiles=Profiles.of("dev","test");
        //通过environment.acceptsProfiles判断是否处在自己设定的环境当中
        boolean flag = environment.acceptsProfiles(profiles);
        return new Docket(DocumentationType.SWAGGER_2).groupName("mall-manage-web")
                .apiInfo(apiInfo())
                .select()
                //这里采用包含注解的方式来确定要显示的接口(建议使用这种)
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build();

    }
//    @Bean
//    public Docket createRestApi1(Environment environment) {
//        //设置要显示的swagger环境
//        Profiles profiles=Profiles.of("dev","test");
//        //通过environment.acceptsProfiles判断是否处在自己设定的环境当中
//        boolean flag = environment.acceptsProfiles(profiles);
//        return new Docket(DocumentationType.SWAGGER_2).groupName("lzy2")
//                .apiInfo(apiInfo1())
//                ;
//
//    }


    private ApiInfo apiInfo() {
        //作者信息
        Contact contact = new Contact("lzy", "http://localhost:8080/index", "84826607@qq.com");
        return new ApiInfo(
                "lzy的swaggeraAPI文档",
                "即使",
                "v1.0",
                "http://localhost:8080/index",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList()
        );
    }


//    private ApiInfo apiInfo1() {
//        //作者信息
//        Contact contact = new Contact("lzy", "http://localhost:8989/hello", "84826607@qq.com");
//        return new ApiInfo(
//                "lzy的swaggeraAPI文档",
//                "即使",
//                "v1.0",
//                "http://localhost:8989/hello",
//                contact,
//                "Apache 2.0",
//                "http://www.apache.org/licenses/LICENSE-2.0",
//                new ArrayList()
//        );
//    }



}
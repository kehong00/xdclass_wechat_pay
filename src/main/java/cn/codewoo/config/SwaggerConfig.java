package cn.codewoo.config;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName SwaggerConfig
 * @Description TODO
 * @Author kehong
 * @Date 2020/11/13 8:13 下午
 * @Version 1.0
 **/
@Configuration
@EnableSwagger2
@ConfigurationProperties(prefix = "swagger")
public class SwaggerConfig {
    private boolean enable;
    @Bean
    public ApiInfo apiInfo(){
        return new ApiInfo("微信支付项目", "微信支付实战", "1.0",
                "http://www.codewoo.cn",
                new Contact("kehong", "http://www.codewoo.cn","wu@kehong.xyz"),
                "Apache 2.0", "http://www.codewoo.cn", new ArrayList());
    }

    @Bean
    @Autowired
    public Docket docket(ApiInfo apiInfo){
        List<Parameter> parameterList = new ArrayList<>();
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        //全局参数，token用户凭证
        Parameter headerToken = parameterBuilder.required(false)
                .parameterType("header")
                .description("用户凭证")
                .modelRef(new ModelRef("string"))
                .name("token")
                .build();
        parameterList.add(headerToken);
        return new Docket(DocumentationType.SWAGGER_2).enable(true)
                .apiInfo(apiInfo)
                .globalOperationParameters(parameterList)
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.codewoo.controller"))
                .build();
    }
}

package com.github.caoyeung.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;

import com.google.common.base.Predicates;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2配置类
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	/**
	 * 可以定义多个组，比如本类中定义把test和demo区分开了 （访问页面就可以看到效果了）
	 */
	@Bean
	public Docket createInstanceDocket() {
		Docket docket = new Docket(DocumentationType.SWAGGER_2)
				.select().apis(RequestHandlerSelectors.basePackage("com.github.caoyeung"))
//		        .apis(RequestHandlerSelectors.basePackage("com.github.caoyeung"))
		        .paths(PathSelectors.any())
				.build()
				.apiInfo(createInstanceApiInfo());
		return docket;
	}
	private ApiInfo createInstanceApiInfo() {
		return new ApiInfoBuilder().title("XX项目测试")
				.description("用于XXX模块1")
				.version("1.0")
				.termsOfServiceUrl("http://www.boco.com.cn/")
				.contact(new Contact("XXX小组", "http://www.boco.com.cn/", "cyang198906@163.com"))
				.license("XX许可")
				.licenseUrl("http://www.boco.com.cn/")
				.build();
	}
	

	/*@Bean
	public Docket demoApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("server2").genericModelSubstitutes(DeferredResult.class)
				// .genericModelSubstitutes(ResponseEntity.class)
				.useDefaultResponseMessages(false).forCodeGeneration(false).pathMapping("/").select()
				.paths(Predicates.or(PathSelectors.regex("/demo/.*")))// 过滤的接口
				.build().apiInfo(demoApiInfo());
	}

	private ApiInfo demoApiInfo() {
		ApiInfo apiInfo = new ApiInfo("大标题",// 大标题
				"小标题",// 小标题
				"版本",// 版本
				"作者", "CaoYeung",// 作者
				"链接显示文字",// 链接显示文字
				"网站链接"// 网站链接
		);

		return apiInfo;
	}*/
}

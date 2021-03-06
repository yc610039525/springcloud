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
 * Swagger配置类:
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
				.description("用于XXX模块2")
				.version("1.0")
				.termsOfServiceUrl("http://www.boco.com.cn/")
				.contact(new Contact("XXX小组", "http://www.boco.com.cn/", "cyang198906@163.com"))
				.license("XX许可")
				.licenseUrl("http://www.boco.com.cn/")
				.build();
	}

	/**
	 * 可以定义多个组，比如本类中定义把test和demo区分开了 （访问页面就可以看到效果了）
	 */
	/*
	@Bean
	public Docket testApi() {
		Docket docket = new Docket(DocumentationType.SWAGGER_2).groupName("server1")
				.genericModelSubstitutes(DeferredResult.class).useDefaultResponseMessages(false)
				.forCodeGeneration(true).pathMapping("/")// base，最终调用接口后会和paths拼接在一起
				.select().paths(Predicates.or(PathSelectors.regex("/api/.*")))// 过滤的接口
				.build().apiInfo(testApiInfo());
		return docket;
	}

	private ApiInfo testApiInfo() {
		ApiInfo apiInfo = new ApiInfo("上海云服务改造项目",// 大标题
				"调度系统TNMS-SERVICE测试",// 小标题
				"1.0",// 版本
				"##http://www.boco.com.cn/",
				"综合资源部",// 作者
				"亿阳信通成都",// 链接显示文字
				"##http://www.boco.com.cn/"// 网站链接
		);

		return apiInfo;
	}
	

	@Bean
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

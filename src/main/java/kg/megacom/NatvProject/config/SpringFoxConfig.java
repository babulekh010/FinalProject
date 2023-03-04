package kg.megacom.NatvProject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SpringFoxConfig {
    public static final String BALANCE = "Баланс";
    public static final String BANNER = "Баннер";
    public static final String PAYMENT = "Оплата заказов";
    public static final String CHANNEL = "Канал";
    public static final String CLIENT = "Клиент";
    public static final String ORDER = "Подача заявки на рекламу";


    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("kg.megacom.NatvProject"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .tags(new Tag(CLIENT,""))
                .tags(new Tag(CHANNEL, ""))
                .tags(new Tag(BALANCE,""))
                .tags(new Tag(BANNER,""))
                .tags(new Tag(PAYMENT,""))
                .tags(new Tag(ORDER,""));
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("NATV PROJECT")
                .description("API Documentation")
                .version("1.0.0")
                .contact(new Contact("Umarova Elina", "https://megacom.kg/", "babulekh010@gmail.com"))
                .build();
    }
}

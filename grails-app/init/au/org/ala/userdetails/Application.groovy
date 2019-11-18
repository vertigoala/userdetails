package au.org.ala.userdetails

import au.org.ala.recaptcha.RecaptchaClient
import grails.boot.GrailsApp
import grails.boot.config.GrailsAutoConfiguration
import groovy.util.logging.Slf4j
import okhttp3.OkHttpClient
import org.springframework.boot.actuate.health.DataSourceHealthIndicator
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.session.data.redis.config.ConfigureRedisAction
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

import javax.sql.DataSource

@Slf4j
class Application extends GrailsAutoConfiguration {
    static void main(String[] args) {
        GrailsApp.run(Application, args)
    }

    @ConditionalOnProperty('spring.session.disable-redis-config-action')
    @Bean
    ConfigureRedisAction configureRedisAction() {
        ConfigureRedisAction.NO_OP
    }

    @Bean
    DataSourceHealthIndicator dataSourceHealthIndicator(DataSource dataSource) {
        new DataSourceHealthIndicator(dataSource)
    }

    @Bean
    RecaptchaClient recaptchaClient() {
        def baseUrl = grailsApplication.config.getProperty('recaptcha.baseUrl', 'https://www.google.com/recaptcha/api/')
        return new Retrofit.Builder().baseUrl(baseUrl).client(new OkHttpClient()).addConverterFactory(MoshiConverterFactory.create()).build().create(RecaptchaClient)
    }

}
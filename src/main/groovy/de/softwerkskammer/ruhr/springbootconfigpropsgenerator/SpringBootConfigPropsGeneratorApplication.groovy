package de.softwerkskammer.ruhr.springbootconfigpropsgenerator

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties

@SpringBootApplication
@EnableConfigurationProperties(
        [SoftwerkskammerProps, OtherProps]
)
class SpringBootConfigPropsGeneratorApplication {

	static void main(String[] args) {
		SpringApplication.run SpringBootConfigPropsGeneratorApplication, args
	}
}

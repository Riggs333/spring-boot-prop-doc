package de.softwerkskammer.ruhr.springbootconfigpropsgenerator

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.configurationmetadata.ConfigurationMetadataGroup
import org.springframework.boot.configurationmetadata.ConfigurationMetadataProperty
import org.springframework.boot.configurationmetadata.ConfigurationMetadataRepository
import org.springframework.boot.configurationmetadata.ConfigurationMetadataRepositoryJsonBuilder
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.Resource
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner)
@SpringBootTest
class SpringBootConfigPropsGeneratorApplicationTests {

    @Autowired
    SoftwerkskammerProps softwerkskammerProps

	@Test
	void contextLoads() {
        assert softwerkskammerProps.getTest() == "defaultTest"
	}

    @Test
    void findsMetaDataInClassPath() {
        def resources = new PathMatchingResourcePatternResolver()
                .getResources("classpath*:META-INF/*.json")
        ConfigurationMetadataRepositoryJsonBuilder builder = ConfigurationMetadataRepositoryJsonBuilder.create();
        for (Resource resource : resources) {
            resource.getInputStream().with { input ->
                builder.withJsonResource(input);
            }
        }
        ConfigurationMetadataRepository repository = builder.build();

        def group = repository.allGroups['de.softwerkskammer']

        def properties = repository.allProperties
        assert properties.keySet().contains("de.softwerkskammer.test")

    }

}

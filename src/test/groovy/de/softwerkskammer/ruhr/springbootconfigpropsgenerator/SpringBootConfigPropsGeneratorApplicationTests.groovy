package de.softwerkskammer.ruhr.springbootconfigpropsgenerator

import org.asciidoctor.Asciidoctor
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.configurationmetadata.ConfigurationMetadataRepository
import org.springframework.boot.configurationmetadata.ConfigurationMetadataRepositoryJsonBuilder
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.Resource
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import org.springframework.test.context.junit4.SpringRunner
import spock.lang.Specification

@RunWith(SpringRunner)
@SpringBootTest
class SpringBootConfigPropsGeneratorApplicationTests extends Specification {

    @Autowired
    SoftwerkskammerProps softwerkskammerProps

    def "generate asciidoc from custom configuration properties class"() {

        given:
        def resources = new PathMatchingResourcePatternResolver()
                .getResources("classpath*:META-INF/spring-configuration-metadata.json")
        ConfigurationMetadataRepositoryJsonBuilder builder = ConfigurationMetadataRepositoryJsonBuilder.create();
        for (Resource resource : resources) {
            resource.getInputStream().with { input ->
                builder.withJsonResource(input);
            }
        }
        ConfigurationMetadataRepository repository = builder.build();

        and:
        def swkProperties = repository.allGroups['de.softwerkskammer']
        def printer = new Printer()
        def tableAsAsciiDoc = printer.generateTableForGroup(swkProperties)
        def factory = Asciidoctor.Factory.create()

        when:
        def html = factory.convert(tableAsAsciiDoc, [:])

        expect:
        html == """
       <table class="tableblock frame-all grid-all stretch">
       <colgroup>
       <col style="width: 25%;">
       <col style="width: 25%;">
       <col style="width: 25%;">
       <col style="width: 25%;">
       </colgroup>
       <thead>
       <tr>
       <th class="tableblock halign-left valign-top">name</th>
       <th class="tableblock halign-left valign-top">type</th>
       <th class="tableblock halign-left valign-top">default value</th>
       <th class="tableblock halign-left valign-top">description</th>
       </tr>
       </thead>
       <tbody>
       <tr>
       <td class="tableblock halign-left valign-top"><p class="tableblock">de.softwerkskammer.test</p></td>
       <td class="tableblock halign-left valign-top"><p class="tableblock">java.lang.String</p></td>
       <td class="tableblock halign-left valign-top"><p class="tableblock">defaultTest</p></td>
       <td class="tableblock halign-left valign-top"><p class="tableblock">Description for test property.</p></td>
       </tr>
       </tbody>
       </table>
""".stripIndent().trim()
    }

}

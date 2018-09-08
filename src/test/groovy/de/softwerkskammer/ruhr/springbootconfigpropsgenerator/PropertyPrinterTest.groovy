package de.softwerkskammer.ruhr.springbootconfigpropsgenerator


import org.springframework.boot.configurationmetadata.ConfigurationMetadataGroup
import org.springframework.boot.configurationmetadata.ConfigurationMetadataProperty
import spock.lang.Specification

class PropertyPrinterTest extends  Specification {

    private Printer printer

    def setup() {
        printer = new Printer()
    }

    def "empty group generates empty message"() {
        given:
        def metadataGroup = new ConfigurationMetadataGroup("de.softwerkskammer")

        expect:
        this.printer.generateTableForGroup(metadataGroup) == 'no properties found'

    }

    def "group with one entry generates table for that entry"() {
        given:
        def metaDatagroup = new ConfigurationMetadataGroup("de.softwerkskammer")
        def property = new ConfigurationMetadataProperty(name: "myTestProperty", type: "String", description: "It's" +
                " a test!", defaultValue: "Default Value!")
        metaDatagroup.properties["myTestProperty"] = property

        expect:
        printer.generateTableForGroup metaDatagroup contains "| de.softwerkskammer.myTestProperty | String | Default Value! | " +
                "It's a test!"
    }

    def "header row with data lines"() {

        given:
        def property1 = new ConfigurationMetadataProperty(name: "myTestProperty", type: "String", description: "It's" +
                " a test!")
        def property2 = new ConfigurationMetadataProperty(name: "myOtherProperty", type: "java.time.LocalDate", description:
                "It's a trap!")

        def group = new ConfigurationMetadataGroup()
        group.properties << [(property1.name):property1, (property2.name):property2]

        expect:
        printer.generateTableForGroup(group) ==
                this.class.getResource("/expected_output.adoc").text.stripIndent().trim()
    }


}

package de.softwerkskammer.ruhr.springbootconfigpropsgenerator


import org.springframework.boot.configurationmetadata.ConfigurationMetadataProperty
import spock.lang.Specification

class PropertyPrinterTest extends  Specification {

    private Printer printer

    def setup() {
        printer = new Printer()
    }

    def "empty repo generates empty message"() {
        given:
        def metaDataProperties = []

        expect:
        this.printer.generate(metaDataProperties) == 'no properties found'

    }

    def "repo with one entry generates table line for that entry"() {
        given:
        def metaDataProperties = [new ConfigurationMetadataProperty(name: "myTestProperty", type: "String", description: "It's" +
                " a test!")]

        expect:
        printer.generate metaDataProperties contains "| myTestProperty | String | It's a test!"
    }


    def "repo with two entries generates two table lines for that entries in alphabetical order"() {
        given:
        def property1 = new ConfigurationMetadataProperty(name: "myTestProperty", type: "String", description: "It's" +
                " a test!")
        def property2 = new ConfigurationMetadataProperty(name: "myOtherProperty", type: "java.time.LocalDate", description:
                "It's" +
                        " a trap!")

        def metaDataProperties = [property1, property2]

        expect:
        printer.generate (metaDataProperties) ==
                """
                | myOtherProperty | java.time.LocalDate | It's a trap!
                | myTestProperty | String | It's a test!
                """.stripIndent().trim()
    }

    def "header row contains name, type and description"() {

        expect:
        printer.generateTableAroundRows([]) ==
                """
                [options="header"]
                |=====
                | name | type | description
                |=====
                """.stripIndent().trim()
    }

}

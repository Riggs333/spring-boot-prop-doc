package de.softwerkskammer.ruhr.springbootconfigpropsgenerator

class Printer {


    String generate(Collection metaDataProperties) {
        if (metaDataProperties) {
            return metaDataProperties.collect { prop -> "| $prop.name | $prop.type | $prop.description" }.sort().join("\n")
        }
        "no properties found"
    }

    String generateTableAroundRows(Collection metaDataProperties) {
        """
                [options="header"]
                |=====
                | name | type | description
                |=====
                """.stripIndent().trim()
    }
}

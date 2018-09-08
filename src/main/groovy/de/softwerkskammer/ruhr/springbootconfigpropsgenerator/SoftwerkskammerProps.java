package de.softwerkskammer.ruhr.springbootconfigpropsgenerator;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "de.softwerkskammer")
public class SoftwerkskammerProps {
    /**
     * Description for test property.
     */
    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    /**
     * Description for test property.
     */
    private String test = "defaultTest";
}

package de.softwerkskammer.ruhr.springbootconfigpropsgenerator;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "de")
public class OtherProps {

    private Map<String, String> softwerkskammer;

    public Map<String, String> getSoftwerkskammer() {
        return softwerkskammer;
    }

    public void setSoftwerkskammer(Map<String, String> softwerkskammer) {
        this.softwerkskammer = softwerkskammer;
    }
}

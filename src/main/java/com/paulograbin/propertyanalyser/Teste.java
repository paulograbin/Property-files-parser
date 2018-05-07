package com.paulograbin.propertyanalyser;
//
import java.util.function.Consumer;
//
//import static org.springframework.util.StringUtils.hasText;
//
public class Teste implements Consumer {
    /**
     * Performs this operation on the given argument.
     *
     * @param o the input argument
     */
    @Override
    public void accept(Object o) {

    }
//
//    /**
//     * Performs this operation on the given argument.
//     *
//     * @param o the input argument
//     */
//    @Override
//    public void accept(Object o) {
//        if (hasText(line)) {
//            try {
//                final Property property = extractPropertyFromTextLine(line);
//
//                if (propertyAlreadyProcessed(property)) {
//                    appendNewValue(property);
//
//                } else {
//                    addPropertyForTheFirstTime(property);
//                }
//            } catch (RuntimeException e) {
//                System.err.println("Error trying to extract property from line " + line + ". Are you sure this is a property?");
//            }
//        }
//    }
//    }
//
//
//    private void appendNewValue(Property property) {
//        final Property currentProp = propertiesDictionary.get(property.getName());
//
//        currentProp.addEnvironmentValue(environmentName, property.getValue());
//    }
//
//    private boolean propertyAlreadyProcessed(Property property) {
//        return propertiesDictionary.containsKey(property.getName());
//    }
//
//
//    private void addPropertyForTheFirstTime(Property property) {
//        propertiesDictionary.put(property.getName(), property);
//    }
}

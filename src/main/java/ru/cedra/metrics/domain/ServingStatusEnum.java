//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.05.21 at 11:21:27 PM SAMT 
//


package ru.cedra.metrics.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ServingStatusEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ServingStatusEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ELIGIBLE"/>
 *     &lt;enumeration value="RARELY_SERVED"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ServingStatusEnum")
@XmlEnum
public enum ServingStatusEnum {

    ELIGIBLE,
    RARELY_SERVED;

    public String value() {
        return name();
    }

    public static ServingStatusEnum fromValue(String v) {
        return valueOf(v);
    }

}

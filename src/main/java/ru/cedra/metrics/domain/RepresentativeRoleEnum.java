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
 * <p>Java class for RepresentativeRoleEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="RepresentativeRoleEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CHIEF"/>
 *     &lt;enumeration value="DELEGATE"/>
 *     &lt;enumeration value="LIMITED"/>
 *     &lt;enumeration value="UNKNOWN"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "RepresentativeRoleEnum")
@XmlEnum
public enum RepresentativeRoleEnum {

    CHIEF,
    DELEGATE,
    LIMITED,
    UNKNOWN;

    public String value() {
        return name();
    }

    public static RepresentativeRoleEnum fromValue(String v) {
        return valueOf(v);
    }

}
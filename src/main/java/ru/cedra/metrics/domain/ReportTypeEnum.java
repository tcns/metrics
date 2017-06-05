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
 * <p>Java class for ReportTypeEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ReportTypeEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ACCOUNT_PERFORMANCE_REPORT"/>
 *     &lt;enumeration value="ADGROUP_PERFORMANCE_REPORT"/>
 *     &lt;enumeration value="AD_PERFORMANCE_REPORT"/>
 *     &lt;enumeration value="CAMPAIGN_PERFORMANCE_REPORT"/>
 *     &lt;enumeration value="CRITERIA_PERFORMANCE_REPORT"/>
 *     &lt;enumeration value="CUSTOM_REPORT"/>
 *     &lt;enumeration value="SEARCH_QUERY_PERFORMANCE_REPORT"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ReportTypeEnum", namespace = "http://api.direct.yandex.com/v5/reports")
@XmlEnum
public enum ReportTypeEnum {

    ACCOUNT_PERFORMANCE_REPORT,
    ADGROUP_PERFORMANCE_REPORT,
    AD_PERFORMANCE_REPORT,
    CAMPAIGN_PERFORMANCE_REPORT,
    CRITERIA_PERFORMANCE_REPORT,
    CUSTOM_REPORT,
    SEARCH_QUERY_PERFORMANCE_REPORT;

    public String value() {
        return name();
    }

    public static ReportTypeEnum fromValue(String v) {
        return valueOf(v);
    }

}

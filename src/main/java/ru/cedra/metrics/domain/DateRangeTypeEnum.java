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
 * <p>Java class for DateRangeTypeEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="DateRangeTypeEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ALL_TIME"/>
 *     &lt;enumeration value="AUTO"/>
 *     &lt;enumeration value="CUSTOM_DATE"/>
 *     &lt;enumeration value="LAST_14_DAYS"/>
 *     &lt;enumeration value="LAST_30_DAYS"/>
 *     &lt;enumeration value="LAST_365_DAYS"/>
 *     &lt;enumeration value="LAST_3_DAYS"/>
 *     &lt;enumeration value="LAST_5_DAYS"/>
 *     &lt;enumeration value="LAST_7_DAYS"/>
 *     &lt;enumeration value="LAST_90_DAYS"/>
 *     &lt;enumeration value="LAST_BUSINESS_WEEK"/>
 *     &lt;enumeration value="LAST_MONTH"/>
 *     &lt;enumeration value="LAST_WEEK"/>
 *     &lt;enumeration value="LAST_WEEK_SUN_SAT"/>
 *     &lt;enumeration value="THIS_MONTH"/>
 *     &lt;enumeration value="THIS_WEEK_MON_TODAY"/>
 *     &lt;enumeration value="THIS_WEEK_SUN_TODAY"/>
 *     &lt;enumeration value="TODAY"/>
 *     &lt;enumeration value="YESTERDAY"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "DateRangeTypeEnum", namespace = "http://api.direct.yandex.com/v5/reports")
@XmlEnum
public enum DateRangeTypeEnum {

    ALL_TIME,
    AUTO,
    CUSTOM_DATE,
    LAST_14_DAYS,
    LAST_30_DAYS,
    LAST_365_DAYS,
    LAST_3_DAYS,
    LAST_5_DAYS,
    LAST_7_DAYS,
    LAST_90_DAYS,
    LAST_BUSINESS_WEEK,
    LAST_MONTH,
    LAST_WEEK,
    LAST_WEEK_SUN_SAT,
    THIS_MONTH,
    THIS_WEEK_MON_TODAY,
    THIS_WEEK_SUN_TODAY,
    TODAY,
    YESTERDAY;

    public String value() {
        return name();
    }

    public static DateRangeTypeEnum fromValue(String v) {
        return valueOf(v);
    }

}

//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.05.21 at 11:21:27 PM SAMT 
//


package ru.cedra.metrics.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrderBy complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrderBy">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Field" type="{http://api.direct.yandex.com/v5/reports}FieldEnum"/>
 *         &lt;element name="SortOrder" type="{http://api.direct.yandex.com/v5/general}SortOrderEnum" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderBy", namespace = "http://api.direct.yandex.com/v5/reports", propOrder = {
    "field",
    "sortOrder"
})
public class OrderBy {

    @XmlElement(name = "Field", required = true)
    @XmlSchemaType(name = "string")
    protected FieldEnum field;
    @XmlElement(name = "SortOrder")
    @XmlSchemaType(name = "string")
    protected SortOrderEnum sortOrder;

    /**
     * Gets the value of the field property.
     * 
     * @return
     *     possible object is
     *     {@link FieldEnum }
     *     
     */
    public FieldEnum getField() {
        return field;
    }

    /**
     * Sets the value of the field property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldEnum }
     *     
     */
    public void setField(FieldEnum value) {
        this.field = value;
    }

    /**
     * Gets the value of the sortOrder property.
     * 
     * @return
     *     possible object is
     *     {@link SortOrderEnum }
     *     
     */
    public SortOrderEnum getSortOrder() {
        return sortOrder;
    }

    /**
     * Sets the value of the sortOrder property.
     * 
     * @param value
     *     allowed object is
     *     {@link SortOrderEnum }
     *     
     */
    public void setSortOrder(SortOrderEnum value) {
        this.sortOrder = value;
    }

}

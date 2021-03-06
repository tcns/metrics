//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2017.05.21 at 11:21:27 PM SAMT
//


package ru.cedra.metrics.domain;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FilterItem complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="FilterItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Field" type="{http://api.direct.yandex.com/v5/reports}FieldEnum"/>
 *         &lt;element name="Operator" type="{http://api.direct.yandex.com/v5/reports}FilterOperatorEnum"/>
 *         &lt;element name="Values" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FilterItem", namespace = "http://api.direct.yandex.com/v5/reports", propOrder = {
    "field",
    "operator",
    "values"
})
public class FilterItem {

    @XmlElement(name = "Field", required = true)
    @XmlSchemaType(name = "string")
    protected FieldEnum field;
    @XmlElement(name = "Operator", required = true)
    @XmlSchemaType(name = "string")
    protected FilterOperatorEnum operator;
    @XmlElement(name = "Values", required = true)
    protected List<String> values;

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
     * Gets the value of the operator property.
     *
     * @return
     *     possible object is
     *     {@link FilterOperatorEnum }
     *
     */
    public FilterOperatorEnum getOperator() {
        return operator;
    }

    /**
     * Sets the value of the operator property.
     *
     * @param value
     *     allowed object is
     *     {@link FilterOperatorEnum }
     *
     */
    public void setOperator(FilterOperatorEnum value) {
        this.operator = value;
    }

    /**
     * Gets the value of the values property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the values property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getValues().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     *
     *
     */
    public List<String> getValues() {
        if (values == null) {
            values = new ArrayList<String>();
        }
        return this.values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }
}

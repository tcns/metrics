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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SelectionCriteria complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="SelectionCriteria">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DateFrom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DateTo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Filter" type="{http://api.direct.yandex.com/v5/reports}FilterItem" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SelectionCriteria", namespace = "http://api.direct.yandex.com/v5/reports", propOrder = {
    "dateFrom",
    "dateTo",
    "filter"
})
public class SelectionCriteria {

    @XmlElement(name = "DateFrom")
    protected String dateFrom;
    @XmlElement(name = "DateTo")
    protected String dateTo;
    @XmlElement(name = "Filter")
    protected List<FilterItem> filter;

    /**
     * Gets the value of the dateFrom property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getDateFrom() {
        return dateFrom;
    }

    /**
     * Sets the value of the dateFrom property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setDateFrom(String value) {
        this.dateFrom = value;
    }

    /**
     * Gets the value of the dateTo property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getDateTo() {
        return dateTo;
    }

    /**
     * Sets the value of the dateTo property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setDateTo(String value) {
        this.dateTo = value;
    }

    /**
     * Gets the value of the filter property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the filter property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFilter().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FilterItem }
     *
     *
     */
    public List<FilterItem> getFilter() {
        if (filter == null) {
            filter = new ArrayList<FilterItem>();
        }
        return this.filter;
    }

    public void setFilter(List<FilterItem> filter) {
        this.filter = filter;
    }
}

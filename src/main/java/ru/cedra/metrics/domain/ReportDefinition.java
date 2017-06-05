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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SelectionCriteria" type="{http://api.direct.yandex.com/v5/reports}SelectionCriteria"/>
 *         &lt;element name="FieldNames" type="{http://api.direct.yandex.com/v5/reports}FieldEnum" maxOccurs="unbounded"/>
 *         &lt;element name="Page" type="{http://api.direct.yandex.com/v5/reports}Page" minOccurs="0"/>
 *         &lt;element name="OrderBy" type="{http://api.direct.yandex.com/v5/reports}OrderBy" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ReportName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ReportType" type="{http://api.direct.yandex.com/v5/reports}ReportTypeEnum"/>
 *         &lt;element name="DateRangeType" type="{http://api.direct.yandex.com/v5/reports}DateRangeTypeEnum"/>
 *         &lt;element name="Format" type="{http://api.direct.yandex.com/v5/reports}FormatEnum"/>
 *         &lt;element name="IncludeVAT" type="{http://api.direct.yandex.com/v5/general}YesNoEnum"/>
 *         &lt;element name="IncludeDiscount" type="{http://api.direct.yandex.com/v5/general}YesNoEnum"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "selectionCriteria",
    "fieldNames",
    "page",
    "orderBy",
    "reportName",
    "reportType",
    "dateRangeType",
    "format",
    "includeVAT",
    "includeDiscount"
})
@XmlRootElement(name = "ReportDefinition", namespace = "http://api.direct.yandex.com/v5/reports")
public class ReportDefinition {

    @XmlElement(name = "SelectionCriteria", namespace = "http://api.direct.yandex.com/v5/reports", required = true)
    protected SelectionCriteria selectionCriteria;
    @XmlElement(name = "FieldNames", namespace = "http://api.direct.yandex.com/v5/reports", required = true)
    @XmlSchemaType(name = "string")
    protected List<FieldEnum> fieldNames;
    @XmlElement(name = "Page", namespace = "http://api.direct.yandex.com/v5/reports")
    protected Page page;
    @XmlElement(name = "OrderBy", namespace = "http://api.direct.yandex.com/v5/reports")
    protected List<OrderBy> orderBy;
    @XmlElement(name = "ReportName", namespace = "http://api.direct.yandex.com/v5/reports", required = true)
    protected String reportName;
    @XmlElement(name = "ReportType", namespace = "http://api.direct.yandex.com/v5/reports", required = true)
    @XmlSchemaType(name = "string")
    protected ReportTypeEnum reportType;
    @XmlElement(name = "DateRangeType", namespace = "http://api.direct.yandex.com/v5/reports", required = true)
    @XmlSchemaType(name = "string")
    protected DateRangeTypeEnum dateRangeType;
    @XmlElement(name = "Format", namespace = "http://api.direct.yandex.com/v5/reports", required = true)
    @XmlSchemaType(name = "string")
    protected FormatEnum format;
    @XmlElement(name = "IncludeVAT", namespace = "http://api.direct.yandex.com/v5/reports", required = true)
    @XmlSchemaType(name = "string")
    protected YesNoEnum includeVAT;
    @XmlElement(name = "IncludeDiscount", namespace = "http://api.direct.yandex.com/v5/reports", required = true)
    @XmlSchemaType(name = "string")
    protected YesNoEnum includeDiscount;

    /**
     * Gets the value of the selectionCriteria property.
     *
     * @return
     *     possible object is
     *     {@link SelectionCriteria }
     *
     */
    public SelectionCriteria getSelectionCriteria() {
        return selectionCriteria;
    }

    /**
     * Sets the value of the selectionCriteria property.
     *
     * @param value
     *     allowed object is
     *     {@link SelectionCriteria }
     *
     */
    public void setSelectionCriteria(SelectionCriteria value) {
        this.selectionCriteria = value;
    }

    /**
     * Gets the value of the fieldNames property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fieldNames property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFieldNames().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FieldEnum }
     *
     *
     */
    public List<FieldEnum> getFieldNames() {
        if (fieldNames == null) {
            fieldNames = new ArrayList<FieldEnum>();
        }
        return this.fieldNames;
    }

    /**
     * Gets the value of the page property.
     *
     * @return
     *     possible object is
     *     {@link Page }
     *
     */
    public Page getPage() {
        return page;
    }

    /**
     * Sets the value of the page property.
     *
     * @param value
     *     allowed object is
     *     {@link Page }
     *
     */
    public void setPage(Page value) {
        this.page = value;
    }

    /**
     * Gets the value of the orderBy property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the orderBy property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrderBy().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OrderBy }
     *
     *
     */
    public List<OrderBy> getOrderBy() {
        if (orderBy == null) {
            orderBy = new ArrayList<OrderBy>();
        }
        return this.orderBy;
    }

    /**
     * Gets the value of the reportName property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getReportName() {
        return reportName;
    }

    /**
     * Sets the value of the reportName property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setReportName(String value) {
        this.reportName = value;
    }

    /**
     * Gets the value of the reportType property.
     *
     * @return
     *     possible object is
     *     {@link ReportTypeEnum }
     *
     */
    public ReportTypeEnum getReportType() {
        return reportType;
    }

    /**
     * Sets the value of the reportType property.
     *
     * @param value
     *     allowed object is
     *     {@link ReportTypeEnum }
     *
     */
    public void setReportType(ReportTypeEnum value) {
        this.reportType = value;
    }

    /**
     * Gets the value of the dateRangeType property.
     *
     * @return
     *     possible object is
     *     {@link DateRangeTypeEnum }
     *
     */
    public DateRangeTypeEnum getDateRangeType() {
        return dateRangeType;
    }

    /**
     * Sets the value of the dateRangeType property.
     *
     * @param value
     *     allowed object is
     *     {@link DateRangeTypeEnum }
     *
     */
    public void setDateRangeType(DateRangeTypeEnum value) {
        this.dateRangeType = value;
    }

    /**
     * Gets the value of the format property.
     *
     * @return
     *     possible object is
     *     {@link FormatEnum }
     *
     */
    public FormatEnum getFormat() {
        return format;
    }

    /**
     * Sets the value of the format property.
     *
     * @param value
     *     allowed object is
     *     {@link FormatEnum }
     *
     */
    public void setFormat(FormatEnum value) {
        this.format = value;
    }

    /**
     * Gets the value of the includeVAT property.
     *
     * @return
     *     possible object is
     *     {@link YesNoEnum }
     *
     */
    public YesNoEnum getIncludeVAT() {
        return includeVAT;
    }

    /**
     * Sets the value of the includeVAT property.
     *
     * @param value
     *     allowed object is
     *     {@link YesNoEnum }
     *
     */
    public void setIncludeVAT(YesNoEnum value) {
        this.includeVAT = value;
    }

    /**
     * Gets the value of the includeDiscount property.
     *
     * @return
     *     possible object is
     *     {@link YesNoEnum }
     *
     */
    public YesNoEnum getIncludeDiscount() {
        return includeDiscount;
    }

    /**
     * Sets the value of the includeDiscount property.
     *
     * @param value
     *     allowed object is
     *     {@link YesNoEnum }
     *
     */
    public void setIncludeDiscount(YesNoEnum value) {
        this.includeDiscount = value;
    }

    public void setFieldNames(List<FieldEnum> fieldNames) {
        this.fieldNames = fieldNames;
    }

    public void setOrderBy(List<OrderBy> orderBy) {
        this.orderBy = orderBy;
    }
}

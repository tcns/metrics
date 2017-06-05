//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.05.21 at 11:21:27 PM SAMT 
//


package ru.cedra.metrics.domain;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ru.cedra.metrics.domain package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _FaultResponse_QNAME = new QName("http://api.direct.yandex.com/v5/general", "FaultResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ru.cedra.metrics.domain
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ReportDownloadError }
     * 
     */
    public ReportDownloadError createReportDownloadError() {
        return new ReportDownloadError();
    }

    /**
     * Create an instance of {@link ApiError }
     * 
     */
    public ApiError createApiError() {
        return new ApiError();
    }

    /**
     * Create an instance of {@link ReportDefinition }
     * 
     */
    public ReportDefinition createReportDefinition() {
        return new ReportDefinition();
    }

    /**
     * Create an instance of {@link SelectionCriteria }
     * 
     */
    public SelectionCriteria createSelectionCriteria() {
        return new SelectionCriteria();
    }

    /**
     * Create an instance of {@link Page }
     * 
     */
    public Page createPage() {
        return new Page();
    }

    /**
     * Create an instance of {@link OrderBy }
     * 
     */
    public OrderBy createOrderBy() {
        return new OrderBy();
    }

    /**
     * Create an instance of {@link FilterItem }
     * 
     */
    public FilterItem createFilterItem() {
        return new FilterItem();
    }

    /**
     * Create an instance of {@link ApiExceptionMessage }
     * 
     */
    public ApiExceptionMessage createApiExceptionMessage() {
        return new ApiExceptionMessage();
    }

    /**
     * Create an instance of {@link ArrayOfInteger }
     * 
     */
    public ArrayOfInteger createArrayOfInteger() {
        return new ArrayOfInteger();
    }

    /**
     * Create an instance of {@link ExtensionModeration }
     * 
     */
    public ExtensionModeration createExtensionModeration() {
        return new ExtensionModeration();
    }

    /**
     * Create an instance of {@link IdsCriteria }
     * 
     */
    public IdsCriteria createIdsCriteria() {
        return new IdsCriteria();
    }

    /**
     * Create an instance of {@link SetBidsActionResult }
     * 
     */
    public SetBidsActionResult createSetBidsActionResult() {
        return new SetBidsActionResult();
    }

    /**
     * Create an instance of {@link ExceptionNotification }
     * 
     */
    public ExceptionNotification createExceptionNotification() {
        return new ExceptionNotification();
    }

    /**
     * Create an instance of {@link ActionResultBase }
     * 
     */
    public ActionResultBase createActionResultBase() {
        return new ActionResultBase();
    }

    /**
     * Create an instance of {@link GetRequestGeneral }
     * 
     */
    public GetRequestGeneral createGetRequestGeneral() {
        return new GetRequestGeneral();
    }

    /**
     * Create an instance of {@link MultiIdsActionResult }
     * 
     */
    public MultiIdsActionResult createMultiIdsActionResult() {
        return new MultiIdsActionResult();
    }

    /**
     * Create an instance of {@link Statistics }
     * 
     */
    public Statistics createStatistics() {
        return new Statistics();
    }

    /**
     * Create an instance of {@link ActionResult }
     * 
     */
    public ActionResult createActionResult() {
        return new ActionResult();
    }

    /**
     * Create an instance of {@link ArrayOfString }
     * 
     */
    public ArrayOfString createArrayOfString() {
        return new ArrayOfString();
    }

    /**
     * Create an instance of {@link LimitOffset }
     * 
     */
    public LimitOffset createLimitOffset() {
        return new LimitOffset();
    }

    /**
     * Create an instance of {@link GetResponseGeneral }
     * 
     */
    public GetResponseGeneral createGetResponseGeneral() {
        return new GetResponseGeneral();
    }

    /**
     * Create an instance of {@link ArrayOfLong }
     * 
     */
    public ArrayOfLong createArrayOfLong() {
        return new ArrayOfLong();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ApiExceptionMessage }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.direct.yandex.com/v5/general", name = "FaultResponse")
    public JAXBElement<ApiExceptionMessage> createFaultResponse(ApiExceptionMessage value) {
        return new JAXBElement<ApiExceptionMessage>(_FaultResponse_QNAME, ApiExceptionMessage.class, null, value);
    }

}
<%@ include file="/init.jsp" %>

<portlet:actionURL name="saveProduct" var="saveProductURL">

</portlet:actionURL>

<h1>Create Product Details</h1>

<aui:form name="fm" action="${saveProductURL}">

<aui:input name="name"> </aui:input>
<aui:input name="category"> </aui:input>
<aui:input name="price"> </aui:input>
<aui:input name="quantity"> </aui:input>

<aui:button-row>
<aui:button cssClass="btn btn-primary" type="submit" />
</aui:button-row>

</aui:form>

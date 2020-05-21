<%@ page contentType="text/html; charset=euc-kr" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%-- /////////////////////// EL / JSTL �������� �ּ� ó�� ////////////////////////

<%@ page import="java.util.List" %>

<%@ page import="java.util.*"  %>
<%@ page import="com.model2.mvc.service.domain.*" %>
<%@ page import="com.model2.mvc.common.*" %>
<%@ page import="com.model2.mvc.common.util.CommonUtil" %>

<%
	List<Product> list= (List<Product>)request.getAttribute("list");
    Page resultPage=(Page)request.getAttribute("resultPage");
    
    Search search = (Search)request.getAttribute("search");
    //==> null �� ""(nullString)���� ����
	String searchCondition = CommonUtil.null2str(search.getSearchCondition());
    String searchKeyword = CommonUtil.null2str(search.getSearchKeyword());
%> --%>

<html>
<head>
<title>��ǰ �����ȸ</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
	// �˻� / page �ΰ��� ��� ��� Form ������ ���� JavaScrpt �̿�
	function fncGetUserList(currentPage){
		document.getElementById("currentPage").value = currentPage;
		document.detailForm.submit();
}
</script>

</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">

<%-- 
<form name="detailForm" action="<% if(request.getParameter("menu").equals("manage")){ %>
								/listProduct.do?menu=manage
								<% } else { %>
								/listProduct.do?menu=search
								<% } %>" method="post"> --%>
								
<form name="detailForm" action="<c:if test="${requestScope.param.menu == 'manage'}" >
 /listProduct.do?menu=manage
</c:if>
<c:if test="${requestScope.param.menu == 'search'}" >
 /listProduct.do?menu=search
</c:if>" method="post">
															
<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37">
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">
					<% if (request.getParameter("menu").equals("manage")) { %>
						��ǰ ����
					<% } else { %>
						��ǰ �����ȸ
					<% } %>	
					</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif" width="12" height="37"/>
		</td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
<%-- 	<td align="right">
			<select name="searchCondition" class="ct_input_g" style="width:80px">
				<option value="0" <%= (searchCondition.equals("0") ? "selected" : "")%>>��ǰ��ȣ</option>
				<option value="1" <%= (searchCondition.equals("1") ? "selected" : "")%>>��ǰ��</option>
				<option value="2" <%= (searchCondition.equals("2") ? "selected" : "")%>>����</option>
			</select>
			<input 	type="text" name="searchKeyword" value="<%= searchKeyword %>"  class="ct_input_g" 
							style="width:200px; height:20px" >
		</td> --%>
		
 		<td align="right">
			<select name="searchCondition" class="ct_input_g" style="width:80px">
				<option value="0" ${! empty search.searchCondition && search.searchCondition==0 ? "selected" : "" } >��ǰ��ȣ</option>
				<option value="1" ${! empty search.searchCondition && search.searchCondition==1 ? "selected" : "" } >��ǰ��</option>
				<option value="2" ${! empty search.searchCondition && search.searchCondition==2 ? "selected" : "" } >����</option>
			</select>
			<input 	type="text" name="searchKeyword" 
						 value="${! empty search.searchKeyword ? search.searchKeyword : ""}"  
						 class="ct_input_g" style="width:200px; height:20px" >
		</td> 
		<td align="right" width="70">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23"/>
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
						<a href="javascript:fncGetUserList('1');">�˻�</a>
					</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23"/>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<%-- 
	<tr>
		<td colspan="11" >��ü  <%= resultPage.getTotalCount() %> �Ǽ�, ���� <%=resultPage.getCurrentPage() %> ������</td>
	</tr>
	--%>

	<tr>
		<td colspan="11" >
		��ü  ${resultPage.totalCount } �Ǽ�, ���� ${resultPage.currentPage} ������</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">��ǰ��ȣ</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">��ǰ</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">��</td>		
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	
	<%--  /////////////////////// EL / JSTL �������� �ּ� ó�� //////////////////////
	
	<% 	
		for(int i=0; i<list.size(); i++) {
			Product product = list.get(i);
	%>
	<tr class="ct_list_pop">
		<td align="center"><%=i + 1+"����"%></td>
		<td></td>
		<td align="left">
		<% if(request.getParameter("menu").equals("manage")) { %>
			<a href="/getProduct.do?prodNo=<%=product.getProdNo() %>&menu=manage"><%= product.getProdNo() %></a>
		<% } else { %>
			<a href="/getProduct.do?prodNo=<%=product.getProdNo() %>&menu=search"><%= product.getProdNo() %></a>
		<% } %>	
		</td>
		<td></td>
		<td align="left"><%= product.getProdName() %></td>
		<td></td>
		 <td align="left"><%= product.getProdDetail() %>
		</td>	
		 <td align="left"><%= product.getManuDate() %>
		</td>
	</tr>
	<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
	</tr>
	<% } %> //////////////////////// EL / JSTL �������� �ּ� ó�� /////////////////////// --%>
	
	<c:set var="i" value="0" />
	<c:forEach var="product" items="${list}">
		<c:set var="i" value="${ i+1 }" />
		<tr class="ct_list_pop">
			<td align="center">${ i }</td>
			<td></td>
			<td align="left">
				<c:if test="${param.menu == 'manage' }">
				<a href="/getProduct.do?prodNo=${product.prodNo}&menu=manage">${product.prodNo}</a></td>
				</c:if>
				<c:if test="${param.menu == 'search' }">
				<a href="/getProduct.do?prodNo=${product.prodNo}&menu=search">${product.prodNo}</a></td>
				</c:if>
			</td>
			<td></td>
			<td align="left">${product.prodName}</td>
			<td></td>
			<td align="left">${product.prodDetail }</td>	
			<td></td>
			<td align="left">${product.manuDate }</td>
		</tr>
		<tr>
			<td colspan="11" bgcolor="D6D7D6" height="1"></td>
		</tr>
	</c:forEach>
</table>
<!-- PageNavigation Start... -->
<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top:10px;">
	<tr>
		<td align="center">
		   <input type="hidden" id="currentPage" name="currentPage" value=""/>
		   <%-- /////////////////////// EL / JSTL �������� �ּ� ó�� //////////////////////// 
			<% if( resultPage.getCurrentPage() <= resultPage.getPageUnit() ){ %>
					�� ����
			<% }else{ %>
					<a href="javascript:fncGetProductList('<%=resultPage.getCurrentPage()-1%>')">�� ����</a>
			<% } %>

			<%	for(int i=resultPage.getBeginUnitPage();i<= resultPage.getEndUnitPage() ;i++){	%>
					<a href="javascript:fncGetProductList('<%=i %>');"><%=i %></a>
			<% 	}  %>
	
			<% if( resultPage.getEndUnitPage() >= resultPage.getMaxPage() ){ %>
					���� ��
			<% }else{ %>
					<a href="javascript:fncGetProductList('<%=resultPage.getEndUnitPage()+1%>')">���� ��</a>
			<% } %>
			/////////////////////// EL / JSTL �������� �ּ� ó�� //////////////////////// --%>
	
		<jsp:include page="../common/pageNavigator.jsp"/>	
		
    	</td>
	</tr>
</table>
<!--  ������ Navigator �� -->
</form>
</div>

</body>
</html>

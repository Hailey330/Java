<%@ page import = "java.sql.*" contentType = "text/html;charset=EUC-KR"%>
<%
	Class.forName("oracle.jdbc.driver.OracleDriver");
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	Connection dbconn = DriverManager.getConnection(url, "c##madang", "c##madang");
	Statement stmt = dbconn.createStatement();
	//String custid = request.getParameter("custid");
	String name = request.getParameter("r_name");
	
	
	String sql = "";
	sql += " select "; 
	sql += " 	   customer.custid as cid, name, bookname, publisher ";
	sql += " from  ";
	sql += "       customer, orders, book ";
	sql += " where ";
	sql += "       customer.custid = orders.custid ";
	sql += "   and orders.bookid = book.bookid ";
	sql += "   and publisher in (select publisher ";
	sql += "                   		from  ";
	sql += "                        	 customer, orders, book ";
	sql += "                 	   where ";
	sql += "                      		 customer.custid = orders.custid ";
	sql += "                   		 and orders.bookid = book.bookid ";
	// sql += "                   		 and customer.custid = "+custid+" ) ";
	sql += "                   		 and name like  '"+name+"' ) ";
	sql += " and name not like '"+name+"' ";
%>
<html>

<head>
<meta http-equiv = "content-type" content = "text/html; charset=euc-kr">
<title>**Book VIEW**</title>
</head>

<body bgcolor = "white" text = "black" link = "blue" vlink = "purple" alink = "red">
<table border = "1" cellspacing = "0" width = "400" bordercolor = "#9AD2F7"
			bordercolordark = "white" bordercolorlight = "#B9E0FA">
	<tr>
		<td width = "300" height = "23">
			<p align = "center">
			<span style = "font-size : 9pt;">고 객 번 호</span></p>			
		</td>
			<td width = "300" height = "23">
			<p align = "center">
			<span style = "font-size : 9pt;">성 명</span></p>
		</td>
		<td width = "300" height = "23">
			<p align = "center">
			<span style = "font-size : 9pt;">책 이 름</span></p>
		</td>
		<td width = "300" height = "23">
			<p align = "center">
			<span style = "font-size : 9pt;">출 판 사</span></p>
		</td>
	</tr>
	<%
	ResultSet myResultSet = stmt.executeQuery(sql);
	if(myResultSet != null) {
		while(myResultSet.next()) {
	%>
	<tr>
		<td width = "300">
			<p><span style = "font-size : 9pt;"> 
			<%=myResultSet.getString("cid")%></span></p>
		</td>

		<td width = "300">
			<p><span style = "font-size : 9pt;"> 
			<%=myResultSet.getString("name")%></span></p>
		</td>

		<td width = "300">
			<p><span style = "font-size : 9pt;"> 
			<%=myResultSet.getString("bookname")%></span></p>
		</td>
		
		<td width = "300">
			<p><span style = "font-size : 9pt;"> 
			<%=myResultSet.getString("publisher")%></span></p>
		</td>
	</tr>
	<% 
		}
	}
	stmt.close();
	dbconn.close();
	%>
</table>
	<table cellpadding="0" cellsacing="0" width="400" height="23">
	<tr>
	<td width="150">
		<p align="right"><span style="font-size:9pt;">
		<a href="customerlist.jsp?">
		<font color="black">고 객 목 록</font></a></span></p>
	</td>
</tr>
</table>
</body>
</html>
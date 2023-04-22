<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

	<jsp:include page="./inc/header.jsp">
		<jsp:param name="title" value="Clint Homepage" />
	</jsp:include>
		
		<div id="contentContainer">
			<h2>Homepage Second Header</h2>
			<p>The current date and time is: <%= new java.util.Date() %></p>
			<p>Inceptos amet dictumst massa, varius rutrum justo maecenas pretium. Condimentum egestas mi volutpat, ultricies eu venenatis, himenaeos nulla curabitur curae euismod orci. Morbi lectus est sapien, aliquet tristique rhoncus, fusce primis odio erat dolor quisque mi. Per quisque amet pulvinar, commodo euismod viverra, dolor platea etiam hac taciti habitant accumsan sapien. Lacinia luctus, eros orci dolor. Aenean commodo duis, sapien augue, vehicula nullam quis curabitur varius et venenatis. Turpis posuere semper justo, donec nostra velit magna non. Placerat nec nullam, sed suscipit arcu condimentum. Scelerisque fusce viverra amet, consequat sociosqu sem, dictum bibendum accumsan nam iaculis luctus. Adipiscing turpis bibendum, ligula aliquam gravida nullam mauris.</p>
		</div>
	
		<jsp:include page="./inc/footer.jsp">
			<jsp:param name="footerTitle" value="Main Homepage" />
		</jsp:include>
		
	</div>

</body>
</html>
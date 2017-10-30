<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<link rel="stylesheet" type="text/css" href="css/table.css" />
<script type="text/javascript" src="js/tableFunctions.js"></script>

<form id="criteriaForm" method="post" action="contacts?page=1">
	<button class="filters button" onclick="document.getElementById('submitHidden').click()" type="button">
		<span style="color: #d7ebec;">Удалить фильтры</span>
	</button>
	<input type="hidden" name="filterDelete" id="filterDelete" value="1">
	<input type="submit" value="submit" id="submitHidden" style="display: none">
</form>

<table class="list">
	<input type="hidden" id="filters" value="${hasCriteria}">
	<input type="hidden" id="maxpage" value="${maxpage}">
			<thead>
				<tr>
					<td width="1" style="text-align: center;">
						<form action="contacts" method="post" id="form">
							<input type="hidden" name="command" value="processDeleteContacts"/>
							<input type="hidden" name="application" value="basic"/>
							<input id="submitDeleteHidden" type="submit" style="display: none"/>
						</form>
						<button id="deleteButton" onclick="submitDelete()" type="button" style="margin-bottom:2px"><i class="fa fa-trash fa-fw" aria-hidden="true"></i></button>
						<form action="sendMail" method="get" id="mailForm">
							<button type="button" id="mailButton" onclick="submitSend()"><i class="fa fa-envelope-o fa-fw" aria-hidden="true"></i></button>
							<input type="submit" id="submitSendInput" value="submit" style="display: none"/>
						</form>
					</td>
					<td class="center image-column ">Photo</td>
					<td class="left contact-column ">Name</td>
					<td class="left model-column ">Address</td>
					<td class="left qty-column ">E-mail</td>
					<td class="left qty-column ">Job place</td>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="curr" items="${rows}">
				<tr class="contact-row">
					<input type="hidden" name="id" value="${curr.contact.id}"/>
					<td style="text-align: center;">
						<input type="checkbox" name="selected[]"></td>
						<td class="center contact-image image-column ">
							<div class="image-wrapper">
								<img class="img-circle" src="${curr.contact.photo.relativePath}">
							</div>
						</td>
					<td class="left contact-name contact-column"><a
							href="${pageContext.request.contextPath}/edit?id=${curr.contact.id}">${curr.contact.firstName}
							${curr.contact.lastName} ${curr.contact.patronymic}</a></td>
						<td class="left contact-model model-column ">${curr.contact.state} ${curr.contact.city}
								${curr.contact.postalCode} ${curr.contact.street}
						${curr.contact.houseNumber}</td>
						<td class="left quantity qty-column ">${curr.contact.email}</td>
						<td class="status left status-column ">${curr.contact.jobPlace}</td>
				</tr>
			</c:forEach>
			</tbody>
</table>
<div style="text-align:center">
<form action="contacts">
	<button type="button" id="prevButton" onclick="goBack()"><i class="fa fa-angle-left fa-fw" aria-hidden="true"></i></button>
	<input type="submit" value="submit" id="backInput" style="display: none"/>
	<input type="hidden" name="page" id="backPage"/>
</form>
<form action="contacts">
	<button type="button" id="nextButton" onclick="goForward()"><i class="fa fa-angle-right fa-fw" aria-hidden="true"></i></button>
	<input type="submit" value="submit" id="forwardInput" style="display: none"/>
	<input type="hidden" name="page" id="forwardPage"/>
</form>
</div>

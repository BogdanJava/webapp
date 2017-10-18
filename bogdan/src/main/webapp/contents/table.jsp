<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/table.css" />

<table class="list">
			<thead>
				<tr>
					<td width="1" style="text-align: center;">
						<form action="contacts" method="post" id="form">
							<input type="hidden" name="command" value="processDeleteContacts"/>
							<input type="hidden" name="application" value="basic"/>
							<input id="submitDeleteHidden" type="submit" style="display: none"/>
						</form>
						<button id="deleteButton" onclick="submitDelete()" type="button">Удалить</button>
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
								<img src="${curr.contact.photo.relativePath}">
							</div>
						</td>
					<td class="left contact-name contact-column"><a
							href="${pageContext.request.contextPath}/edit?id=${curr.contact.id}">${curr.contact.first_name}
							${curr.contact.last_name} ${curr.contact.patronymic}</a></td>
						<td class="left contact-model model-column ">${curr.contact.state} ${curr.contact.city}
								${curr.contact.postal_code} ${curr.contact.street}
						${curr.contact.house_number}</td>
						<td class="left quantity qty-column ">${curr.contact.email}</td>
						<td class="status left status-column ">${curr.contact.job_place}</td>
				</tr>
			</c:forEach>
			</tbody>
</table>
<script type="text/javascript" src="js/tableFunctions.js"></script>

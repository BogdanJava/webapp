<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/table.css" />

<table class="list">
			<thead>
				<tr>
					<td width="1" style="text-align: center;"></td>
					<td class="center image-column ">Photo</td>
					<td class="left contact-column ">Name</td>
					<td class="left model-column ">Address</td>
					<td class="left qty-column ">E-mail</td>
					<td class="left qty-column ">Job place</td>
					<td class="right actions">Edit</td>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="curr" items="${rows}">
				<tr class="contact-row">
					<td style="text-align: center;">
						<input type="checkbox" id="${curr.contact.id}" name="selected[]" value="${curr.contact.id}"></td>
						<td class="center contact-image image-column ">
							<div class="image-wrapper">
								<img src="${curr.contact.photo.relativePath}">
							</div>
						</td>
					<td class="left contact-name contact-column"><a href="#">${curr.contact.firstName}
							${curr.contact.lastName} ${curr.contact.patronymic}</a></td>
						<td class="left contact-model model-column ">${curr.contact.state} ${curr.contact.city}
								${curr.contact.postalCode} ${curr.contact.street}
						${curr.contact.houseNumber}</td>
						<td class="left quantity qty-column ">${curr.contact.email}</td>
						<td class="status left status-column ">${curr.contact.jobPlace}</td>
						<td class="right nobr">
							<div class="nobr">
								<span class="edit-column "><a class="edit_link pe_action" href="${curr.contact.websiteURL}"
															  title="Edit"></a></span>
							</div>
						</td>
				</tr>
			</c:forEach>
			</tbody>
</table>
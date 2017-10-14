<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/table.css" />

<table class="list">
			<thead>
				<tr>
					<td width="1" style="text-align: center;"></td>
					<td class="left id-column ">ID</td>
					<td class="center image-column ">Photo</td>
					<td class="left contact-column ">Name</td>
					<td class="left model-column ">Address</td>
					<td class="left qty-column ">E-mail</td>
					<td class="right actions">Edit</td>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="curr" items="${rows.contact}">
				<tr class="contact-row">
					<td style="text-align: center;">
						<input type="checkbox" id="${curr.id}" name="selected[]" value="${curr.id}"></td>
						<td class="center contact-image image-column ">
							<div class="image-wrapper">
								<img src="${curr.photo.relativePath}">
							</div>
						</td>
					<td class="left contact-name contact-column"><a href="#">${curr.firstName}
							${curr.lastName} ${curr.patronymic}</a></td>
						<td class="left contact-model model-column ">${curr.state} ${curr.city} ${curr.postalCode} ${curr.street}
						${curr.houseNumber}</td>
						<td class="left quantity qty-column ">${curr.email}</td>
						<td class="status left status-column ">${curr.jobPlace}</td>
						<td class="right nobr">
							<div class="nobr">
								<span class="edit-column "><a class="edit_link pe_action" href="${curr.websiteURL}"
															  title="Edit"></a></span>
							</div>
						</td>
				</tr>
			</c:forEach>
			</tbody>
</table>
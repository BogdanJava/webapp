package com.bogdan.filter;

import com.bogdan.logic.LogicUtils;
import com.bogdan.pojo.AttachedFile;
import com.bogdan.pojo.Phone;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@WebFilter(filterName = "multiEncodeFilter")
public class MultipartEncodeFilter extends BaseEncodeFilter{
    private static final Logger LOGGER = Logger.getLogger("user_action_logger");
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        if (servletRequest.getContentType() != null && servletRequest.getContentType().toLowerCase().indexOf("multipart/form-data") > -1) {
            setParameters(servletRequest, servletResponse);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void setParameters(ServletRequest servletRequest, ServletResponse response) {
        List<String> fileRealNames = new ArrayList<>();
        List<AttachedFile> afList = new ArrayList<>();
        List<Phone> phoneList = new ArrayList<>();
        List<String> operatorCodes = new ArrayList<>();
        List<String> countryCodes = new ArrayList<>();
        List<String> numbers = new ArrayList<>();
        List<String> phoneTypes = new ArrayList<>();
        List<String> comments = new ArrayList<>();
        List<String> fileNames = new ArrayList<>();
        List<String> fileComments = new ArrayList<>();

        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(1024 * 1024);
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setSizeMax(1024 * 1024 * 10);
        try {
            List items = upload.parseRequest((HttpServletRequest) servletRequest);
            Iterator iterator = items.iterator();
            while (iterator.hasNext()) {
                FileItem item = (FileItem) iterator.next();
                if (!item.isFormField()) {
                    if (item.getFieldName().equals("photo")) {
                        AttachedFile photo = new AttachedFile();
                        photo.setName(RandomStringUtils.randomNumeric(5) + "_" + "profilephoto");
                        photo.setBytes(item.get());
                        servletRequest.setAttribute("profile_photo", photo);
                    } else {
                        AttachedFile file = new AttachedFile();
                        file.setBytes(item.get());
                        afList.add(file);
                    }
                } else {
                    LOGGER.info(item.getFieldName() + ": " + item.getString());
                    switch (item.getFieldName()) {
                        case "country_code_val":
                            countryCodes.add(item.getString());
                            break;
                        case "operator_code_val":
                            operatorCodes.add(item.getString());
                            break;
                        case "phone_type_val":
                            phoneTypes.add(item.getString());
                            break;
                        case "number_val":
                            numbers.add(item.getString());
                            break;
                        case "comment_val":
                            comments.add(item.getString());
                            break;
                        case "t_fname":
                            fileNames.add(item.getString());
                            break;
                        case "t_fcomment":
                            fileComments.add(item.getString());
                            break;
                        case "t_frealname":
                            fileRealNames.add(item.getString());
                            break;
                        default:
                            servletRequest.setAttribute(item.getFieldName(), item.getString());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < numbers.size(); i++) {
            Phone p = new Phone();
            p.setOperatorCode(operatorCodes.get(i));
            p.setStateCode(countryCodes.get(i));
            p.setType(phoneTypes.get(i));
            p.setNumber(numbers.get(i));
            p.setComment(comments.get(i));
            phoneList.add(p);
        }
        for (int i = 0; i < afList.size(); i++) {
            AttachedFile thisFile = afList.get(i);
            thisFile.setType(LogicUtils.getFileType(fileRealNames.get(i)));
            thisFile.setName(fileNames.get(i) + "_" + RandomStringUtils.randomNumeric(5));
            thisFile.setDescription(fileComments.get(i));
        }
        LOGGER.info(fileRealNames);
        LOGGER.info("Phone list: " + phoneList);
        LOGGER.info("Number of files: " + afList.size());
        servletRequest.setAttribute("phoneList", phoneList);
        servletRequest.setAttribute("fileList", afList);
    }
}

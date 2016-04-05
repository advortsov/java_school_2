package com.tsystems.javaschool.view.servlet;


import com.tsystems.javaschool.dao.entity.Book;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ShowImage extends HttpServlet {

    private static byte[] defaultImage = null;

    private byte[] getDefaultImage() {
        if (defaultImage != null) {
            return defaultImage;
        } else {
            String filePath = getServletContext().getRealPath("/") + "/resources/images/standart_book_img.png";
            File file = new File(filePath);
            byte[] bytes = new byte[0];

            try (FileInputStream fis = new FileInputStream(file);
                 ByteArrayOutputStream bos = new ByteArrayOutputStream();) {
                byte[] buf = new byte[1024];
                try {
                    for (int readNum; (readNum = fis.read(buf)) != -1; ) {
                        //Writes to this byte array output stream
                        bos.write(buf, 0, readNum);
                    }
                } catch (IOException ex) {
                }

                bytes = bos.toByteArray();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            defaultImage = bytes;
            return defaultImage;
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("image/jpeg");

        try (OutputStream out = response.getOutputStream()) {
            Integer index = Integer.valueOf(request.getParameter("index"));
            List<Book> list = (ArrayList<Book>) request.getSession().getAttribute("allBooks");
            if (index != null && list != null) {
                Book book = list.get(index);
                if (book.getImage() != null) {
                    response.setContentLength(book.getImage().length);
                    out.write(book.getImage());
                } else {
                    out.write(getDefaultImage());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}
package ma.fstt.shoplite.controllers;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.nio.file.*;

@WebServlet("/image")
public class ImageController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String imageName = req.getParameter("name");
        if (imageName == null || imageName.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing image name");
            return;
        }

        // Dynamically get the folder inside your webapp
        String imageDir = getServletContext().getRealPath("/uploads");
        File imageFile = new File(imageDir, imageName);

        if (!imageFile.exists()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        resp.setContentType(Files.probeContentType(imageFile.toPath()));

        try (FileInputStream in = new FileInputStream(imageFile);
             OutputStream out = resp.getOutputStream()) {
            in.transferTo(out);
        }
    }
}

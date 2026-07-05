package com.example.certificate.util;

import com.example.certificate.model.CertificateRequest;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.File;

public class PdfGenerator {

    public static byte[] generateCertificate(
            String templatePath,
            CertificateRequest request
    ) throws Exception {

        PDDocument document = Loader.loadPDF(new File(templatePath));

        PDPage page = document.getPage(0);

        PDPageContentStream contentStream =
                new PDPageContentStream(
                        document,
                        page,
                        PDPageContentStream.AppendMode.APPEND,
                        true,
                        true
                );

        float NAME_CENTER_X;
        float NAME_Y;

        float DURATION_X;
        float DURATION_Y;

        float START_X;
        float START_Y;

        float END_X;
        float END_Y;

        switch (request.getCertificateType()) {

            case "DA":

                NAME_CENTER_X = 430;
                NAME_Y = 306;

                DURATION_X = 557;
                DURATION_Y = 245;

                START_X = 331;
                START_Y = 223;

                END_X = 512;
                END_Y = 222;

                break;

            case "MERN":

                NAME_CENTER_X = 430;
                NAME_Y = 306;

                // <-- MERN duration coordinates
                DURATION_X = 630;   // Change these after testing
                DURATION_Y = 248;

                START_X = 331;
                START_Y = 223;

                END_X = 512;
                END_Y = 222;

                break;

            case "DSA":

                NAME_CENTER_X = 430;
                NAME_Y = 306;

                // <-- DSA duration coordinates
                DURATION_X = 580;
                DURATION_Y = 246;

                START_X = 331;
                START_Y = 223;

                END_X = 512;
                END_Y = 222;

                break;

            default:

                throw new IllegalArgumentException("Invalid Certificate Type");
        }

        //----------------------------------------
        // FONT
        //----------------------------------------

        PDType1Font nameFont =
                new PDType1Font(Standard14Fonts.FontName.TIMES_BOLD);

        PDType1Font normalFont =
                new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN);

        //----------------------------------------
        // NAME
        //----------------------------------------

        float fontSize = 28;

        float width =
                nameFont.getStringWidth(request.getName())
                        /1000
                        *fontSize;

        float startX =
                NAME_CENTER_X - (width/2);

        contentStream.beginText();
        contentStream.setNonStrokingColor(Color.BLACK);
        contentStream.setFont(nameFont,fontSize);
        contentStream.newLineAtOffset(startX,NAME_Y);
        contentStream.showText(request.getName());
        contentStream.endText();

        //----------------------------------------
        // DURATION
        //----------------------------------------

        contentStream.beginText();
        contentStream.setNonStrokingColor(Color.BLACK);
        contentStream.setFont(normalFont,14);
        contentStream.newLineAtOffset(DURATION_X,DURATION_Y);
        contentStream.showText(request.getDuration());
        contentStream.endText();

        //----------------------------------------
        // START DATE
        //----------------------------------------

        contentStream.beginText();
        contentStream.setNonStrokingColor(Color.BLACK);
        contentStream.setFont(normalFont,14);
        contentStream.newLineAtOffset(START_X,START_Y);
        contentStream.showText(request.getStartDate());
        contentStream.endText();

        //----------------------------------------
        // END DATE
        //----------------------------------------

        contentStream.beginText();
        contentStream.setNonStrokingColor(Color.BLACK);
        contentStream.setFont(normalFont,14);
        contentStream.newLineAtOffset(END_X,END_Y);
        contentStream.showText(request.getEndDate());
        contentStream.endText();

        contentStream.close();

        ByteArrayOutputStream output =
                new ByteArrayOutputStream();

        document.save(output);
        document.close();

        return output.toByteArray();

    }

}
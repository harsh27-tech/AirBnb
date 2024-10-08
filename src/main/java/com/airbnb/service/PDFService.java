package com.airbnb.service;

import com.airbnb.entity.Booking;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.stream.Stream;

@Service
public class PDFService {

    //private EmailService emailService;

//    public PDFService(EmailService emailService) {
//        this.emailService = emailService;
//    }

    public void createPdf(Booking booking){
       try{
           Document document = new Document();
           PdfWriter.getInstance(document, new FileOutputStream("D://bnb_booking//"+booking.getId()+"_booking_confirmation.pdf"));

           document.open();

           PdfPTable table = new PdfPTable(3);
           addTableHeader(table);
           addRows(table,booking);
           document.add(table);
           document.close();
           //Another method to call Email
           //emailService.sendEmailWithAttachment(
//                   booking.getEmail(),
//                   "Booking Confirmation. Your booking id is "+ booking.getId(),
//                   "test",
//                   new File("D://bnb_booking//"+booking.getId()+"_booking_confirmation.pdf")
//           );
       }catch(Exception e){
           e.printStackTrace();
       }
    }
    private void addTableHeader(PdfPTable table) {
        Stream.of("Guest Name", "Hotel Name", "City ")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }
    private void addRows(PdfPTable table,Booking booking) {
        table.addCell(booking.getGuestName());
        table.addCell(booking.getProperty().getName());
        table.addCell(booking.getProperty().getCity().getName());
    }
}

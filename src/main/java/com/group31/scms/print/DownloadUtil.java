package com.group31.scms.print;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.stereotype.Component;

import com.group31.scms.model.CasePaper;
import com.group31.scms.model.FollowUp;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

@Component
public class DownloadUtil {

    public void generatePDF(CasePaper casePaper) {
        try {
            FollowUp followUp = null;;
            Set<FollowUp> followUpSet = new HashSet<>();
            TreeSet<FollowUp> hashSetToTreeSet = new TreeSet<>(followUpSet);
            if(casePaper.getFollowUps() !=null) {
                hashSetToTreeSet.addAll(casePaper.getFollowUps());
            }
            Optional<FollowUp> lastFollowUp = hashSetToTreeSet.descendingSet().stream().findFirst();
            if (lastFollowUp.isPresent()) {
                followUp = lastFollowUp.get();
            }

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy");

            Document document = new Document();
            String fullFilePath = System.getProperty("user.home") + "\\Downloads\\" + getFileName(casePaper);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fullFilePath));
            writer.setPageEvent(new WatermarkPageEvent());
            document.open();
            Paragraph headingPara = new Paragraph("PATIENT CASE PAPER", new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLDITALIC));
            headingPara.setAlignment(Element.ALIGN_CENTER);
            document.add(headingPara);
            // Add case paper details
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Case Paper creation date : " + simpleDateFormat.format(casePaper.getCreatedOn())));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Case ID : " + casePaper.getCasePaperNumber()));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Patient Name : " + casePaper.getFirstName() + " " + casePaper.getLastName()));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Gender : " + casePaper.getGender()));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Occupation : " + casePaper.getOccupation()));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Age : " + casePaper.getAge()));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Contact Mobile : " + (casePaper.getContactNo() != null ? casePaper.getContactNo(): "")));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Address : " + casePaper.getAddress()));
            document.add(new Paragraph(" "));
            //Follow Up details
            Paragraph followUpPara = new Paragraph("*** Last Follow Up ***", new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLDITALIC));
            followUpPara.setAlignment(Element.ALIGN_CENTER);
            document.add(followUpPara);
            if(followUp == null) {
                Paragraph endPara = new Paragraph("No follow-up yet", new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.ITALIC));
                endPara.setAlignment(Element.ALIGN_CENTER);
                document.add(endPara);
            }else{
                document.add(new Paragraph("Follow Up Date : " + simpleDateFormat.format(followUp.getFollowUpDate())));
                document.add(new Paragraph(" "));
                document.add(new Paragraph("Diagnosis : " + followUp.getDiagnosis()));
                document.add(new Paragraph(" "));
                document.add(new Paragraph("Complaint : " + followUp.getComplaint()));
                document.add(new Paragraph(" "));
                document.add(new Paragraph("Medicine : " + followUp.getMedicine()));
                document.add(new Paragraph(" "));
                document.add(new Paragraph("Weight : " + (followUp.getWeight() != null ? followUp.getWeight():"")));
                document.add(new Paragraph(" "));
                document.add(new Paragraph("Mind : " + followUp.getMind()));
                document.add(new Paragraph(" "));
                document.add(new Paragraph("Head : " + followUp.getHead()));
                document.add(new Paragraph(" "));
                document.add(new Paragraph("Hairs : " + followUp.getHairs()));
                document.add(new Paragraph(" "));
                document.add(new Paragraph("Sleep : " + followUp.getSleep()));
                document.add(new Paragraph(" "));
                document.add(new Paragraph("Dreams : " + followUp.getDreams()));
                document.add(new Paragraph(" "));
                document.add(new Paragraph("Eyes : " + followUp.getEyes()));
                document.add(new Paragraph(" "));
                document.add(new Paragraph("Ears : " + followUp.getEars()));
                document.add(new Paragraph(" "));
                document.add(new Paragraph("Nose : " + followUp.getNose()));
                document.add(new Paragraph(" "));
                document.add(new Paragraph("Mouth : " + followUp.getMouth()));
                document.add(new Paragraph(" "));
                document.add(new Paragraph("Back : " + followUp.getBack()));
                document.add(new Paragraph(" "));
                document.add(new Paragraph("Urine : " + followUp.getUrine()));
                document.add(new Paragraph(" "));
                document.add(new Paragraph("Stool : " + followUp.getStool()));
                document.add(new Paragraph(" "));
                document.add(new Paragraph("Extremities : " + followUp.getExtremities()));
                document.add(new Paragraph(" "));
                document.add(new Paragraph("Skin : " + followUp.getSkin()));
                document.add(new Paragraph(" "));
                document.add(new Paragraph("Thermal : " + followUp.getThermal()));
                document.add(new Paragraph(" "));
                document.add(new Paragraph("Throat : " + followUp.getThroat()));
                document.add(new Paragraph(" "));
                document.add(new Paragraph("Chest : " + followUp.getChest()));
                document.add(new Paragraph(" "));
                document.add(new Paragraph("Stomach : " + followUp.getStomach()));
                document.add(new Paragraph(" "));
                document.add(new Paragraph("Blood report : " + followUp.getBloodReport()));
                document.add(new Paragraph(" "));
            }

            Paragraph endPara = new Paragraph("*** Thank you visit again ***", new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLDITALIC));
            endPara.setAlignment(Element.ALIGN_CENTER);
            document.add(endPara);

            document.addAuthor("Dr Avinash Todhkar Clinic");
            document.addCreationDate();
            document.addCreator("Systematic case paper management");
            document.addTitle("Case paper of patient");

            document.close();
            // to open pdf file
            download(fullFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void download(String fileName) throws IOException, InterruptedException {
        if ((new File(fileName)).exists()) {
            Process p = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + fileName);
            p.waitFor();
        } else {
            System.out.println("fileName:: " + fileName);
            System.out.println("File is not exists");
        }
    }

    private String getFileName(CasePaper casePaper) {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return casePaper.getFirstName() + "_" + casePaper.getCasePaperNumber() + "_" + String.format("%06d", number) + ".pdf";
    }

}

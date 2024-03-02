package day31;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class PDFtoExcelConverter {
    public static void main(String[] args) {
        String pdfFilePath = "C:\\Users\\amola\\OneDrive\\Desktop\\KITE_10_07_23.pdf";
        String excelFilePath = "C:\\Users\\amola\\OneDrive\\Desktop\\KITE_10_07_23.xlsx";

        try {
            // Load the PDF document
            PDDocument document = PDDocument.load(new File(pdfFilePath));

            // Initialize PDFTextStripper class to extract text from the PDF
            PDFTextStripper pdfTextStripper = new PDFTextStripper();

            // Extract text from the PDF
            String pdfText = pdfTextStripper.getText(document);

            // Split the text into lines
            String[] lines = pdfText.split("\n");

            // Create a new Excel workbook
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Sheet1");
            int rowIdx = 0;

            // Iterate over each line and create Excel rows and cells
            for (String line : lines) {
                Row row = sheet.createRow(rowIdx++);
                String[] cells = line.split(" ");
                int cellIdx = 0;
                for (String cellData : cells) {
                    Cell cell = row.createCell(cellIdx++);
                    cell.setCellValue(cellData);
                }
            }

            // Write the Excel workbook to the output file
            FileOutputStream outputStream = new FileOutputStream(new File(excelFilePath));
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();

            System.out.println("Conversion completed successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

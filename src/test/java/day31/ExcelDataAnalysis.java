package day31;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ExcelDataAnalysis {
    public static void main(String[] args) {
        String excelFilePath = "C:\\Users\\amola\\OneDrive\\Desktop\\Book2.xlsx";

        try {
            FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            System.out.println("Minute Group\tMin Offer\tMax Offer");

            for (Row row : sheet) {
                // Skip the header row
                if (row.getRowNum() == 0) {
                    continue;
                }

                Cell minuteGroupCell = row.getCell(0);
                Cell offerCell = row.getCell(1);

                String minuteGroup;
                double firstSecondOffer;

                if (minuteGroupCell.getCellType() == CellType.NUMERIC) {
                    minuteGroup = Double.toString(minuteGroupCell.getNumericCellValue());
                } else if (minuteGroupCell.getCellType() == CellType.STRING) {
                    minuteGroup = minuteGroupCell.getStringCellValue();
                } else {
                    continue; // Skip the row if minuteGroupCell is not numeric or string
                }

                if (offerCell.getCellType() == CellType.NUMERIC) {
                    firstSecondOffer = offerCell.getNumericCellValue();
                } else {
                    continue; // Skip the row if offerCell is not numeric
                }

                double minOffer = Double.MAX_VALUE;
                double maxOffer = Double.MIN_VALUE;

                for (Row innerRow : sheet) {
                    Cell innerMinuteGroupCell = innerRow.getCell(0);
                    Cell innerOfferCell = innerRow.getCell(1);

                    String innerMinuteGroup;
                    double offer;

                    if (innerMinuteGroupCell.getCellType() == CellType.NUMERIC) {
                        innerMinuteGroup = Double.toString(innerMinuteGroupCell.getNumericCellValue());
                    } else if (innerMinuteGroupCell.getCellType() == CellType.STRING) {
                        innerMinuteGroup = innerMinuteGroupCell.getStringCellValue();
                    } else {
                        continue; // Skip the row if innerMinuteGroupCell is not numeric or string
                    }

                    if (innerOfferCell.getCellType() == CellType.NUMERIC) {
                        offer = innerOfferCell.getNumericCellValue();
                    } else {
                        continue; // Skip the row if innerOfferCell is not numeric
                    }

                    if (minuteGroup.equals(innerMinuteGroup)) {
                        minOffer = Math.min(minOffer, offer);
                        maxOffer = Math.max(maxOffer, offer);
                    }
                }

                if (firstSecondOffer == minOffer) {
                    System.out.println(minuteGroup + "\t\t" + minOffer + "\t\t" + maxOffer);
                }
            }

            workbook.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

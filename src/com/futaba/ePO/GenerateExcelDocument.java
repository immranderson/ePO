package com.futaba.ePO;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class GenerateExcelDocument {

	public GenerateExcelDocument(ArrayList<ePOData> ePODataList) throws Exception{

		try
		{

			//Create Workbook
			Workbook wb = new XSSFWorkbook();
			CreationHelper createHelper = wb.getCreationHelper();

			Sheet[] sheet = new Sheet[20]; //Create Excel Worksheet for each category

			sheet[0] = wb.createSheet("FIC Piece Price");
			sheet[1] = wb.createSheet("FIA Piece Price");
			sheet[2] = wb.createSheet("FIO Piece Price");
			sheet[3] = wb.createSheet("FIT Piece Price");
			sheet[4] = wb.createSheet("FIC Service");
			sheet[5] = wb.createSheet("FIA Service");
			sheet[6] = wb.createSheet("FIO Service");
			sheet[7] = wb.createSheet("FIT Service");
			sheet[8] = wb.createSheet("FIC Direct");
			sheet[9] = wb.createSheet("FIA Direct");
			sheet[10] = wb.createSheet("FIO Direct");
			sheet[11] = wb.createSheet("FIT Direct");
			sheet[12] = wb.createSheet("FIC Tooling");
			sheet[13] = wb.createSheet("FIA Tooling");
			sheet[14] = wb.createSheet("FIO Tooling");
			sheet[15] = wb.createSheet("FIT Tooling");
			sheet[16] = wb.createSheet("FIC Packaging");
			sheet[17] = wb.createSheet("FIA Packaging");
			sheet[18] = wb.createSheet("FIO Packaging");
			sheet[19] = wb.createSheet("FIT Packaging");





			//Headers




			for (int k=0; k<20; k++)
			{
				Row row = sheet[k].createRow(0);

				row.createCell(0).setCellValue(createHelper.createRichTextString("Part Number"));
				row.createCell(1).setCellValue(createHelper.createRichTextString("PO Number"));
				row.createCell(2).setCellValue(createHelper.createRichTextString("Revision"));
				row.createCell(3).setCellValue(createHelper.createRichTextString("REV DATE"));
				row.createCell(4).setCellValue(createHelper.createRichTextString("NAMC"));
				row.createCell(5).setCellValue(createHelper.createRichTextString("Piece Price"));
				row.createCell(6).setCellValue(createHelper.createRichTextString("Effective Date"));
				row.createCell(7).setCellValue(createHelper.createRichTextString("Note"));
				row.createCell(8).setCellValue(createHelper.createRichTextString("Submission Date"));

			}

			//Input Data

			Row row;

			int[] s = new int[20];
			for (int m=0; m<20; m++)
			{
				s[m] = 1;
			}

			for (ePOData data : ePODataList)
			{
				

				if (data.getFileName().contains("Tooling") || data.getFileName().contains("Packaging"))
					continue;

				//Modify Piece Number Name: Add hyphen 5 characters in

				String formattedPartNumber = new StringBuffer(data.getPartNumber()).insert(5,"-").toString();

				int k;



				//so we can make the rows neat, we're going to have an s[m] assigned to each case and iterate by 1 if there is an entry
				
				if (data.getFileName().contains("0756-3 Piece Price")) {

					k=3;
					row = sheet[k].createRow(s[0]);
					row.createCell(0).setCellValue(formattedPartNumber);
					row.createCell(1).setCellValue((data.getPO()));
					row.createCell(2).setCellValue((Integer.parseInt(data.getRev())));			    
					row.createCell(3).setCellValue((data.getRevDate()));		    
					row.createCell(4).setCellValue((data.getShipToAttention()));
					row.createCell(5).setCellValue((Double.parseDouble(data.getTotalPrice().replace("$",""))));
					row.createCell(6).setCellValue((data.getEffectiveDate()));
					row.createCell(7).setCellValue((data.getNotes()));
					s[0]++;
				}

				else if (data.getFileName().contains("0756-2 Piece Price")) {
					k=2;
					row = sheet[k].createRow(s[1]);
					row.createCell(0).setCellValue(formattedPartNumber);
					row.createCell(1).setCellValue((data.getPO()));
					row.createCell(2).setCellValue((Integer.parseInt(data.getRev())));
					row.createCell(3).setCellValue((data.getRevDate()));
					row.createCell(4).setCellValue((data.getShipToAttention()));
					row.createCell(5).setCellValue((Double.parseDouble(data.getTotalPrice().replace("$",""))));
					row.createCell(6).setCellValue((data.getEffectiveDate()));

					row.createCell(7).setCellValue((data.getNotes()));
					s[1]++;
				}


				else if (data.getFileName().contains("0756-1 Piece Price")) {
					k=1;
					row = sheet[k].createRow(s[2]);
					row.createCell(0).setCellValue(formattedPartNumber);
					row.createCell(1).setCellValue((data.getPO()));
					row.createCell(2).setCellValue((Integer.parseInt(data.getRev())));
					row.createCell(3).setCellValue((data.getRevDate()));
					row.createCell(4).setCellValue((data.getShipToAttention()));
					row.createCell(5).setCellValue((Double.parseDouble(data.getTotalPrice().replace("$",""))));
					row.createCell(6).setCellValue((data.getEffectiveDate()));

					row.createCell(7).setCellValue((data.getNotes()));
					s[2]++;
				}

				else if (data.getFileName().contains("0756-0 Piece Price")){
					k=0;
					row = sheet[k].createRow(s[3]);
					row.createCell(0).setCellValue(formattedPartNumber);
					row.createCell(1).setCellValue((data.getPO()));
					row.createCell(2).setCellValue((Integer.parseInt(data.getRev())));
					row.createCell(3).setCellValue((data.getRevDate()));
					row.createCell(4).setCellValue((data.getShipToAttention()));
					row.createCell(5).setCellValue((Double.parseDouble(data.getTotalPrice().replace("$",""))));
					row.createCell(6).setCellValue((data.getEffectiveDate()));

					row.createCell(7).setCellValue((data.getNotes()));
					s[3]++;
				}


				else if (data.getFileName().contains("0756-3 TMS")){
					k=7;
					row = sheet[k].createRow(s[4]);
					row.createCell(0).setCellValue(formattedPartNumber);
					row.createCell(1).setCellValue((data.getPO()));
					row.createCell(2).setCellValue((Integer.parseInt(data.getRev())));
					row.createCell(3).setCellValue((data.getRevDate()));
					row.createCell(4).setCellValue((data.getShipToAttention()));
					row.createCell(5).setCellValue((Double.parseDouble(data.getTotalPrice().replace("$",""))));
					row.createCell(6).setCellValue((data.getEffectiveDate()));

					row.createCell(7).setCellValue((data.getNotes()));
					s[4]++;
				}

				else if (data.getFileName().contains("0756-2 TMS")){
					k=6;
					row = sheet[k].createRow(s[5]);
					row.createCell(0).setCellValue(formattedPartNumber);
					row.createCell(1).setCellValue((data.getPO()));
					row.createCell(2).setCellValue((Integer.parseInt(data.getRev())));
					row.createCell(3).setCellValue((data.getRevDate()));
					row.createCell(4).setCellValue((data.getShipToAttention()));
					row.createCell(5).setCellValue((Double.parseDouble(data.getTotalPrice().replace("$",""))));
					row.createCell(6).setCellValue((data.getEffectiveDate()));

					row.createCell(7).setCellValue((data.getNotes()));
					s[5]++;
				}

				else if (data.getFileName().contains("0756-1 TMS")){
					k=5;
					row = sheet[k].createRow(s[6]);
					row.createCell(0).setCellValue(formattedPartNumber);
					row.createCell(1).setCellValue((data.getPO()));
					row.createCell(2).setCellValue((Integer.parseInt(data.getRev())));
					row.createCell(3).setCellValue((data.getRevDate()));
					row.createCell(4).setCellValue((data.getShipToAttention()));
					row.createCell(5).setCellValue((Double.parseDouble(data.getTotalPrice().replace("$",""))));
					row.createCell(6).setCellValue((data.getEffectiveDate()));

					row.createCell(7).setCellValue((data.getNotes()));
					s[6]++;
				}

				else if (data.getFileName().contains("0756-0 TMS")){
					k=4;
					row = sheet[k].createRow(s[7]);
					row.createCell(0).setCellValue(formattedPartNumber);
					row.createCell(1).setCellValue((data.getPO()));
					row.createCell(2).setCellValue((Integer.parseInt(data.getRev())));
					row.createCell(3).setCellValue((data.getRevDate()));
					row.createCell(4).setCellValue((data.getShipToAttention()));
					row.createCell(5).setCellValue((Double.parseDouble(data.getTotalPrice().replace("$",""))));
					row.createCell(6).setCellValue((data.getEffectiveDate()));

					row.createCell(7).setCellValue((data.getNotes()));
					s[7]++;
				}


				else if (data.getFileName().contains("0756-3 Direct")){
					k=11;
					row = sheet[k].createRow(s[8]);
					row.createCell(0).setCellValue(formattedPartNumber);
					row.createCell(1).setCellValue((data.getPO()));
					row.createCell(2).setCellValue((Integer.parseInt(data.getRev())));
					row.createCell(3).setCellValue((data.getRevDate()));
					row.createCell(4).setCellValue((data.getShipToAttention()));
					row.createCell(5).setCellValue((Double.parseDouble(data.getTotalPrice().replace("$",""))));
					row.createCell(6).setCellValue((data.getEffectiveDate()));

					row.createCell(7).setCellValue((data.getNotes()));
					s[8]++;
				}

				else if (data.getFileName().contains("0756-2 Direct")){
					k=10;
					row = sheet[k].createRow(s[9]);
					row.createCell(0).setCellValue(formattedPartNumber);
					row.createCell(1).setCellValue((data.getPO()));
					row.createCell(2).setCellValue((Integer.parseInt(data.getRev())));
					row.createCell(3).setCellValue((data.getRevDate()));
					row.createCell(4).setCellValue((data.getShipToAttention()));
					row.createCell(5).setCellValue((Double.parseDouble(data.getTotalPrice().replace("$",""))));
					row.createCell(6).setCellValue((data.getEffectiveDate()));

					row.createCell(7).setCellValue((data.getNotes()));
					s[9]++;
				}

				else if (data.getFileName().contains("0756-1 Direct")){
					k=9;
					row = sheet[k].createRow(s[10]);
					row.createCell(0).setCellValue(formattedPartNumber);
					row.createCell(1).setCellValue((data.getPO()));
					row.createCell(2).setCellValue((Integer.parseInt(data.getRev())));
					row.createCell(3).setCellValue((data.getRevDate()));
					row.createCell(4).setCellValue((data.getShipToAttention()));
					row.createCell(5).setCellValue((Double.parseDouble(data.getTotalPrice().replace("$",""))));
					row.createCell(6).setCellValue((data.getEffectiveDate()));

					row.createCell(7).setCellValue((data.getNotes()));
					s[10]++;
				}

				else if (data.getFileName().contains("0756-0 Direct")){
					k=8;
					row = sheet[k].createRow(s[11]);
					row.createCell(0).setCellValue(formattedPartNumber);
					row.createCell(1).setCellValue((data.getPO()));
					row.createCell(2).setCellValue((Integer.parseInt(data.getRev())));
					row.createCell(3).setCellValue((data.getRevDate()));
					row.createCell(4).setCellValue((data.getShipToAttention()));
					row.createCell(5).setCellValue((Double.parseDouble(data.getTotalPrice().replace("$",""))));
					row.createCell(6).setCellValue((data.getEffectiveDate()));

					row.createCell(7).setCellValue((data.getNotes()));
					s[11]++;
				}

			} 

			FileOutputStream fileOut = new FileOutputStream(GUI.outputdirectoryname + "\\" + "Price Update Sheet.xlsx"); //write to excel spreadsheet
			wb.write(fileOut);
			fileOut.close();






		} catch (Exception ex){
			ex.printStackTrace();
		}

		InputStream fileRead = new FileInputStream(GUI.outputdirectoryname + "\\" + "Price Update Sheet.xlsx");
		XSSFWorkbook wbRead = new XSSFWorkbook(fileRead);



//		for (int sheet=0 ; sheet<16 ; sheet++)
//		{
//			temp_cell = CV.Search("Piece Price", wbRead, sheet);
//			piece_price_column = temp_cell.getColumnIndex();
//			rowRead = temp_cell.getRowIndex()+1;
//			do
//			{
//				temp_read = CV.Read(wbRead, sheet, rowRead, piece_price_column);
//				temp_read = temp_read.replace("$", "");
//				CV.Write(temp_read, wbRead, sheet, rowRead, piece_price_column);
//
//				rowRead++;
//			}
//			while (!temp_read.equals(""));
//
//
//			temp_cell = CV.Search("Effective Date", wbRead, sheet);
//			effective_date_column = temp_cell.getColumnIndex();
//			rowRead = temp_cell.getRowIndex()+1;
//
//			do
//			{
//				temp_read = CV.Read(wbRead, sheet, rowRead, effective_date_column);
//				temp_read = temp_read.replace("     /  /  ", "");
//				temp_read = temp_read.replace(": THRU", "");
//				if (temp_read.length()>10)
//				{
//					temp_read = temp_read.substring(0, 10) + " THRU" + temp_read.substring(10,21);
//				}
//
//				CV.Write(temp_read, wbRead, sheet, rowRead, effective_date_column);
//
//				rowRead++;
//			}
//			while (!temp_read.equals(""));


//		}

		FileOutputStream fileSave = new FileOutputStream(GUI.outputdirectoryname + "\\" + "Price Update Sheet.xlsx");
		wbRead.write(fileSave);
		fileSave.close();

	}

}

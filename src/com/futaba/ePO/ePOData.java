package com.futaba.ePO;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.util.PDFTextStripperByArea;


/* This class goes through a single purchase order and scrapes the data
 * 
 */
public class ePOData {


	private String part_number, po, rev, ship_to, vendor, effective_date, total_price, notes, rev_date, file_name;

	public ePOData(File file) throws IOException
	{
		
		String filename = file.getName();
		
		this.file_name = filename;

		if (filename.contains("Tooling") || filename.contains("Packaging"))
			return;

		PDDocument doc = PDDocument.load(GUI.inputdirectoryname + "\\" + filename);

		List<?> allPages = doc.getDocumentCatalog().getAllPages();
		PDPage firstPage = (PDPage)allPages.get( 0 );

		//System.out.print(sentence);



		//Find and extract text based on their location within the document.

		PDFTextStripperByArea stripper = new PDFTextStripperByArea();
		stripper.setSortByPosition( true );

		Rectangle po_no_rect = new Rectangle( 400, 90, 100, 10 );
		stripper.addRegion( "PO#", po_no_rect );


		Rectangle rev_no_rect = new Rectangle( 400, 110, 100, 10 );
		stripper.addRegion( "Rev. No.", rev_no_rect );


		// Ship to box check

		Rectangle ship_to_rect = new Rectangle( 200, 200, 175, 20 ); 
		stripper.addRegion("Ship To Attention", ship_to_rect);

		stripper.extractRegions( firstPage );

		String shipcheck = stripper.getTextForRegion( "Ship To Attention" );
		Rectangle vendor_rect = new Rectangle();

		if(shipcheck.contains("Futaba")||shipcheck.contains("America")||shipcheck.contains("FIO")||shipcheck.contains("Indiana"))
		{				
			ship_to_rect = new Rectangle( 25, 200, 150, 10 ); 
			stripper.addRegion("Ship To Attention", ship_to_rect);


			vendor_rect = new Rectangle( 200, 200, 190, 20 );
			stripper.addRegion( "Vendor", vendor_rect );

		} 
		else
		{
			ship_to_rect = new Rectangle( 200, 200, 190, 10 ); 
			stripper.addRegion("Ship To Attention", ship_to_rect);


			vendor_rect = new Rectangle( 25, 200, 150, 20 );
			stripper.addRegion( "Vendor", vendor_rect );

		}

		// End Ship to box check


		Rectangle piece_rect = new Rectangle( 130, 420, 80, 20 );
		stripper.addRegion( "Piece", piece_rect );





		//Begin Effective Date Placement Differences

		if (filename.contains("Piece Price")||filename.contains("Direct"))
		{
			Rectangle effective_date_rect = new Rectangle( 200, 400, 200, 10 );
			stripper.addRegion( "Effective Date", effective_date_rect );
		}

		else if (filename.contains("Service"))
		{
			Rectangle effective_date_rect = new Rectangle( 200, 390, 200, 10 );
			stripper.addRegion( "Effective Date", effective_date_rect );
		}

		//End Effective Date Placement Differences




		//Begin Price Placement Differences

		if (filename.contains("Piece Price"))
		{
			Rectangle total_price_rect = new Rectangle( 500, 510, 200, 10 );
			stripper.addRegion( "Total Price", total_price_rect );
		}

		else if (filename.contains("Service"))
		{
			Rectangle total_price_rect = new Rectangle( 500, 500, 200, 10 );
			stripper.addRegion( "Total Price", total_price_rect );
		}

		else if (filename.contains("Direct"))
		{
			Rectangle total_price_rect = new Rectangle( 500, 510, 200, 10 );
			stripper.addRegion( "Total Price", total_price_rect );
		}

		//End Price Placement Differences


		//Begin Notes Placement Differences

		if (filename.contains("Piece Price"))
		{
			Rectangle notes_rect = new Rectangle( 150, 520, 500, 40 );
			stripper.addRegion( "Notes", notes_rect );
		}

		else if (filename.contains("Service"))
		{
			Rectangle notes_rect = new Rectangle( 150, 540, 500, 30 );
			stripper.addRegion( "Notes", notes_rect );
		}

		else if (filename.contains("Direct"))
		{
			Rectangle notes_rect = new Rectangle( 80, 540, 540, 50 );
			stripper.addRegion( "Notes", notes_rect );
		}

		//End Notes Placement Differences


		Rectangle rev_date_rect = new Rectangle( 340, 132, 106, 8 );
		stripper.addRegion( "Revision Date", rev_date_rect );


		stripper.extractRegions( firstPage ); //store contents of regions into memory


		String po_storage, rev_storage, ship_to_storage, vendor_storage, piece_storage, effective_date_storage, total_price_storage, notes_storage, rev_date_storage;


		po_storage = stripper.getTextForRegion( "PO#" );
		po_storage = po_storage.replaceAll("\n", ""); //formatting
		po_storage = po_storage.replaceAll("\r", "");
		System.out.println( "Text in the PO area:" + po_storage );  //trace

		this.po = po_storage;


		rev_storage = stripper.getTextForRegion("Rev. No.");
		rev_storage = rev_storage.replaceAll("\n", "");
		rev_storage = rev_storage.replaceAll("\r", "");
		rev_storage = rev_storage.replaceAll(" ", "");
		if (rev_storage.equals(""))
			rev_storage="0000";
		System.out.println( "Text in the Rev area:" + rev_storage );

		this.rev = rev_storage;


		ship_to_storage = stripper.getTextForRegion( "Ship To Attention" );
		ship_to_storage = ship_to_storage.replaceAll("\n", "");
		ship_to_storage = ship_to_storage.replaceAll("\r", "");
		System.out.println( "Text in the ship to area:" + ship_to_storage );;

		this.ship_to = ship_to_storage;


		vendor_storage = stripper.getTextForRegion( "Vendor" );
		vendor_storage = vendor_storage.replaceAll("\n", "");
		vendor_storage = vendor_storage.replaceAll("\r", "");
		System.out.println( "Text in the vendor area:" + vendor_storage );

		this.vendor = vendor_storage;


		piece_storage=stripper.getTextForRegion("Piece");
		piece_storage = piece_storage.replaceAll("\n", "");
		piece_storage = piece_storage.replaceAll("\r", "");
		System.out.println( "Text in the Piece area:" + piece_storage );

		this.part_number = piece_storage;


		effective_date_storage = stripper.getTextForRegion( "Effective Date" );
		effective_date_storage = effective_date_storage.replaceAll("\n", "");
		effective_date_storage = effective_date_storage.replaceAll("\r", "");


		//Begin Effective Date Modification

		effective_date_storage = effective_date_storage.replaceAll("/  /" , "");
		effective_date_storage = effective_date_storage.replaceAll(":", "");
		effective_date_storage = effective_date_storage.trim();

		System.out.println( "Text in the Effective Date area:" + effective_date_storage );

		//End Effective Date Modification

		this.effective_date = effective_date_storage;



		total_price_storage = stripper.getTextForRegion( "Total Price" );
		total_price_storage = total_price_storage.replaceAll("\n", "");
		total_price_storage = total_price_storage.replaceAll("\r", "");
		System.out.println( "Text in the Total Price area:" + total_price_storage );

		this.total_price = total_price_storage;


		notes_storage = stripper.getTextForRegion( "Notes" );
		notes_storage = notes_storage.replaceAll("\n", "");
		notes_storage = notes_storage.replaceAll("\r", "");
		System.out.println( "Text in the Notes area:" + notes_storage );

		this.notes = notes_storage;


		rev_date_storage = stripper.getTextForRegion( "Revision Date" );
		rev_date_storage = rev_date_storage.replaceAll("\n", ""); //formatting
		rev_date_storage = rev_date_storage.replaceAll("\r", "");
		System.out.println( "Text in the Rev Date area:" + rev_date_storage );  //trace

		this.rev_date = rev_date_storage;


		System.out.println("-----------------------------------------");

	}

	public String getPO(){
		return this.po;
	}

	public String getRev(){
		return this.rev;
	}

	public String getRevDate(){
		return this.rev_date;
	}

	public String getShipToAttention(){
		return this.ship_to;
	}

	public String getVendor(){
		return this.vendor;
	}

	public String getPartNumber(){
		return this.part_number;
	}

	public String getEffectiveDate(){
		return this.effective_date;
	}

	public String getTotalPrice(){
		return this.total_price;
	}

	public String getNotes(){
		return this.notes;
	}
	
	public String getFileName(){
		return this.file_name;
	}
	
	public void setFileName(String filename)
	{
		this.file_name = filename;
	}

	

}
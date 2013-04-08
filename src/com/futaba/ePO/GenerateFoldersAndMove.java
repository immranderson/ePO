package com.futaba.ePO;

import java.io.File;


public class GenerateFoldersAndMove {
	

	String subfolder;
	String apr_folder;
	
	/**
	 * @param data
	 * @throws Exception 
	 */
	public GenerateFoldersAndMove(ePOData data) throws Exception
	{
		
		//If file is Tooling or Packaging, skip
		if (data.getFileName().contains("Tooling") || data.getFileName().contains("Packaging")) 
			return;

		
		//Logic for figuring out proper APR year
		String formattedeffectivedate = data.getEffectiveDate().substring(0, 10).replaceAll("/", "");
		System.out.println(formattedeffectivedate);

		String inputyear = formattedeffectivedate.substring(0, 4);

		String apr_begin = inputyear + "0401";
		String next_apr = Integer.toString((Integer.parseInt(inputyear)+1));

		int input_int = Integer.parseInt(formattedeffectivedate);
		int apr_begin_int = Integer.parseInt(apr_begin);


		if (input_int<apr_begin_int)
		    {
			apr_folder = "APR" + inputyear;
		    }

		else
		    {
			apr_folder = "APR" + next_apr;
		    }
		
		
		if (data.getVendor().contains("Texas") && data.getFileName().contains("Piece Price"))
	    {
		subfolder = "0756-3 Piece Price";
		
	    }
	else if (data.getVendor().contains("FIO") && data.getFileName().contains("Piece Price"))
	    {
		subfolder = "0756-2 Piece Price";
		
	    }
	else if (data.getVendor().contains("Indiana") && data.getFileName().contains("Piece Price"))
	    {
		subfolder = "0756-1 Piece Price";
		
	    }
	else if (data.getVendor().contains("FIC America Corp.") && data.getFileName().contains("Piece Price"))
	    {
		subfolder = "0756-0 Piece Price";		
	    }

	else if (data.getVendor().contains("Texas") && data.getFileName().contains("Service"))
	    {
		subfolder = "0756-3 TMS";		
	    }
		
	else if (data.getVendor().contains("FIO") && data.getFileName().contains("Service"))
	    {
		subfolder = "0756-2 TMS";		
	    }
		
	else if (data.getVendor().contains("Indiana") && data.getFileName().contains("Service"))
	    {
		subfolder = "0756-1 TMS";		
	    }
		
	else if (data.getVendor().contains("FIC America Corp.") && data.getFileName().contains("Service"))
	    {
		subfolder = "0756-0 TMS";		
	    }


	else if (data.getVendor().contains("Texas") && data.getFileName().contains("Direct"))
	    {
		subfolder = "0756-3 Direct";		
	    }
	else if (data.getVendor().contains("FIO") && data.getFileName().contains("Direct"))
	    {
		subfolder = "0756-2 Direct";		
	    }
	else if (data.getVendor().contains("Indiana") && data.getFileName().contains("Direct"))
	    {
		subfolder = "0756-1 Direct";		
	    }
	else if (data.getVendor().contains("FIC America Corp.") && data.getFileName().contains("Direct"))
	    {
		subfolder = "0756-0 Direct";
	    }
		
		
		
		//Create Directory referencing APR Year
		File aprFile = new File(GUI.outputdirectoryname + "\\" + apr_folder);
		
		if (aprFile.exists() == false)
		{
			aprFile.mkdir();
			System.out.println("Directory Created");
		}
		else
		{
			System.out.println("Directory was not Created");
		}
		
		subfolder = subfolder + " " + GUI.dateN;
		
		//Create Directory referencing plant location and type -- nested within the APR Year folder
		File file = new File(GUI.outputdirectoryname + "\\" + apr_folder + "\\" + subfolder);
		
		if (file.exists() == false)
		{
			file.mkdir();
			System.out.println("Directory Created");
		}
		else
		{
			System.out.println("Directory was not Created");
		}
		
			
		
			
		//Stitch together actual file name
		String rename = data.getPartNumber() + "-" + data.getPO() + "-" + data.getRev() + ".pdf";
		
		//Move file from original folder to newly created folder
		File sourceFile = new File (GUI.inputdirectoryname + "\\" + data.getFileName());
		File destinationFile = new File (GUI.outputdirectoryname + "\\" + apr_folder + "\\" + subfolder + "\\" + rename);
		
		boolean success = sourceFile.renameTo(destinationFile);
		
		if (success)
			System.out.println("Move Success!");
		else
			System.out.println("Move Failure...");
		
		data.setFileName(destinationFile.getAbsolutePath());
		
		
		File email = new File(GUI.outputdirectoryname + "\\" + "email"); //create directories
		try{
		    if(email.exists()==false){
			email.mkdir();
			System.out.println("Email Directory Created");
		    }
		    else{
			System.out.println("Directory is not created");
		    }
		}catch(Exception e){
		    e.printStackTrace();
		}
		
		String email_sub_directory_name = GUI.outputdirectoryname + "\\" + "email" + "\\" + subfolder ;
		
		File email_with_sub = new File(email_sub_directory_name); //create directories
		try{
		    if(email_with_sub.exists()==false){
			email_with_sub.mkdir();
			System.out.println("Email with subDirectory Created");
		    }
		    else{
			System.out.println("Directory is not created");
		    }
		}catch(Exception e){
		    e.printStackTrace();
		}
		
		
		FileCopy.copy(data.getFileName(), email_sub_directory_name);		

		
		ZipThatShit.zipFolder(GUI.outputdirectoryname + "\\" + apr_folder + "\\" + subfolder, GUI.outputdirectoryname + "\\" + apr_folder + "\\" + subfolder + ".zip");
		ZipThatShit.zipFolder(GUI.outputdirectoryname + "\\" + "email" + "\\" + subfolder, GUI.outputdirectoryname + "\\" + "email" + "\\" + subfolder + ".zip");
		
			
	}
	


}

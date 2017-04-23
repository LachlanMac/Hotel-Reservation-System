package com.group.hrs.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;


public class BookingConfirmation 
{
	public BookingConfirmation (String which, Reservation res) throws IOException
	{
		if (which.equals("create"))
		{
			createDocument(res);
		}
		else if (which.equals("delete"))
		{
			confirmDelete(res);
		}
	}

	/********************************************************************
	* Method which creates a message to the user with the confirmation	*
	* that their reservation has been canceled.							*
	********************************************************************/
	public void createDocument(Reservation res) throws IOException
	{
		//current word that is being checked from template document
		String current1 = "";	
		//String that holds one line of the message to be written to the new file
        String message = "";
        //line counter
        int line = 0;
        
        /************************************************************
         * Open the template file for editing						*
         ***********************************************************/
        File tempFile = new File("res/confirm_template.txt");
        Scanner fileReader = new Scanner(tempFile);
        
        /************************************************************
         * Create new file to copy the deleted confirmation message	*
         * to. The current reservation id is used to give unique	*
         * identification to the file.								*
         ************************************************************/
        String filename = "res/confirm" + res.getLastName() + ".txt";
        BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
        
        /************************************************************
         * Try/Catch block to catch any exceptions that may happen	*
         * when accessing the files.								*  
         ************************************************************/
        try
        {
        	/********************************************************
        	* Traverse through the template until the entire 		*
        	* document has been gone through.						*
        	********************************************************/
            while (fileReader.hasNext())
            {
            	//Reset the message string for each iteration
            	message = "";
            	//Store the line from the document into a string
                current1 = fileReader.nextLine();
                //Iterate the line for tracking
                line++;
                
                /****************************************************
                 * Only certain lines in the document needs to be	*
                 * checked to have information inserted into the	*
                 * document to be completed.						*
                 ***************************************************/
             	 if (line == 1 || line == 3 || line == 11 || line == 12 || line == 13)
                 {    
             		/***********************************************
              		 * Using the space as a delimiter, split the	*
              		 * current line into separate words to check	*
              		 * and replace for the correct values.			*
              		 ***********************************************/
                     for (String current: current1.split(" "))
                     {
                         if (current.equals("<<confirmNbr>>"))
                         {
                             current = Integer.toString(createConfirmNumber());                                 
                         }
                         else if (current.equals("<<First>>"))
                         {
                             current = res.getFirstName() + " ";
                         }
                         else if (current.contains("<<Last>>"))
                         {
                             current = res.getLastName() + ",";
                         }
                         else if(current.equals("<<checkIn>>"))
                         {
                             current = res.getDateString(res.getInDate()) + " ";
                         }
                         else if(current.equals("<<checkOut>>"))
                         {
                             current = res.getDateString(res.getOutDate());
                         }
                         else if(current.equals("<<roomNum>>"))
                         {
                             current = Integer.toString(res.getRoom());
                         }
                         else if(current.equals("<<cost>>"))
                         {
                             current = "$" + (115 * res.getReservationDates().size());
                         }
                         /*******************************************
                          * Using <> or <1> to indicate that a new 	*
                          * line needs to be created.				*
                          * <> indicates 2 new lines				*
                          * <1> indicates only 1 new line			*
                          *******************************************/
                         else if(current.equals("<>"))
                         {
                        	 current = "";
                        	 bw.newLine();
                        	 bw.newLine();
                         }
                         else if(current.equals("<1>"))
                         {
                         	current = "";
                         	bw.newLine();
                         }
                         else
                         {
                             current += " ";
                         }
                         
                         message = current;
                     
                         bw.write(message);
                     }
                 }
                 else
                 {
                	 /***********************************************
               		 * Using the space as a delimiter, split the	*
               		 * current line into separate words to check	*
               		 * and insert new lines where necessary.		*
               		 * <> indicates 2 new lines						*
                     * <1> indicates only 1 new line				*		
               		 ***********************************************/
                	 for (String current : current1.split(" ") )
                	 {
                		 
                    	 if (current.equals("<>"))
                    	 {
                    		 current = "";
                    		 bw.newLine();
                    		 bw.newLine();
                    	 }
                    	 else if(current.equals("<1>"))
                         {
                         	current = "";
                         	bw.newLine();
                         }
                    	 else
                    	 {
                    		current += " ";
                    	 }
                    	 
                    	 message = current;
                    	 bw.write(message);
                	 }
                 }
            }

            //Close the writer
            bw.close();

            
        } catch (IOException e) 
        {

		e.printStackTrace();
        }                    
	}
	
	/********************************************************************
	* Method which creates a message to the user with the confirmation	*
	* that their reservation has been canceled.							*
	********************************************************************/
	public void confirmDelete (Reservation res) throws IOException
	{
		//current word that is being checked from template document
		String current1 = "";	
		//String that holds one line of the message to be written to the new file
        String message = "";
        //line counter
        int line = 0;
        
        /****************************************
        * Open the template file for editing	*
        ****************************************/
        File tempFile = new File("res/delete_template.txt");
        Scanner fileReader = new Scanner(tempFile);
        
        /************************************************************
        * Create new file to copy the deleted confirmation message	*
        * to. The current reservation id is used to give unique		*
        * identification to the file.								*
        ************************************************************/
        String filename = "res/delete" + res.getLastName() + ".txt";
        BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
        
        /************************************************************
        * Try/Catch block to catch any exceptions that may happen	*
        * when accessing the files.									*  
        ************************************************************/
        try
        {
        	/********************************************************
        	* Traverse through the template until the entire 		*
        	* document has been gone through.						*
        	********************************************************/
            while (fileReader.hasNext())
            {
            	//Reset the message string for each iteration
            	message = "";
            	//Store the line from the document into a string
                current1 = fileReader.nextLine();
                //Iterate the line for tracking
                line++;
                
                 /***************************************************
                 * Only two lines in the document needs to be		*
                 * checked to have information inserted into the	*
                 * document to be completed.						*
                 ***************************************************/
             	 if (line == 1 || line == 3)
                 {   
             		 /***********************************************
             		 * Using the space as a delimiter, split the	*
             		 * current line into separate words to check	*
             		 * and replace for the correct values.			*
             		 ***********************************************/
                     for (String current: current1.split(" "))
                     {
                         if (current.equals("<<confirmNbr>>"))
                         {
                             current = Integer.toString(createConfirmNumber());                                 
                         }
                         else if (current.equals("<<First>>"))
                         {
                             current = res.getFirstName() + " ";
                         }
                         else if (current.contains("<<Last>>"))
                         {
                             current = res.getLastName() + ",";
                         }   
                         /*******************************************
                         * Using <> or <1> to indicate that a new 	*
                         * line needs to be created.				*
                         * <> indicates 2 new lines					*
                         * <1> indicates only 1 new line			*
                         *******************************************/
                         else if(current.equals("<>"))
                         {
                        	 current = "";
                        	 bw.newLine();
                        	 bw.newLine();
                         }
                         else if(current.equals("<1>"))
                         {
                         	current = "";
                         	bw.newLine();
                         }
                         else
                         {
                             current += " ";
                         }
                         
                         //Write line to text document
                         message = current;                         
                         bw.write(message);
                     }
                 }
                 else
                 {
                	 /***********************************************
              		 * Using the space as a delimiter, split the	*
              		 * current line into separate words to check	*
              		 * and insert new lines where necessary.		*
              		 * <> indicates 2 new lines						*
                     * <1> indicates only 1 new line				*		
              		 ***********************************************/
                	 for (String current : current1.split(" ") )
                	 {
                		 
                    	 if (current.equals("<>"))
                    	 {
                    		 current = "";
                    		 bw.newLine();
                    		 bw.newLine();
                    	 }
                    	 else if(current.equals("<1>"))
                         {
                         	current = "";
                         	bw.newLine();
                         }
                    	 else
                    	 {
                    		current += " ";
                    	 }
                    	 
                    	 //Write message to the text document
                    	 message = current;
                    	 bw.write(message);
                	 }
                 }
            }
            
            //Close the writer
            bw.close();

            
        } catch (IOException e) 
        {

		e.printStackTrace();
        }
        
	}   
	
    /************************************************************
    * This method creates a random confirmation number for 		*
    * each created message for the user.						*    
    ************************************************************/
	private int createConfirmNumber()
	{	
	    Random rand = new Random();
	    int confirmNumber = rand.nextInt((200000-100000) + 100000);
	       
	    return confirmNumber;
	}
	
}


// Byron Burdette
// COP2552.0M1



/*********************************************************************************************************************************************************************
**********************************************************************************************************************************************************************
**********************************************************************************************************************************************************************
                                                THIS MARKS THE BEGINNING OF THE COMPILATION UNIT FOR FILE THE "ZooManager.java"
**********************************************************************************************************************************************************************
**********************************************************************************************************************************************************************
**********************************************************************************************************************************************************************/






import java.util.*;                                                                                    //    import java.util package
import java.awt.*;                                                                                    //    imports all AWT components
import java.awt.event.*;                                                                            //    imports the AWT handler events
import java.awt.geom.AffineTransform;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.*;
import java.nio.file.StandardCopyOption.*;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.io.StringReader;
import java.nio.file.Files;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;









/*********************************************************************************************************************************************************************
**********************************************************************************************************************************************************************
                                                THIS MARKS THE BEGINNING OF THE CLASS DEFINITION FOR THE CLASS "ZooManager"
**********************************************************************************************************************************************************************
**********************************************************************************************************************************************************************/
class ZooManager                                                                    //    begin class declaration
{
    private Frame mainFrame;                                                    //    instantiate Frame "mainFrame"
    private Label headerLabel;                                                    //    instantiate a label that will display instructions for the user
    private Label resultLabel;                                                    //    instantiate a label that will display the results of the operation for the user
    private Panel controlPanel;                                                    //    instantiate a Panel "controlPanel"
    private Panel picturePanel;
    private TextField textField;                                                //    instantiate the textfield for user input
    private String str;                                                            //    create a string for storing user input
    private boolean flag = true;                                                //    create a flag we will use to track  button presses
    
    
    ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();        //    2d ArrayList "data" to store animals and the food they eat
    int row = 0;                                                                //    counter keeps track of the row in the ArrayList "data"
    
    /*     The user will have the option to enter multiple types of food an animal eats as one giant string. Using the String.split() method, we will separate
     *   that string up by commas to store each food individually. String "delims" holds delimiting characters to be matched by the String.split() method.  */
    String delims = "[,]+";
    
    FileDialog fileDialog = new FileDialog(mainFrame, "Select file"); 
    
    PrintWriter writer = null;
     
     
     
/*********************************************************************************************************************************************************************
                                                        THIS MARKS THE BEGINNING OF THE DEFAULT CONSTRUCTOR

                                    The purpose of this method is to call the function prepareGUI() to force the GUI to set up
**********************************************************************************************************************************************************************/ 
     public ZooManager()                                                            //    begin method definition for default constructor
     {
         prepareGUI();                                                            //    call "prepareGUI" to set up the GUI
         readFile();
     }                                                                            //    end method definition
/*********************************************************************************************************************************************************************
                                                        THIS MARKS THE END OF THE DEFAULT CONSTRUCTOR
**********************************************************************************************************************************************************************/     
          
     
     

/*********************************************************************************************************************************************************************
                                                        THIS MARKS THE BEGINNING OF "prepareGUI" METHOD

                                                The purpose of this method is to set up the GUI framework
**********************************************************************************************************************************************************************/
     private void prepareGUI()                                                    //    begin method definition for method "prepareGUI"
        {
            mainFrame = new Frame("Simple GUI");                                //    create a new frame "Simple GUI" from Frame "mainFrame"
            
            mainFrame.setSize(1200,600);                                            //    set the size of the frame 
            mainFrame.setLayout(new GridLayout(0, 1));                            //    give the frame a grid layout with 1 column and as many rows as are required
            
            mainFrame.addWindowListener(new WindowAdapter()                        //    create a listener for the window to intercept the window closing
            {        
                public void windowClosing(WindowEvent windowEvent)                //    when the window closes...
                {
                    writeFile();
                    System.exit(0);                                                //    terminate the program without errors
                }        
            });
            
            headerLabel = new Label();                                            //    create a new label from the one instantiated earlier
            headerLabel.setAlignment(Label.CENTER);                                //    center the label
            
            resultLabel = new Label();                                            //    create a new label from the one instantiated earlier
            resultLabel.setAlignment(Label.CENTER);                                //    center the label
            
            controlPanel = new Panel();                                            //    create a new panel from the one instantiated earlier
            
            mainFrame.add(headerLabel);                                            //    add the header label to the frame in the first row
            mainFrame.add(controlPanel);                                        //    add the control panel to the frame in the second row
           // mainFrame.add(picturePanel);
            mainFrame.add(resultLabel);                                            //    add the result label to the frame in the third row
            
            mainFrame.setVisible(true);                                            //    make the frame visible
        }                                                                        //    end method definition
/*********************************************************************************************************************************************************************
                                                    THIS MARKS THE END OF THE "prepareGUI" METHOD
**********************************************************************************************************************************************************************/
     

     
     
     
     
     
     
     private void readFile() 
     {
         boolean animal = true;
         
         try (BufferedReader f = new BufferedReader(new FileReader("data.txt")))    //  try to read the input file
            {
                while ((str = f.readLine()) != null)                                                        //  store the contents of the file in string "str"
                {
                    if (animal)
                    {
                        data.add(new ArrayList<String>());                    //    add a String ArrayList to the 2D ArrayList "data"
                        data.get(row).add(str);                                //    stick the animal name in the first element of the new ArrayList
                        animal = false;
                    }
                    else
                    {
                        BufferedReader br = new BufferedReader(new StringReader(str));
                        
                        String food;
                        
                        while ((food = br.readLine()) != null)        
                            data.get(row).add(food);
                        
                        animal = true;
                        row++;
                    }
                }
            }
        catch (Exception a)                                                            //  catch the exception if we weren't able to open the file
            {
                resultLabel.setText("error. file not found. creating file...");            //  create an error message
                
                
                
                
            }
     }
     
     
     
     
     
     
     
     
     public void writeFile()
     {
         try 
             { 
                 writer = new PrintWriter("data.txt");                           //  try to create a new text file to write to
                 
                 for (int i = 0; i < data.size(); i++)
                 {
                     writer.println(data.get(i).get(0));
                     
                     for (int j = 1; j < data.get(i).size(); j++)
                     {
                         if (j == (data.get(i).size() - 1))
                             writer.println(data.get(i).get(j));
                         else
                             writer.print(data.get(i).get(j) + ' ');
                     }
                 }
                 
                 writer.close();
             }
         catch (FileNotFoundException b)                                            //  catch the exception if we are unable to create the file
            {
                String err = "Error: could not access file";                           //  create an error message
                System.out.println(err);                                               //  display the error message
                System.exit(0);                                                        //  terminate the program
            }
         
         
     }
     
     
     
     
     
     
     
     
     
     
/*********************************************************************************************************************************************************************
                                                    THIS MARKS THE BEGINNING OF "displayGUI" METHOD

                                            The purpose of this method is create the buttons and their listeners
**********************************************************************************************************************************************************************/ 
     private void displayGUI()                                                    //    begin method definition for method "displayGUI"
        {
             //        set the message for the header label
            headerLabel.setText("To add a new animal, type in the name of the animal and press \"submit\"."
                              + " Otherwise you may type in the names of multipel animals seperated by commas and press"
                              + "\"query\" to look up information on existing animals");        
            
            Button submit = new Button("submit");                                //    create the button for submitting info
            Button query = new Button ("query");                                //    create the button to query info
            textField = new TextField(16);                                        //    create the text field for user input
            Button poop = new Button("poop");
            
            submit.setActionCommand("submit");                                    //    assign button "submit" command "submit"
            query.setActionCommand("query");                                    //    assign button "query" command "query"
            
            submit.addActionListener(new ButtonClickListener());                //    assign a listener to the "submit" button
            query.addActionListener(new ButtonClickListener());                    //    assign a listener to the "query" button
            
            controlPanel.add(textField);                                        //    add the textfield to the control panel
            controlPanel.add(submit);                                            //    add the submit button to the control panel
            controlPanel.add(query);                                            //    add the query button to the control panel
            
            picturePanel = new Panel();
            controlPanel.add(picturePanel);
            
            mainFrame.setVisible(true);                                            //    make the frame visible
        }
/*********************************************************************************************************************************************************************
                                                    THIS MARKS THE END OF THE "displayGUI" METHOD
**********************************************************************************************************************************************************************/
     
     
     
     
 /*********************************************************************************************************************************************************************
  *********************************************************************************************************************************************************************
                                     THIS MARKS THE BEGINNING OF THE CLASS DEFINITION FOR THE CLASS "ButtonClickListener"

             The purpose of this class is to create the listeners for the different buttons and define the actions to take when the buttons are pressed. 
  *********************************************************************************************************************************************************************
 **********************************************************************************************************************************************************************/ 
     private class ButtonClickListener implements ActionListener                //    begin class definition for class "ButtonClickListener"
        {
         
/*********************************************************************************************************************************************************************
                                                THIS MARKS THE BEGINNING OF "actionPerformed" METHOD

                                The purpose of this method is to define what actions to take when each button is pressed
**********************************************************************************************************************************************************************/
         public void actionPerformed(ActionEvent e)                                //    begin method definition for method "actionPerformed"
            {
             str = textField.getText();                                    //    store the user's input in "str"
             
             resultLabel.setText("");                                            //    clear the text in resultLabel
             textField.setText("");
                
                String command = e.getActionCommand();                            //    store the command for the button that was pressed in String"command"
                
                if (command.equals("submit"))                                    //    if the "submit" button was pressed...
                {
                    if(flag)                                                    //    if "flag" is true, than we're ready to add a new animal to the database
                    {
                        if (!str.matches("[a-zA-Z ]*") || str.matches("[ ]+") || str.isEmpty())    //    if the input is empty, just blank spaces or contains non alphanumeric characters...
                            headerLabel.setText("Invalid input. Input must contain alphanumeric characters only. Please re-enter input");    //    display an error message and prompt the user to re-enter input
                        else                                                    //    otherwise, we've got good input
                        {
                            str = str.toLowerCase();                            //    transform the user's input to all lowercase letters
                            str = str.replaceAll("\\s+", " ");                    //    replace multiple spaces with a single space
                            str = str.trim();                                    //    strip out leading and trailing spaces
                            
                            for (int i = 0; i < data.size(); i++)
                            {
                            	if (str.equals(data.get(i).get(0)))
                            	{
                            		data.remove(data.get(i));
                            		row--;
                            	}
                            }
                            
                            data.add(new ArrayList<String>());                    //    add a String ArrayList to the 2D ArrayList "data"
                            data.get(row).add(str);                                //    stick the animal name in the first element of the new ArrayList
                            
                            headerLabel.setText("now enter the different foods it eats seperated by commas and press \"submit\"");    //    instruct the user on the next step to take
                            
                            flag = false;                                        //    set the flag to false to prevent the user from doing anything besides adding different foods to the array
                        }
                    }
                    else                                                        //    submit button was pressed but now it's time to add what food the animal eats
                     {
                         str = str.toLowerCase();                                //    transform the user's input to all lowercase letters
                         
                         String[] food = str.split(delims);                        //    split the input into individual strings each containing a different food, and store all the strings in the array "food"
                         
                         for (int i = 0; i < food.length; i++)                    //    for each different food
                         {
                             food[i] = food[i].replaceAll("\\s+", " ");            //    replace multiple spaces with a single space
                             food[i] = food[i].trim();                            //    strip out leading and trailing spaces
                             
                             if (!food[i].isEmpty() && !food[i].matches("[ ]+"))    //    if the string holding this food is NOT empty or NOT just some blank spaces...
                                 data.get(row).add(food[i]);                    //    then add it on the end of the row representing the current animal in the ArrayList "data"
                         }
                         
                         flag = true;                                            //    reset the flag so that we can add new animals
                         
                         fileDialog.setVisible(true);
                         
                         Path from = Paths.get(fileDialog.getDirectory() + fileDialog.getFile());
                         Path to = Paths.get("resources\\" + data.get(row).get(0) + ".jpg");
                         
                         try { Files.copy(from, to, REPLACE_EXISTING); } 
                         catch (IOException e1) { }
                         
                         /*  we're finished with this animal, and consequently the current row in the 2D ArrayList "data". Increment the int counter "row" because
                          *  next time we're here, it'l be because we're adding a new animal's data on a new row. This makes it easy to reference the next row    */
                         row++;
                         
                         //        reset the message for the header label
                         headerLabel.setText("To add a new animal, type in the name of the animal and press \"submit\"."
                                          +  " Otherwise you may type in the names of multipel animals seperated by commas and press"
                                          +  "\"query\" to look up information on existing animals");
                     }
                }
                else if (flag)                                                    //    otherwise, the query button was pushed so...
                {
                    boolean match = false;                                        //    create a flag representing the existence of a matching name in the ArrayList "data"
                     
                    String results = "";                                        //    create an empty string to store the results of the query
                    
                    str = str.toLowerCase();                                    //    transform the user's input to all lowercase letters
                    
                    String[] names = str.split(delims);                            //    split the input into individual strings each containing a different animal name and store all the names in the array "names" 
                    
                    String animal = "";
                    
                     for (int i = 0; i < names.length; i++)                        //    for each different name...
                     {
                         
                         names[i] = names[i].replaceAll("\\s+", " ");            //    replace multiple spaces with a single space for the current query
                         names[i] = names[i].trim();                            //    strip out leading and trailing spaces for the current query
                         
                         if (names[i].matches("[ ]+") || names[i].isEmpty())    //    if the current name being queried is empty...
                             continue;                                            //    then skip it and move on to the next name to be queried
                         
                         for (int j = 0; j < data.size(); j++)                    //    check each name held in the ArrayList "data"
                             if (names[i].equals(data.get(j).get(0)))            //    if the current name being queried matches the current name in the ArrayList "data"
                             {
                                 animal = names[i];
                                 results += names[i] + " food: ";                //    add the animal to the results
                                 for (int k = 1; k < data.get(j).size(); k++)    //    for every type of food this animal eats...
                                 {
                                     results += data.get(j).get(k);                //    add the food to the results
                                     if (!(k+1 == data.get(j).size()))            //    as long as it's not the last food stored in the ArrayList "data"...
                                         results += ", ";                        //    print a comma followed by a space
                                 }
                                 results += "    ";                                //    space out the different animals
                                 match = true;                                    //    If we've made it this far, then the query returned a match.
                             }    
                         
                         if (!match)                                                                            //    if the query returned no matches...
                             resultLabel.setText("Error. \"" + names[i] + "\" doesn't exist in the database.");    //    print an error message
                         else                                                                                    //    otherwise...
                         {
                             picturePanel.removeAll();                                                        //  remove the image currently displayed on the panel
                             resultLabel.setText(results);                                                        //    print the results of the query
                             picturePanel.add(new ImageComponent("resources\\" + animal + ".jpg"));
                             mainFrame.setVisible(true);

                         }
                    }                                                                                        //    end of the loop
                     }                                                                                            //    end of the if statement
                }                                                                                                //    end of the function definition
 /*********************************************************************************************************************************************************************
                                                        THIS MARKS THE END OF THE "actionPerformed" METHOD
**********************************************************************************************************************************************************************/
            
        
        
        
        
        
         class ImageComponent extends Component                                                   //  begin class definition
           {
              BufferedImage img;                                                                    //  create a placeholder for the buffered image
              
              public ImageComponent(String path)                                                    //  constructor loads the image from the filepath
              {
                 try { img = ImageIO.read(new File(path)); }                                        //  try to load the image
                 catch (IOException e) { e.printStackTrace(); }                                     //  if it fails, print out the error
              }
              
              public Dimension getPreferredSize()                                                   //  set the dimensions of the image
              {
                 if (img == null)                                                                   //  if there is not image
                    return new Dimension(300,300);                                                  //  default to 300 x 300
                 else                                                                               //  otherwise
                    return new Dimension(img.getWidth(), img.getHeight());                          //  set the dimensions to the dimensions of the image
              }
              
              //BufferedImage i = scale(img, 4, img.getWidth()*.5, img.getHeight()*.5, .5, .5);
              
              public void paint(Graphics g) { g.drawImage(img, 0, 0, null); }                       //  draw the image
           }                                                                                        //  end class definition
         
        
        
        
         
         
         
         
         
         
         
         
         public BufferedImage scale(BufferedImage sbi, int imageType, int dWidth, int dHeight, double fWidth, double fHeight)
         {
        	    BufferedImage dbi = null;
        	    if(sbi != null) {
        	        dbi = new BufferedImage(dWidth, dHeight, imageType);
        	        Graphics2D g = dbi.createGraphics();
        	        AffineTransform at = AffineTransform.getScaleInstance(fWidth, fHeight);
        	        g.drawRenderedImage(sbi, at);
        	    }
        	    return dbi;
        	}
         
         
         
         
         
         
         
        
        
        
        
        }                                                                                                    //    end of the class definition
 /*********************************************************************************************************************************************************************
 **********************************************************************************************************************************************************************
                                             THIS MARKS THE BEGINNING OF THE CLASS DECLARATION FOR THE CLASS "ButtonClickListener"
 **********************************************************************************************************************************************************************
 **********************************************************************************************************************************************************************/
         
        
     
     
    
     
     
     
     
     
     
     
/*********************************************************************************************************************************************************************
                                                                THIS MARKS THE BEGINNING OF MAIN METHOD
**********************************************************************************************************************************************************************/
     
     public static void main(String[] args)                                        //    begin main method definition
        {
            ZooManager gui = new ZooManager();                                            //    create a new object "gui" of type "ZooManager"
            
            gui.displayGUI();                                                    //    display the GUI
        }                                                                        //    end main method definition
/*********************************************************************************************************************************************************************
                                                                THIS MARKS THE END OF THE MAIN METHOD
**********************************************************************************************************************************************************************/
}
/*********************************************************************************************************************************************************************
**********************************************************************************************************************************************************************
                                                THIS MARKS THE END OF THE CLASS DECLARATION FOR THE CLASS "SimpleGUI"
**********************************************************************************************************************************************************************
**********************************************************************************************************************************************************************/




/*********************************************************************************************************************************************************************
**********************************************************************************************************************************************************************
**********************************************************************************************************************************************************************
                                                THIS MARKS THE END OF THE COMPILATION UNIT FOR FILE THE "ZooManager.java"
**********************************************************************************************************************************************************************
**********************************************************************************************************************************************************************
**********************************************************************************************************************************************************************/
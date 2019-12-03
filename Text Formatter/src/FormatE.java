import java.util.*;
/**
 *
 * @author Kyle Benda
 */
public class FormatE {
    final private int maxLineLen = 120;
    private int lineLen;
    int justif = 0;         //poor man's enum:
                            //justification 0 left, 1 center, 2 right
    boolean equal = false;  //equally space across line or not;
    boolean wrapping = false;   //wrapping?
    boolean doubleSpace = false;    //double space or not
    boolean qTitle = false;         //check immediately
    //paragraph - happens once or multiple times?
    int parSpace = 0;               //number of paragraph spaces
    int blanks = 0;                 //check immediately
    boolean twoCol = false; //two columns or not.
    private ArrayList<String> output = new ArrayList<String>();
    private ArrayList<String> errors = new ArrayList<String>();
    private String wrapBuf = "";    //wrapping buffer
    
    //modifies control for all commands but paragraph
    void proCom(String comm){
        String temp = comm.substring(1);
        int ret = 0;
        boolean error = false;
        switch(temp.charAt(0)){
            case 'n':
                temp = temp.substring(1);
                ret = 0;
                //int old = lineLen;
                error = false;
                try{
                    ret = Integer.parseInt(temp);
                } catch (Exception e){
                    error = true;
                    //put error on error arraylist
                }
                if (!error){
                    lineLen = ret;
                }
                break;
            case 'r':
                //right just
                if (temp.equals("r")){
                    justif = 2;
                } else {
                    //print error
                }
                break;
            case 'l':
                //left just
                if (temp.equals("l")){
                    justif = 0;
                } else {
                    //print error
                }
                break;
            case 'c':
                //center just
                if (temp.equals("c")){
                    justif = 1;
                } else {
                    //print error
                }
                break;
            case 'e':
                //equal spacing
                if (temp.equals("e")){
                    equal = true;
                } else {
                    //print error
                }
                break;
            case 'w':
                //wrapping or not
                temp = temp.substring(1);
                if (temp.equals("+")){
                    wrapping = true;
                } else if (temp.equals("-")){
                    wrapping = false;
                } else {
                    //error
                }
                break;
            case 's':
                //single space
                if (temp.equals("s")){
                    doubleSpace = false;
                } else {
                    //print error
                }
                break;
            case 'd':
                //double space
                if (temp.equals("d")){
                    doubleSpace = true;
                } else {
                    //print error
                }
                break;
            case 't':
                //title
                if (temp.equals("t")){
                    qTitle = true;
                } else {
                    //print error
                }
                break;
            case 'p':
                //paragraph spacing
                //handle it
                break;
            case 'b':
                //number of blank lines
                temp = temp.substring(1);
                ret = 0;
                error = false;
                try{
                    ret = Integer.parseInt(temp);
                } catch (Exception e){
                    error = true;
                }
                if (!error){
                    if (ret > 0){
                        blanks = ret;
                    } else {
                        //write error
                    }
                } else {
                    //write error
                }
                break;
            case 'a':
                //number of columns
                temp = temp.substring(1);
                ret = 0;
                error = false;
                try{
                    ret = Integer.parseInt(temp);
                } catch (Exception e){
                    error = true;
                }
                if (!error){
                    if (ret == 1){
                        twoCol = false;
                    } else if (ret == 2){
                        twoCol = true;
                    } else {
                        //write error
                    }
                } else {
                    //write error
                }
                break;
            default:
                //couldnt understand command error
        }
    }
    
    void makeBlanks(int blankLines){
        for (int i = 0; i < blankLines; i++){
            output.add("\n");     //a blank line with nothing on/in it?
        }
    }
    
    void makeTitle(String formatMe){
        int spaces = (lineLen-formatMe.length())/2;
        String outStr = "";
        for (int i = 0; i < spaces; i++){
            outStr = outStr+" ";
        }
        output.add(outStr+formatMe);
        for (int i = 0; i < formatMe.length(); i++){
            outStr = outStr+"-";
        }
        output.add(outStr);
    }
    
    void handleStr(String formatMe){
        //all of the other reindeer i uh mean formatting
    }
    
    //handles it
    void handleIt(ArrayList<String> input){
        String temp = "";
        boolean collecting = false;
        for (int i = 0; i < input.size(); i++){
            String inTem = input.get(i);
            //we now have a string - check if command
            if (inTem.charAt(0) == '-'){
                //a command/control string - handle it
                proCom(inTem);
                if (qTitle){
                    i++;
                    if (i < input.size()){
                        inTem = input.get(i);
                        if (inTem.charAt(0) == '-'){
                            //error/warning
                        }
                        if (inTem.length() > lineLen){
                            //error/warning
                        }
                        makeTitle(inTem);
                    }
                } else if (blanks > 0){
                    makeBlanks(blanks);
                    blanks = 0;
                }
            } else {
                //is it too big/too small?
                //we'll chop up too-large strings, and put them out line by line
                temp = temp+inTem;
                if (temp.length()+parSpace > lineLen){
                    handleStr(temp.substring(0, lineLen));
                    temp = temp.substring(lineLen);
                } else if (temp.length()+parSpace <= lineLen && wrapping == false){
                    handleStr(temp);
                    temp = "";
                } else if (temp.length()+parSpace <= lineLen && wrapping == true){
                    //get another line.. unless the next line is a command line
                    //or there is no next line, then print
                    if (i+1 < input.size()){
                        if (input.get(i+1).charAt(0) == '-'){
                            handleStr(temp);
                            temp = "";
                        }
                    } else {
                        handleStr(temp);
                        temp = "";
                    }
                }
                //if too small, we'll go collect more and append
                
            }
        }
    }
    
}

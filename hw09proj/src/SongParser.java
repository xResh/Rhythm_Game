import java.util.*;
import java.awt.Color;
import java.io.*;

public class SongParser {
	private BufferedReader in;
	public List<StoredNote> SHEET = new LinkedList<>();
	private int counter = 0;
	private int num = 0;

  @SuppressWarnings("serial")
  public static class FormatException extends Exception {
    public FormatException(String msg) {
      super(msg);
    }
  }

  
  public SongParser(Reader r) throws IOException, FormatException {
	  if (r == null) {
		  throw new IllegalArgumentException();
	  }
	  in = new BufferedReader(r);
	  String str = in.readLine();
	  try {
		  while(!(str == null)){
			  str = str.trim();
			  str = str.toLowerCase();
			  if (str.equals("")){
				  str = in.readLine();
			  }
			  else {
				  if (!isValid(str)){
					  throw new FormatException("bad format");
				  }
				  str = str.trim();
				  int coma = str.lastIndexOf(',');
				  int val1 = 0;
				  int val2 = -1;
				  String beforecoma = str.substring(0,coma);
				  if (beforecoma.contains("-")){
					  try {
						  int dash = beforecoma.lastIndexOf('-');
						  val1 = Integer.parseInt(beforecoma.substring(0, dash).trim());
						  val2 = Integer.parseInt(beforecoma.substring(dash + 1).trim());
						  if (val1 > val2){
							  throw new NumberFormatException();
						  }
						  num = num+2;
						  //System.out.println(val1 + "'" + val2);
					  }
					  catch (NumberFormatException e) {
						  throw new FormatException("bad format");
					  }
				  }
				  else {
					  try {
						  val1 = Integer.parseInt(str.substring(0,coma).trim());
						  num++;
						  //System.out.println(val1);
					  }
					  catch (NumberFormatException e) {
						  throw new FormatException("bad format");
					  }
				  }
				  if (val1 < counter) {
					  throw new FormatException("bad format");
				  }
				  else {
					  counter = val1;
				  }
				  char k = str.charAt(str.length()-1);
				  //line has been validated now
				  SHEET.add(new StoredNote(val1, val2, k));
				  str = in.readLine();
			  }
		  }
	  } catch (IOException e) {
	  }
  }

  /** Construct a FileCorrector from a file.
   *
   * @param filename of file to read from
   * @throws IOException if error while reading
   * @throws FileCorrector.FormatException for an invalid line
   * @throws FileNotFoundException if file cannot be opened
   */
  public static SongParser make(String filename) throws IOException, FormatException {
	  Reader r = new FileReader(filename);
	  SongParser fc;
	  try {
			fc = new SongParser(r);
	  } finally {
	      if (r != null) { r.close(); }
     }
	  return fc;
  }
  
  public int getNum(){
	  return this.num;
  }
  
  public static boolean isValid (String line){
//	  if (line == null || line.equals("")){
//		  return false;
//	  }
	  line = line.trim();
	  if (!line.contains(",")){
		  return false;
	  }
	  int ind = line.lastIndexOf(',');
	  if (ind == 0 || ind == (line.length()-1)){
		 return false; 
	  }
	  String after = line.substring(ind+1);
	  return (after.equals("q") || after.equals("w") || after.equals("e") || 
			  after.equals("i") || after.equals("o") || after.equals("p"));
  }

  public List<StoredNote> getSheet() {
  	return SHEET;
  }
}

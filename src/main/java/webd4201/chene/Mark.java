package webd4201.chene;

import java.text.DecimalFormat;

public class Mark {

	/**
	 * According to the requirements, the class will set the mark to its value based on what the student gets and on its program.
	 */
	public final float MINIMUM_GPA = 0.0f;
	public final float MAXIMUM_GPA = 5.0f;
	public final DecimalFormat GPA = new DecimalFormat("00.##");
	
	private String courseCode;
	private String courseDescription;
	private int result;
	private float GPAWeighting;
	
	//parameterized constructor
	public Mark(String CourseCode, String CourseName, int finalresults, float GPAweight) {
		setCourseCode(CourseCode);
		setCourseDescription(CourseName);
		setresult(finalresults);
		setGPAweighting(GPAweight);
	}
	
	//start of setters
	public void setCourseCode(String coursecode)
	{
		courseCode = coursecode;
	}
	
	public void setCourseDescription(String coursedesc)
	{
		courseDescription = coursedesc;
	}
	
	public void setresult(int finalresult)
	{
		result = finalresult;
	}
	
	public void setGPAweighting(float GPAWeight)
	{
		GPAWeighting = GPAWeight;
	}
	//end of setters
	
	
	//start of getters
	public String getCourseCode()
	{
		return courseCode;
	}
	
	public String getCourseDescription()
	{
		return courseDescription;
	}
	
	public int getresult()
	{
		return result;
	}
	
	public float getGPAweighting()
	{
		return GPAWeighting;
	}
	//end of getters
	
	
	public String toString()
	{
		return courseCode + " " + courseDescription + " " + result + " " + GPAWeighting;
	}
}

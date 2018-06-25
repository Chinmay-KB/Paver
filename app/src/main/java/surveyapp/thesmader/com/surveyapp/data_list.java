package surveyapp.thesmader.com.surveyapp;

public class data_list {
    private String subjectCode;
    private String year;
    private String semester;
    public data_list()
    {}
    public data_list(String subjectCode,String year,String semester)
    {
        this.subjectCode=subjectCode;
        this.year=year;
        this.semester=semester;
    }
    public String getCode()
    {
        return subjectCode;
    }
    public void setCode(String subjectCode)
    {
        this.subjectCode=subjectCode;
    }
    public String getYear()
    {
        return year;
    }
    public void setYear(String year)
    {
        this.year=year;
    }
    public String getSemester()
    {
        return semester;
    }
    public void setSemester(String semester)
    {
        this.semester=semester;
    }
}

import java.util.ArrayList;


public abstract class ElPart
{
	private String name;
	private double value;


	public ElPart()
	{}

	public ElPart(double value)
	{
		this.value = value;
	}

	protected void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	//method for scanning numbers with SI suffixes
	public static double scanValue()
    {
        String s = evidence.scan.nextLine();
        if(s.length()==0)
        	throw new java.util.InputMismatchException();								
        char suffix = s.substring(s.length()-1).charAt(0);					//copy last character (suffix)
        double value;

        if(Character.isDigit(suffix))
        {
        	value=Double.parseDouble(s);
        	if(value<0)
        		throw new java.util.InputMismatchException();				//because negative values are irrelevant
        	return Double.parseDouble(s);									//return normally if not suffix
        }

        if(s.length()<=1)
        	throw new java.util.InputMismatchException();

        value = Double.parseDouble(s.substring(0, s.length()-1));			//copy value without suffix
        if(value<0)
        		throw new java.util.InputMismatchException();				//because negative values are irrelevant
        switch(suffix)														//multiply or divide depending on suffix
        {
            case 'T': value*=1000;
            case 'G': value*=1000;
            case 'M': value*=1000;
            case 'k': value*=1000;
                break;
            case 'p': value/=1000;
            case 'n': value/=1000;
            case 'u': value/=1000;
            case 'm': value/=1000;
                break;
        }
        return value;
    }

	public void setValue(double value)
	{
		this.value = value;
	}

	public double getValue()
	{
		return this.value;
	}

	//returns new part of desired type
	public static ElPart factory(String type)
	{
		switch(type.toLowerCase())
		{
			case "r": return (ElPart) new Resistor();
			case "c": return (ElPart) new Capacitor();
			case "l": return (ElPart) new Inductor();

			default : evidence.printErr("Zadaný typ neexistuje\n"); return null;
		}
	}

	public abstract ArrayList<Object> getAllParams();
	
}
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

	public static double scanValue()
    {
        String s = evidence.scan.nextLine();
        char suffix = s.substring(s.length()-1).charAt(0);

        if(!Character.isDigit(suffix))
        	return Double.parseDouble(s);

        double value = Double.parseDouble(s.substring(0, s.length()-1));
        switch(suffix)
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

	public static ElPart factory(String type, long id)
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
import java.util.ArrayList;


public abstract class ElPart
{
	private double value;
	private String name;

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

	public void setValue(double value)
	{
		this.value = value;
	}

	public double getValue()
	{
		return this.value;
	}

	public static ElPart factory(String type)
	{
		switch(type.toLowerCase())
		{
			case "r": return (ElPart) new Resistor();
			case "c": return (ElPart) new Capacitor();
			//case "l": return (ElPart) new Inductor();
			default : return null;	
		}
	}

	public abstract ArrayList<Object> getAllParams();
	
}
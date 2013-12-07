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
			case "r": return (ElPart) new Resistor(id);
			case "c": return (ElPart) new Capacitor(id);
			//case "l": return (ElPart) new Inductor();
			default : return null;	
		}
	}

	public abstract ArrayList<Object> getAllParams();
	
}
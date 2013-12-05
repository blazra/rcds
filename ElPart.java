import java.util.ArrayList;


public abstract class ElPart
{
	//static final long serialVersionUID=42;
	private double value;

	public ElPart()
	{}

	public ElPart(double value)
	{
		this.value = value;
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
			//case "c": return (ElPart) new Capacitor();
			//case "l": return (ElPart) new Inductor();
			default : return null;	
		}
	}

	public abstract ArrayList<Object> getAllParams();
	
}
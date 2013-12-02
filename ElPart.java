import java.util.ArrayList;


public abstract class ElPart
{
	static final long serialVersionUID=42;
	double value;

	ElPart()
	{}

	ElPart(double value)
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

	//public static ElPart factory()
	//{}

	public abstract ArrayList<Object> getAllParams();
	
}
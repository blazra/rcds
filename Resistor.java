import java.util.ArrayList;

public class Resistor extends ElPart
{
	String material = "carbon";
	double powerRating = 0.25;	//in W
	double tolerance = 5;		//in percents

	Resistor(double value, double powerRating, double tolerance)
	{
		super(value);
		this.powerRating = powerRating;
		this.tolerance = tolerance;
	}

	public ArrayList<Object> getAllParams()
	{
		ArrayList<Object> list = new ArrayList<>();
		list.add(getValue());
		list.add(getPowerRating());
		list.add(getTolerance());
		list.add(getMaterial());
		return list;
	}

	public double getPowerRating()
	{
		return powerRating;
	}

	public double getTolerance()
	{
		return tolerance;
	}

	public String getMaterial()
	{
		return material;
	}
}

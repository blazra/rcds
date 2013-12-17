import java.util.ArrayList;

public class Inductor extends ElPart
{
	protected double maxCurrent;
	protected double tolerance = 5;

	public Inductor()
	{
		setName("Cívka");
		System.out.print("Zadej hodnotu v Henry: ");
		setValue(scanValue());	
		System.out.print("Zadej maximální povolený proud v Ampérech: ");
		setMaxCurrent(scanValue());
		System.out.print("Zadej toleranci v % (0 pro výchozích "+tolerance+"% ): ");
		setTolerance(scanValue());
	}

	public ArrayList<Object> getAllParams()
	{
		ArrayList<Object> list = new ArrayList<>();
		list.add(getName()+"\t");
		list.add(getValue()+"H");
		list.add(getMaxCurrent()+"A");
		list.add(getTolerance()+"%");
		return list;
	}

	public void setMaxCurrent(double maxCurrent)
	{
		this.maxCurrent=maxCurrent;
	}

	public double getMaxCurrent()
	{
		return maxCurrent;
	}

	public void setTolerance(double tolerance)
	{
		if(tolerance!=0)
			this.tolerance = tolerance;
	}

	public double getTolerance()
	{
		return tolerance;
	}
}
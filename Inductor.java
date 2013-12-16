import java.util.ArrayList;

public class Inductor extends ElPart
{
	protected double maxCurrent;

	public Inductor()
	{
		setName("Cívka");
		System.out.print("Zadej hodnotu v Henry: ");
		setValue(scanValue());	
		System.out.print("Zadej maximální povolený proud v Ampérech: ");
		setMaxCurrent(scanValue());
	}

	public ArrayList<Object> getAllParams()
	{
		ArrayList<Object> list = new ArrayList<>();
		list.add(getName());
		list.add(getValue()+"H");
		list.add(getMaxCurrent()+"A");
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
}
import java.util.ArrayList;


public class Capacitor extends ElPart
{
	double value;
	double maxVoltage;
	String dielectric;
	double tolerance;

	Capacitor(double value, double maxVoltage, double tolerance, String dielectric)
	{
		super(value);
		this.maxVoltage = maxVoltage;
		this.tolerance = tolerance;
		setDielectric(dielectric);
	}

	public ArrayList<Object> getAllParams()
	{
		ArrayList<Object> list = new ArrayList<>();
		list.add(getValue());
		list.add(getMaxVoltage());
		list.add(getDielectric());
		list.add(getTolerance());
		return list;
	}

	public void setDielectric(String dielectric)
	{
		this.dielectric = dielectric;
	}

	public String getDielectric()
	{
		return dielectric;
	}

	public double getTolerance()
	{
		return tolerance;
	}

	public double getMaxVoltage()
	{
		return maxVoltage;
	}
	
}
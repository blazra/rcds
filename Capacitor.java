import java.util.ArrayList;
import java.util.Arrays;


public class Capacitor extends ElPart
{
	protected ArrayList<String> dielectrics = new ArrayList<String>(Arrays.asList("neznámý", "keramický", "elektrolytický", "fóliový", "tantalový"));

	protected String dielectric=dielectrics.get(0);
	protected double maxVoltage;
	protected double tolerance = 5;

	public Capacitor()
	{
		setName("Kondenzátor");
		System.out.print("Zadej hodnotu ve Faradech: ");
		setValue(scanValue());	
		System.out.print("Zadej maximální povolené napětí ve Voltech: ");
		setMaxVoltage(scanValue());
		System.out.print("Zadej toleranci v % (0 pro výchozích "+tolerance+"% ): ");
		setTolerance(scanValue());
		System.out.print("Zadej typ dielektrika (číslo) - ");
		setDielectric();
	}

	public ArrayList<Object> getAllParams()
	{
		ArrayList<Object> list = new ArrayList<>();
		list.add(getName());
		list.add(getValue()+"F");
		list.add(getMaxVoltage()+"V");
		list.add(getDielectric());
		list.add(getTolerance()+"%");
		return list;
	}

	public void setDielectric()
	{
		System.out.println("možnosti jsou: ");
		for(int i=0;i<dielectrics.size();i++)
		{
			System.out.print(i+" "+dielectrics.get(i)+", ");
		}
		System.out.println();
		setDielectric(dielectrics.get(evidence.scan.nextInt()));
	}

	public void setDielectric(String dielectric)
	{
		this.dielectric = dielectric;
	}

	public String getDielectric()
	{
		return dielectric;
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

	public void setMaxVoltage(double maxVoltage)
	{
		this.maxVoltage = maxVoltage;
	}

	public double getMaxVoltage()
	{
		return maxVoltage;
	}
	
}
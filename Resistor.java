import java.util.ArrayList;
import java.util.Arrays;


public class Resistor extends ElPart
{
	protected ArrayList<String> materials = new ArrayList<String>(Arrays.asList("neznámý", "uhlíkový", "metalizovaný", "metal-oxid", "drátový"));
	
	protected String material = materials.get(0); 	//get default from the first element
	protected double powerRating = 0.25;			//in W
	protected double tolerance = 5;					//in percents

	public Resistor()
	{
		setName("Rezistor");
		System.out.print("Zadej hodnotu v Ohmech: ");
		setValue(scanValue());	
		System.out.print("Zadej maximální ztrátový výkon ve Wattech (0 pro výchozích "+powerRating+"W ): ");
		setPowerRating(scanValue());
		System.out.print("Zadej toleranci v % (0 pro výchozích "+tolerance+"% ): ");
		setTolerance(scanValue());
		System.out.print("Zadej typ materiálu (číslo) - ");
		setMaterial();
	}

	public ArrayList<Object> getAllParams()
	{
		ArrayList<Object> list = new ArrayList<>();
		list.add(getName());
		list.add(getValue()+"R");
		list.add(getPowerRating()+"W");
		list.add(getTolerance()+"%");
		list.add(getMaterial());
		return list;
	}

	public void	setPowerRating(double powerRating)
	{
		if(powerRating!=0)
			this.powerRating = powerRating;
	}

	public double getPowerRating()
	{
		return powerRating;
	}

	public void	setTolerance(double tolerance)
	{
		if(tolerance!=0)
			this.tolerance = tolerance;
	}

	public double getTolerance()
	{
		return tolerance;
	}

	public void setMaterial()
	{
		System.out.println("možnosti jsou: ");
		for(int i=0;i<materials.size();i++)
		{
			System.out.print(i+" "+materials.get(i)+", ");
		}
		System.out.println();
		setMaterial(materials.get(evidence.scan.nextInt()));
	}

	public void setMaterial(String material)
	{
		this.material = material;
	}

	public String getMaterial()
	{
		return material;
	}
}

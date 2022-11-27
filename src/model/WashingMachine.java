package model;

public class WashingMachine {
    public Color color;
    public int temperature;
    public String powdertype;
    public String conditioner;
    boolean flag = false;
    public ColoredLinen[] linens = new ColoredLinen[1];

    public WashingMachine()
    {
        this.color = Color.light;
        this.temperature = 30;
        this.powdertype = "Mif";
        this.conditioner = "Same";
    }
    public WashingMachine(int temperature, Color color,  String powdertype, String conditioner)
    {
        this.color = color;
        this.temperature = temperature;
        this.powdertype = powdertype;
        this.conditioner = conditioner;

    }

    public void Load(ColoredLinen linen){
    	if (!flag && this.temperature == linen.gettWashing() && this.color == linen.getColor()) {
    		linens[0] = linen;
    		flag = true;
    	}
    	else if (flag && this.temperature == linen.gettWashing() && this.color == linen.getColor())
        {
            ColoredLinen[] tmp = new ColoredLinen[linens.length + 1];
            System.arraycopy(linens, 0, tmp, 0, linens.length);
            tmp[linens.length] = linen;
            linens = new ColoredLinen[tmp.length];
            System.arraycopy(tmp, 0, linens, 0, tmp.length);
        }
    }
    public void add(Model model){
        boolean isSelect = false;

    }

    public String toString() {
    	StringBuilder ret = new StringBuilder("Machine:\n");
    	ret.append("Color type:").append(color.toString()).append("\n");
    	ret.append("Temperature:").append(Integer.toString(temperature)).append('\n');
    	ret.append("Powdertype:").append(powdertype).append('\n');
    	ret.append("Conditioner:").append(conditioner).append('\n');
    	for (ColoredLinen linen : linens) {
    		ret.append(linen.toString());//
    	}
    	return ret.toString();
    }
    
}

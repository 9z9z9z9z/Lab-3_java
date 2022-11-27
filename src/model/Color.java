package model;

public enum Color {
	light("light"),
	dark("dark"),
	colored("colored");
	
	private String color;
	
	Color(String color)
	{
       this.color = color;
	}
	
	public String getColor(){
		return color;
   }
}

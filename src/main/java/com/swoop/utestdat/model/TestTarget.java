package com.swoop.utestdat.model;

public class TestTarget
{
	private String id;
	private String name;
	private double weight;

	public TestTarget(String id)
	{
		this.id = id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getId()
	{
		return id;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public void setWeight(double weight)
	{
		this.weight = weight;
	}

	public double getWeight()
	{
		return weight;
	}
}

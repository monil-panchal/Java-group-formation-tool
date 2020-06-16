package com.assessme.db.connection;

public interface IDatabaseConfiguration
{
	public String getDatabaseUserName();
	public String getDatabasePassword();
	public String getDatabaseURL();
	public String getDatabaseName();
	public int getDatabasePort();
}

package br.ufrn.aircontrol.servlet;

public class AirConditioner {
	
	String id;
	int temperatura;
	boolean on;

	public AirConditioner() {
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(int temperatura) {
		this.temperatura = temperatura;
	}

	public boolean isOn() {
		return on;
	}

	public void setOn(boolean status) {
		this.on = status;
	}

	@Override
	public String toString() {
		return "AirConditioner [id=" + id + ", temperatura=" + temperatura
				+ ", status=" + on + "]";
	}
	
	

}

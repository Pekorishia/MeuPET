package com.fepa.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;


@Path("/")
public class Rest {

	

	static int count_init = 0;

	// http://localhost:8080/AirControl/rest/test
	@GET
	@Path("/test")
	public String test() {
			return "Teste Sucesso!!";
		
	}

	// http://localhost:8080/tictactoews/rest/test2?id_player=1
	@GET
	@Path("/test2")
	public String test2(@QueryParam("id_player") int id_player) {
		return "test2 = "+id_player;

	}

	
}

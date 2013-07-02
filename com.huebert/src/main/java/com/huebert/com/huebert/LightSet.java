package com.huebert.com.huebert;

/** (c) 2013 mkb
 *  This file is part of Huebert.
 *
 *  Huebert is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Huebert is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with Huebert.  If not, see <http://www.gnu.org/licenses/>.
**/

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import com.google.gson.*;

public class LightSet {
	private Light[] lights;
	private String server;
	private String user;
	private int numLights;
	
	private Gson gson = new Gson();
	
	public LightSet(String server, String user, int numLights){
		this.server = server;
		this.user = user;
		this.numLights = numLights;
		this.lights = new Light[numLights];
	}
	
	public void getAll() throws Exception{
		System.out.println("debug: getting all lights");
		
		for (int i = 1; i <= this.numLights; i++){
			ClientRequest getLight = new ClientRequest("http://"+this.server+"/api/"+this.user+"/lights/"+i);
			getLight.accept("application/json");
			String response = getLight.get(String.class).getEntity();
			this.lights[i-1] = (gson.fromJson(response, Light.class));
			System.out.println("debug: got light "+i);
		}
	}
	
	public void changeAll(State state) throws Exception{
		this.getAll();
		
		for(int i = 1; i <= this.numLights; i++){
			System.out.println("debug: changing light state for "+i);
			
			this.lights[i-1].state = state; //probably not needed, but it's nice to keep the lights' current state in memory
			
			ClientRequest stateChange = new ClientRequest("http://"+this.server+"/api/"+this.user+"/lights/"+i+"/state"); 
			stateChange.body("application/json", gson.toJson(state));
			ClientResponse<?> response = stateChange.put();
			
			System.out.println("debug: response is: "+response.getEntity(String.class));
		}
	}
	
	public void turnAllOff() throws Exception {
		//this turns off each one individually rather than using changeAll.
		//the reason for this is to keep settings of each individual light
		//when they are turned back on.
		
		this.getAll();
		
		for(int i = 1; i <= this.numLights; i++){
			System.out.println("debug: turning off light "+i);
			
			this.lights[i-1].state.on = false;
			
			ClientRequest turnOff = new ClientRequest("http://"+this.server+"/api/"+this.user+"/lights/"+i+"/state");
			turnOff.body("application/json", gson.toJson(this.lights[i-1].state));
			ClientResponse<?> response = turnOff.put();
			
			System.out.println("debug: response is: "+response.getEntity(String.class));
		}
	}
	
	public void turnAllOn() throws Exception {
		//this turns off each one individually rather than using changeAll.
		//the reason for this is to keep settings of each individual light
		//when they are turned back on.
		
		this.getAll();
		
		for(int i = 1; i <= this.numLights; i++){
			System.out.println("debug: turning on light "+i);
			
			this.lights[i-1].state.on = true;
			
			ClientRequest turnOff = new ClientRequest("http://"+this.server+"/api/"+this.user+"/lights/"+i+"/state");
			turnOff.body("application/json", gson.toJson(this.lights[i-1].state));
			ClientResponse<?> response = turnOff.put();
			
			System.out.println("debug: response is: "+response.getEntity(String.class));
		}
	}
}

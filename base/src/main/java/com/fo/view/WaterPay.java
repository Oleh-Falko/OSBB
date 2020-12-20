/*
 * Copyright 2009-2014 PrimeTek.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fo.view;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;


@ManagedBean
@SessionScoped
public class WaterPay implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{userLogin}")
    private UserLogin userLogin;
	
	private int past_coldwater;
	private int past_warmwater;
	private int present_coldwater;
	private int present_warmwater;
	private double pay;
	private double price_coldwater = 1.5;
	private double price_warmwater = 2.5;
	
	@PostConstruct
    public void init() {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		
		try {
			System.out.println("ID="+userLogin.getId());
			if(userLogin.getId() == 0) 
			{
				System.out.println("fsddsfsdfsfdsdf");
				FacesContext.getCurrentInstance().getExternalContext().redirect("login2.xhtml");
				return;
			}
			con = ConnectionPool.getConnection();
			ps = con.prepareStatement("SELECT * FROM waterpay WHERE user_id=? ORDER BY created DESC");
			ps.setInt(1, userLogin.getId());
			rs = ps.executeQuery();
			

			if (rs.next()) {
				past_coldwater = rs.getInt("cold_value");
				present_coldwater = past_coldwater;
				past_warmwater = rs.getInt("warm_value");
				present_warmwater = past_warmwater;
			} else {
				past_coldwater = 0;
				present_coldwater = past_coldwater;
				past_warmwater = 0;
				present_warmwater = past_warmwater;
			} 
		} catch(Exception e) {
			e.printStackTrace();
			past_coldwater = 0;
			present_coldwater = past_coldwater;
			past_warmwater = 0;
			present_warmwater = past_warmwater;
		} finally {
			ConnectionPool.closeConnection(rs,ps,con);	
		}
	}
	
	
	public int getPast_coldwater() {
		return past_coldwater;
	}
	
	public void setPast_coldwater(int past_coldwater) {
		this.past_coldwater = past_coldwater;
	}

	public int getPast_warmwater() {
		return past_warmwater;
	}

	public void setPast_warmwater(int past_warmwater) {
		this.past_warmwater = past_warmwater;
	}

	public int getPresent_coldwater() {
		return present_coldwater;
	}

	public void setPresent_coldwater(int present_coldwater) {
		this.present_coldwater = present_coldwater;
	}

	public int getPresent_warmwater() {
		return present_warmwater;
	}

	public void setPresent_warmwater(int present_warmwater) {
		this.present_warmwater = present_warmwater;
	}


	public double getPay() {
		return pay;
	}


	public void setPay(double pay) {
		this.pay = pay;
	}
	
	public UserLogin getUserLogin() {
		return userLogin;
	}


	public void setUserLogin(UserLogin userLogin) {
		this.userLogin = userLogin;
	}
	
	public void handleKeyEvent()
	{
		pay = ((present_coldwater - past_coldwater)*price_coldwater) + ((present_warmwater - past_warmwater)*price_warmwater);
	}

	public void applyPay()
	{
		Connection con = null;
		PreparedStatement ps = null;
		

		try {
			con = ConnectionPool.getConnection();
			ps = con.prepareStatement("INSERT INTO public.waterpay(\r\n"
					+ "	user_id, cold_value, warm_value)\r\n"
					+ "	VALUES (?, ?, ?);");
			ps.setInt(1, userLogin.getId());
			ps.setInt(2, present_coldwater);
			ps.setInt(3, present_warmwater);
			ps.execute();
			
			past_coldwater = present_coldwater;
			past_warmwater = present_warmwater;
			
		} catch(Exception e) {
			e.printStackTrace();
			
		} finally {
			ConnectionPool.closeConnection(null,ps,con);	
		}
	}
	
	
	
}
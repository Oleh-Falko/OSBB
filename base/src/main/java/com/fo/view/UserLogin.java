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

import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;


@ManagedBean
@SessionScoped
public class UserLogin implements Serializable {
	private static final long serialVersionUID = 1L;
	private String login;
	private String pwd;
	private int id = 0;

    @PostConstruct
    public void init() {
    	setLogin("");
    	setPwd("");
	}


	public String getLogin() {
		return login;
	}


	public void setLogin(String login) {
		System.out.println("Login="+login);
		this.login = login;
	}


	public String getPwd() {
		return pwd;
	}


	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	public int getId() {
		return id;
	}


	public void checkLogin() throws SQLException, IOException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		

		try {
			con = ConnectionPool.getConnection();
			ps = con.prepareStatement("SELECT * FROM users WHERE login=? AND password=?");
			ps.setString(1, login);
			ps.setString(2, pwd);
			rs = ps.executeQuery();
			

			if (rs.next()) {
				id = rs.getInt("id");
				System.out.println("LOGIN_ID="+id);
				FacesContext.getCurrentInstance().getExternalContext().redirect("dashboard2.xhtml");
			} else {
				id = 0;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Login failed."));
			} 
		} finally {
			ConnectionPool.closeConnection(rs,ps,con);	
		}
	}


}

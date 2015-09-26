package com.picaloopand.picaloop;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "UsersDBTable")
public class UsersDBTable extends Model {
	
	@Column(name = "userName")
    public String userName;
	
	@Column(name = "userPassword")
    public String userPassword;
	
	@Column(name = "userEmail")
    public String userEmail;
	
	@Column(name = "userFirstName")
    public String userFirstName;
	
	@Column(name = "userLastName")
    public String userLastName;
	
	@Column(name = "userPicture")
    public String userPicture;
	
	@Column(name = "signInMethod")
    public String signInMethod;
    
 
    public UsersDBTable() {
      super();
    }
    
    public UsersDBTable(String userName, String userFirstName, 
    		String userLastName, String userPassword, String userEmail, String userPicture, String signInMethod) {
    	super();
    	
        this.userName = userName;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
        this.userPicture = userPicture;
        this.signInMethod = signInMethod;
    }
    
}
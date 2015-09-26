package com.picaloopand.picaloop;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Users")
public class Users extends Model {
	
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
    
 
    public Users() {
      super();
    }
    
    public Users(String userName, String userEmail, String signInMethod) {
        super();
        
        this.userName = userName;
        this.userEmail = userEmail;
        this.signInMethod = signInMethod;
    }
 
    public Users(String userName, String userPassword, String userEmail, String signInMethod) {
        super();
        
        this.userName = userName;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
        this.signInMethod = signInMethod;
    }
    
    public Users(String userName, String userPassword, String userEmail, String userPicture, String signInMethod) {
        super();
        
        this.userName = userName;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
        this.userPicture = userPicture;
        this.signInMethod = signInMethod;
    }
    
    public Users(String userName, String userPassword, String userEmail, String userFirstName, 
    		String userLastName, String signInMethod) {
        
        this.userName = userName;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.signInMethod = signInMethod;
    }
    
}
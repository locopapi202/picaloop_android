package com.picaloopand.picaloop;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "LoopsDBTable")
public class LoopsDBTable extends Model {
	
	@Column(name = "user")
    public UsersDBTable user;
	
	@Column(name = "loopName")
    public String loopName;
	
	@Column(name = "spot1")
    public String spot1;
	
	@Column(name = "spot2")
    public String spot2;
	
	@Column(name = "spot3")
    public String spot3;
	
	@Column(name = "spot4")
    public String spot4;
	
	@Column(name = "spot5")
    public String spot5;
	
	@Column(name = "spot6")
    public String spot6;
	
	@Column(name = "spot7")
    public String spot7;
	
	@Column(name = "spot8")
    public String spot8;
	
	@Column(name = "spot9")
    public String spot9;
	
	@Column(name = "spot10")
    public String spot10;
	
	@Column(name = "spot11")
    public String spot11;
	
	@Column(name = "spot12")
    public String spot12;
	
 
    public LoopsDBTable() {
      super();
    }
    
    public LoopsDBTable(UsersDBTable user, String loopName, String spot1, String spot2, String spot3, 
    		String spot4, String spot5, String spot6, String spot7, String spot8, String spot9,
    		String spot10, String spot11, String spot12) {
    	super();
        
    	this.user = user;
        this.loopName = loopName;
        this.spot1 = spot1;
        this.spot2 = spot2;
        this.spot3 = spot3;
        this.spot4 = spot4;
        this.spot5 = spot5;
        this.spot6 = spot6;
        this.spot7 = spot7;
        this.spot8 = spot8;
        this.spot9 = spot9;
        this.spot10 = spot10;
        this.spot11 = spot11;
        this.spot12 = spot12;
    }
    
}
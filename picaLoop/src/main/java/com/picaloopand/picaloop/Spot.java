package com.picaloopand.picaloop;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;


/*
 * An extension of ParseObject that makes
 * it more convenient to access information
 * about a given Spot 
 */

@ParseClassName("Spot")
public class Spot extends ParseObject {

	public Spot() {
		// A default constructor is required.
	}

	public String getSpotName() {
		return getString("SpotName");
	}

	public void setSpotName(String SpotName) {
		put("SpotName", SpotName);
	}

	public ParseUser getSpotCreator() {
		return getParseUser("SpotCreator");
	}

	public void setSpotCreator(ParseUser SpotCreator) {
		put("SpotCreator", SpotCreator);
	}

	public String getSpotRating() {
		return getString("SpotRating");
	}

	public void setSpotRating(String SpotRating) {
		put("SpotRating", SpotRating);
	}

	public ParseFile getPhotoFile() {
		return getParseFile("photo");
	}

	public void setPhotoFile(ParseFile file) {
		put("photo", file);
	}
	
	public ParseGeoPoint getSpotLocation() {
	   return getParseGeoPoint("SpotLocation");
	}

	public void setSpotLocation(ParseGeoPoint value) {
	   put("SpotLocation", value);
	}
	
	public String getSpotComment() {
		return getString("SpotComment");
	}

	public void setSpotComment(String SpotComment) {
		put("SpotComment", SpotComment);
	}
	
	  public static ParseQuery<Spot> getQuery() {
		    return ParseQuery.getQuery(Spot.class);
		  }
}



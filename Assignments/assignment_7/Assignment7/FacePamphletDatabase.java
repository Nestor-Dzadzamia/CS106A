/*
 * File: FacePamphletDatabase.java
 * -------------------------------
 * This class keeps track of the profiles of all users in the
 * FacePamphlet application.  Note that profile names are case
 * sensitive, so that "ALICE" and "alice" are NOT the same name.
 */

import java.util.*;

public class FacePamphletDatabase implements FacePamphletConstants {
	// creating instance hashmap to save entered profiles
	private HashMap<String, FacePamphletProfile> dataBase;
 	
	/** 
	 * Constructor
	 * This method takes care of any initialization needed for 
	 * the database.
	 */
	public FacePamphletDatabase() {
		dataBase = new HashMap<String, FacePamphletProfile>(); // initializing hashmap
	}
	
	
	/** 
	 * This method adds the given profile to the database.  If the 
	 * name associated with the profile is the same as an existing 
	 * name in the database, the existing profile is replaced by 
	 * the new profile passed in.
	 */
	public void addProfile(FacePamphletProfile profile) {
		// checking if entered profile is already in data, if so we replace old with new one
		if (dataBase.containsKey(profile.getName())) { 
			dataBase.remove(profile.getName());
			dataBase.put(profile.getName(), profile);
			return;
		} 
		// if not in data base we just add it to data.
		dataBase.put(profile.getName(), profile);
	}

	
	/** 
	 * This method returns the profile associated with the given name 
	 * in the database.  If there is no profile in the database with 
	 * the given name, the method returns null.
	 */
	public FacePamphletProfile getProfile(String name) {
		return dataBase.get(name); 
	}
	
	
	/** 
	 * This method removes the profile associated with the given name
	 * from the database.  It also updates the list of friends of all
	 * other profiles in the database to make sure that this name is
	 * removed from the list of friends of any other profile.
	 * 
	 * If there is no profile in the database with the given name, then
	 * the database is unchanged after calling this method.
	 */
	public void deleteProfile(String name) {
		if (dataBase.containsKey(name)) { // checking if profile exists in data base.
			dataBase.remove(name); // if so we remove profile from data
			// checking other profiles, if deleted profile was their friend, we remove it from their friend lists too.
			for (String s : dataBase.keySet()) {
				dataBase.get(s).removeFriend(name);
			}
		}
	}

	
	/** 
	 * This method returns true if there is a profile in the database 
	 * that has the given name.  It returns false otherwise.
	 */
	public boolean containsProfile(String name) {
		return dataBase.containsKey(name);
	}

}

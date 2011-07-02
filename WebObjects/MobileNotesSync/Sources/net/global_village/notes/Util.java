package net.global_village.notes;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSComparator;
import com.webobjects.foundation.NSForwardException;
import com.webobjects.foundation.NSLog;
import com.webobjects.foundation.NSSelector;

public class Util {
	
	public static String encryptString(String string) {
		String digest = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA");
			digest = new BASE64Encoder().encode(md.digest(string.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			// TODO check this exception out
			throw new NSForwardException(e);
		}
		return digest;
	}
	
	/**
     * <p>Checks if a string is empty.</p>
     * 
     * <p>An "empty" string is a null string, a string with length 0,
     * or that contains only white spaces, tabs, etc.</p>
     * 
     * @param string - The string to check
     * @return boolean
     */
    public static boolean isEmptyString(String string) {
        return string == null || string.matches("\\s*");
    }
    
    /**
     * <p>Cropped version of the string.</p>
     * 
     * <p>croppedString( "12345678901234567890", 10 ) will return "123456 ...".
     * 
     * @param string String to crop
     * @param size Length os the resulting stirng, incluing " ...". Must be > 4.
     * @return Cropped String
     */
	public static String croppedString(String string, int size) {
		if( string == null ) { return null; }
		if( string.length() <= size ) { return string; }
		return string.substring(0, size - 4) + " ...";
	}
	

}

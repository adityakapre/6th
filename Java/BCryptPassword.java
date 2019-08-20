/**
  * Read following : 
  * - https://security.blogoverflow.com/2013/09/about-secure-password-hashing/
  * 
  * BCrypt Features :
  * 
  * Not plain text - Not only do plain text passwords compromise your website if the database 
  * is breached but they can also compromise other websites for the users. 
  * Unfortunately, a lot of users share passwords across websites.
  * One way hashing - BCrypt is a one-way hash function to obfuscate the password such that it is not stored in plain text.
  * Salted hashing - Generating random bytes (the salt) and combining it with the password before hashing 
  * creates unique hashes across each user's password. If two users have the same password they will not have 
  * the same password hash. This is to prevent rainbow table attacks which can reverse hashed passwords 
  * using common hashing functions that do not utilize a salt.
  * Logarithmic iterations - The hashing function is executed many times sequentially which can be increased exponentially, 
  * which is known as key stretching. This is to make the function CPU intensive which makes it more secure against brute force attacks.
  * Updatable iterations - As CPUs become faster so do brute force attacks. Since BCrypt stores the number of iterations 
  * as part of the hash it's possible to verify a password and then increase its strength by generating a new hash 
  * with a higher number of iterations.
  *
  *
  * Author: Ian Gallagher <igallagher@securityinnovation.com>
  *
  * This code utilizes jBCrypt, which you need installed to use.
  * jBCrypt: http://www.mindrot.org/projects/jBCrypt/
  */

public class Password {
  	// Define the BCrypt workload to use when generating password hashes. 10-31 is a valid value.
	private static int workload = 12;

	/**
	 * This method can be used to generate a string representing an account password
	 * suitable for storing in a database. It will be an OpenBSD-style crypt(3) formatted
	 * hash string of length=60
	 * The bcrypt workload is specified in the above static variable, a value from 10 to 31.
	 * A workload of 12 is a very reasonable safe default as of 2013.
	 * This automatically handles secure 128-bit salt generation and storage within the hash.
	 * @param password_plaintext The account's plaintext password as provided during account creation,
	 *			     or when changing an account's password.
	 * @return String - a string of length 60 that is the bcrypt hashed password in crypt(3) format.
	 */
	public static String hashPassword(String password_plaintext) {
		String salt = BCrypt.gensalt(workload);
		String hashed_password = BCrypt.hashpw(password_plaintext, salt);

		return(hashed_password);
	}

	/**
	 * This method can be used to verify a computed hash from a plaintext (e.g. during a login
	 * request) with that of a stored hash from a database. The password hash from the database
	 * must be passed as the second variable.
	 * @param password_plaintext The account's plaintext password, as provided during a login request
	 * @param stored_hash The account's stored password hash, retrieved from the authorization database
	 * @return boolean - true if the password matches the password of the stored hash, false otherwise
	 */
	public static boolean checkPassword(String password_plaintext, String stored_hash) {
		boolean password_verified = false;

		if(null == stored_hash || !stored_hash.startsWith("$2a$"))
			throw new java.lang.IllegalArgumentException("Invalid hash provided for comparison");

		password_verified = BCrypt.checkpw(password_plaintext, stored_hash);

		return(password_verified);
	}

	/**
	  * A simple test case for the main method, verify that a pre-generated test hash verifies successfully
	  * for the password it represents, and also generate a new hash and ensure that the new hash verifies
	  * just the same.
	  */
	public static void main(String[] args) {
		String test_passwd = "abcdefghijklmnopqrstuvwxyz";
		String test_hash = "$2a$06$.rCVZVOThsIa97pEDOxvGuRRgzG64bvtJ0938xuqzv18d3ZpQhstC";

		System.out.println("Testing BCrypt Password hashing and verification");
		System.out.println("Test password: " + test_passwd);
		System.out.println("Test stored hash: " + test_hash);
		System.out.println("Hashing test password...");
		System.out.println();

		String computed_hash = hashPassword(test_passwd);
		System.out.println("Test computed hash: " + computed_hash);
		System.out.println();
		System.out.println("Verifying that hash and stored hash both match for the test password...");
		System.out.println();

		String compare_test = checkPassword(test_passwd, test_hash)
			? "Passwords Match" : "Passwords do not match";
		String compare_computed = checkPassword(test_passwd, computed_hash)
			? "Passwords Match" : "Passwords do not match";

		System.out.println("Verify against stored hash:   " + compare_test);
		System.out.println("Verify against computed hash: " + compare_computed);

	}

}

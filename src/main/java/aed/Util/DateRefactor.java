package aed.Util;

public class DateRefactor {
	
	public static String fromString(String str) {
		String[] componentes = str.split("/");
		return componentes[2] + "-" +  componentes[1] + "-" +  componentes[0];
	}

}

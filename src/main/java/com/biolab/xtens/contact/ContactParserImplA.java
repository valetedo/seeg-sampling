package com.biolab.xtens.contact;

/* The formatA is : label,anatomical_area,epileptic_contact,bad_contact,TP_dist,x,y,z
 
 */
public class ContactParserImplA implements ContactParser {

	public ContactParserImplA()
	{

	}
	@Override
	public ContactParam parse(String s) {
		ContactParam contactParam = new ContactParam();
		String[] a = s.split(",");
		contactParam.setLabel(a[0]);
		contactParam.setAnatomical_area(a[1]);
		if (!a[2].equals("")){
			contactParam.setEpileptic_contact(a[2]);
		} else {
			contactParam.setEpileptic_contact("NO");
		}
		if (!a[3].equals("")) {
			contactParam.setBad_contact(a[3]);
		} else {
			contactParam.setBad_contact("NO");
		}
		if (!a[4].equals("")) {
			contactParam.setTp_dist(Float.parseFloat(a[4]));
		} else {
			contactParam.setTp_dist((float)0.0);
		}
		contactParam.setX(Float.parseFloat(a[5]));
		contactParam.setY(Float.parseFloat(a[6]));
		contactParam.setZ(Float.parseFloat(a[7]));
		return contactParam;
	}
}


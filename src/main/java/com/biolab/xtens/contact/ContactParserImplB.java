package com.biolab.xtens.contact;

/* The formatB is : Label,Anatomical_area, Number,X,Y,Z,Bad_contact,Epileptic_contact,TP_dist
 */
public class ContactParserImplB implements ContactParser {
	
	public void ContactParseImplB()
	{
		
	}

	@Override
	public ContactParam parse(String s) {
		
		ContactParam contactParam = new ContactParam();
		String[] a = s.split(",");
		contactParam.setLabel(a[0]);
		contactParam.setAnatomical_area(a[1]);
		contactParam.setX(Float.parseFloat(a[3]));
		contactParam.setY(Float.parseFloat(a[4]));
		contactParam.setZ(Float.parseFloat(a[5]));
		
		try {
			if(!a[6].equals("")) {
				contactParam.setBad_contact(a[6]);
			}
			else {
				contactParam.setBad_contact("NO");
			}
		} catch(IndexOutOfBoundsException e) {
			contactParam.setBad_contact("NO");
		}
		
		try {
			if (!a[7].equals("")) {
				contactParam.setEpileptic_contact(a[7]);
			}
			else
			{
				contactParam.setEpileptic_contact("NO");
			}
		} catch(IndexOutOfBoundsException e) {
			contactParam.setEpileptic_contact("NO");
		}
		try {
			if (!a[8].equals("")) {
			contactParam.setTp_dist(Float.parseFloat(a[8]));
			}
			else {
				contactParam.setTp_dist((float)0.0);
			}
		} catch(IndexOutOfBoundsException e) {
			contactParam.setTp_dist((float)0.0);
		}
		
		
	return contactParam;
	}

}

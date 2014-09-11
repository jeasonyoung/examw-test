package com.examw.test.junittest;



public class test {								
	//private final static String regxpForHtml = "<(?!/?(?i)(img|br)).*?>";
	    public static void main(String[] args) {
//	    	String str = "</P><P><TD style='PADDING-RIGHT: 0cm;br  PADDING-LEFT: 0cm; BORDER-LEFT-COLOR: #ece9d8; BORDER-BOTTOM-COLOR: #ece9d8; PADDING-BOTTOM: 0cm; WIDTH: 72pt; BORDER-TOP-COLOR: #ece9d8; PADDING-TOP: 0cm; BACKGROUND-COLOR: transparent; BORDER-RIGHT-COLOR: #ece9d8' width=96><P style='LIN-HEIGHT: 150%'>375</P></TD></TR><IMG border=0 alt='' src='http://www.cyedu.org/UpFiles/2014-1/17/1.jpg'>";
//	    	str = str.replaceAll("</[p|P]><[p|P](.+?)>", "<br/>");
//	    	str = str.replaceAll("<(?!/?(?i)(img|br)).*?>", "");
//	    	
//	    	System.out.println(str);
	    	String examId = "5449,5450,5451,5452,5453";
			String[] ans = examId.split(",");
			for (int i = 0; i < ans.length; i++) {
				System.out.println(ans[i]);
			}
		} 
	}



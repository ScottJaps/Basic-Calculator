
public class Calculator {
	private String currentInt;
	private String previousInt;
	private String operator;
	private double memory;
	
	public Calculator(){
		currentInt = "0";
		previousInt = null;
		operator = null;
		memory = 0;
	}
	
	private void addToCurrentString(String x){
		if (currentInt == "0" || currentInt == null) currentInt = x;                                   	//prevents mass zeros at the start
		else if(currentInt.equals("-0")) currentInt = "-" + x;                                         	//prevents mass zeros if there is a negitive
		else if(x == "-" && currentInt.contains("-")) currentInt = currentInt.replace("-", "");			//prevents error with the negitive sign
		else if(x == "-") currentInt = "-" + currentInt;												//same as above
		else currentInt = currentInt + x;
	}
	
	public void drawCalculator(){
		StdDraw.clear();
		//sets up the background and the display box
        StdDraw.setPenColor(StdDraw.GRAY);
        StdDraw.setXscale(0, 1000);
        StdDraw.setYscale(0, 1000);
        StdDraw.filledSquare(500, 500, 500);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.filledRectangle(500, 900, 500, 100);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.02);
        //draws the lines for the calculator
        StdDraw.line(0, 0, 0, 1000);
        StdDraw.line(0, 0, 1000, 0);
        StdDraw.line(1000, 0, 1000, 1000);
        StdDraw.line(0, 1000, 1000, 1000);
        StdDraw.line(0, 800, 1000, 800);
        StdDraw.line(200, 0, 200, 800);
        StdDraw.line(400, 0, 400, 800);
        StdDraw.line(600, 0, 600, 800);
        StdDraw.line(800, 0, 800, 800);
        StdDraw.line(0, 150, 1000, 150);
        StdDraw.line(0, 300, 1000, 300);
        StdDraw.line(0, 450, 1000, 450);
        StdDraw.line(0, 600, 1000, 600);
        StdDraw.line(0, 700, 1000, 700);
        //text in the boxes
        StdDraw.text(100, 75, "0");
        StdDraw.text(300, 75, ".");
        StdDraw.text(500, 75, "-");
        StdDraw.text(100, 225, "1");
        StdDraw.text(300, 225, "2");
        StdDraw.text(500, 225, "3");
        StdDraw.text(100, 375, "4");
        StdDraw.text(300, 375, "5");
        StdDraw.text(500, 375, "6");
        StdDraw.text(100, 525, "7");
        StdDraw.text(300, 525, "8");
        StdDraw.text(500, 525, "9");
        StdDraw.text(900, 75, "=");
        StdDraw.text(900, 225, "-");
        StdDraw.text(900, 375, "+");
        StdDraw.text(700, 225, "/");
        StdDraw.text(700, 75, "*");
        StdDraw.text(100, 650, "Sin");
        StdDraw.text(300, 650, "Cos");
        StdDraw.text(900, 750, "Clear");
        StdDraw.text(900, 525, "Pi");
        StdDraw.text(700, 375, "Sqrt");
        StdDraw.text(100, 750, "M+");
        StdDraw.text(300, 750, "M-");
        StdDraw.text(500, 750, "Mclr");
        StdDraw.text(700, 750, "Mem");
        StdDraw.text(500, 650, "Tan");
        StdDraw.text(700, 525, "^");
        StdDraw.text(900, 650, "e");
        StdDraw.text(700, 650, "Backspace");
        //draws the numbers in the display area
        if(currentInt == null) StdDraw.textRight(900,900, previousInt);
        else StdDraw.textRight(900,900, currentInt);
        if(operator != null) StdDraw.textLeft(100, 900, operator);
	}
	
	private double factorial(double x, double acc){                          // factorial for use in sin and cos, tail recursive
		if (x == 1) return acc;
		else if (x>1)  return factorial(x-1, x*acc);
		else return 0;
	}
	
	private double findSinIter(double x, double acc, double exp){            //my own sine function
		double next;
		//find the next element in the series
		if ((exp-1)%4 == 0) {     //add next 
			next = Math.pow(x, exp)/factorial(exp,1);
		}
		else{
			next = -Math.pow(x, exp)/factorial(exp,1);
		}
		if (Math.abs(next)<.00000001){
			return acc + next;
		}
		else return findSinIter(x, acc+next, exp+2);
	}
	
	private double findCosIter(double x, double acc, double exp){              //my own conine function
		double next;
		//find the next element in the series
		if ((exp)%4 == 0) {     //add next 
			next = Math.pow(x, exp)/factorial(exp,1);
		}
		else{
			next = -Math.pow(x, exp)/factorial(exp,1);
		}
		if (Math.abs(next)<.00000001){
			return acc + next;
		}
		else return findCosIter(x, acc+next, exp+2);
	}
	
	private double findsqrt(double num, double prev){
		if(Math.abs(num/prev-prev)<.0001) return prev;
		else return findsqrt(num, (prev + num/prev)/2);
	}

	
	private boolean canEvaluate(){
		if(operator != null && currentInt != null && previousInt != null) return true;
		else return false;
	}
	//evaluates the input with basic operations, sadly you can't use switch statements with strings in java 1.6
	private String evaluate(){
		double x = Double.parseDouble(previousInt);
		double y = Double.parseDouble(currentInt);
		previousInt = null;
		String ourOperator = operator;
		operator = null;
		if(ourOperator == "+") return Double.toString(x+y);
		if(ourOperator == "*") return Double.toString(x*y);
		if(ourOperator == "-") return Double.toString(x-y);
		if(ourOperator == "/") return Double.toString(x/y);
		if(ourOperator == "^") return Double.toString(Math.pow(x, y));
		return "error";
	}
	//gets the input and adds it to the holding strings
	public void inputs(double x, double y){
		if (x > 0 && x < 200 && y > 0 && y < 150){                       //0
			addToCurrentString("0");
		}
		else if(x > 200 && x < 400 && y > 0 && y < 150){                    // .
			currentInt = currentInt + ".";
		}
		else if(x > 400 && x < 600 && y > 0 && y < 150){                  // -
			addToCurrentString("-");
		}
		else if(x > 0 && x < 200 && y > 150 && y < 300){                  //1
			addToCurrentString("1");
		}
		else if(x > 200 && x < 400 && y > 150 && y < 300){                   //2
			addToCurrentString("2");
		}
		else if(x > 400 && x < 600 && y > 150 && y < 300){                      //3
			addToCurrentString("3");
		}
		else if(x > 0 && x < 200 && y > 300 && y < 450){                   //4
			addToCurrentString("4");
		}
		else if(x > 200 && x < 400 && y > 300 && y < 450){              //5
			addToCurrentString("5");
		}
		else if(x > 400 && x < 600 && y > 300 && y < 450){                 //6
			addToCurrentString("6");
		}
		else if(x > 0 && x < 200 && y > 450 && y < 600){                  //7
			addToCurrentString("7");
		}
		else if(x > 200 && x < 400 && y > 450 && y < 600){                 //8
			addToCurrentString("8");
		}
		else if(x > 400 && x <600 && y > 450 && y < 600){                  //9
			addToCurrentString("9");
		}
		else if(x > 800 && x < 1000 && y > 0 && y < 150){                 //=
			if (canEvaluate()) currentInt = evaluate();
		}
		else if(x > 600 && x < 800 && y > 0 && y < 150){                      //*
			if (canEvaluate()) currentInt = evaluate();
			if (currentInt != null){
				previousInt = currentInt;
				currentInt = null;
			}
			operator = "*";
		}
		else if(x > 600 && x < 800 && y > 150 && y < 300){                       //  /
			if (canEvaluate()) currentInt = evaluate();
			if (currentInt != null){
				previousInt = currentInt;
				currentInt = null;
			}
			operator = "/";
		}
		else if(x > 800 && x < 1000 && y > 150 && y < 300){                          //-
			if (canEvaluate()) currentInt = evaluate();
			if (currentInt != null){
				previousInt = currentInt;
				currentInt = null;
			}
			operator = "-";
		}
		else if(x > 800 && x < 1000 && y > 300 && y < 450){                  //+
			if (canEvaluate()) currentInt = evaluate();
			if (currentInt != null){
				previousInt = currentInt;
				currentInt = null;
			}
			operator = "+";
		}
		else if(x>800 && x<1000 && y>700 &&y<800){                            // clear
			operator = null;
			previousInt = null;
			currentInt = "0";
		}
		else if(x>0 && x<200 && y>600 && y<700){                                   //sin
			if (canEvaluate()) currentInt = evaluate();
			if (currentInt != null){
				double forSin = Double.parseDouble(currentInt);
				currentInt = Double.toString(findSinIter(forSin, 0, 1));
				//currentInt = Double.toString(Math.sin(forSin));
			}
		}
		else if (x>200 && x<400 && y>600 && y<700){                                  //cos
			if (canEvaluate()) currentInt = evaluate();
			if (currentInt != null){
				double forCos = Double.parseDouble(currentInt);
				currentInt = Double.toString(findCosIter(forCos, 1, 2));
				//currentInt = Double.toString(Math.cos(forCos));
			}
		}
		else if (x>400 && x<600 && y >600 &&y<700){                                 //tan
			if (canEvaluate()) currentInt = evaluate();
			if (currentInt != null){
				double forTan = Double.parseDouble(currentInt);
				currentInt = Double.toString(findSinIter(forTan, 0, 1)/findCosIter(forTan, 1, 2));
				//currentInt = Double.toString(Math.tan(forTan));
			}
		}
		else if (x>800 && x< 1000 && y>450 && y<600){                               //pi
			currentInt = "3.1415926535";
		}
		else if (x>600 && x<800 && y<450 && y>300){                                 //sqrt
			if (canEvaluate()) currentInt = evaluate();
			if (currentInt != null && Double.parseDouble(currentInt) >= 0){
				currentInt = Double.toString(findsqrt(Double.parseDouble(currentInt), 1));
				//currentInt = Double.toString(Math.sqrt(Double.parseDouble(currentInt)));
			}
		}
		else if (x > 0 && x < 200 && y > 700 && y < 800){                          //memory add
			if (canEvaluate()) currentInt = evaluate();
			if (currentInt != null){
				double toUse = Double.parseDouble(currentInt);
				memory += toUse;				
			}
		}
		else if (x > 200 && x < 400 && y> 700 && y < 800){                            //memory subtract
			if (canEvaluate()) currentInt = evaluate();
			if (currentInt != null){
				double toUse = Double.parseDouble(currentInt);
				memory -= toUse;
			}
		}
		else if (x > 400 && x < 600 && y> 700 && y < 800){                            //memory clear
			memory = 0;
		}
		else if (x > 600 && x < 800 && y> 700 && y < 800){                            //show memory
			currentInt = Double.toString(memory);
			
		}
		else if (x>600 && x<800 && y>450 && y<600){                                       // ^
			if (canEvaluate()) currentInt = evaluate();
			if (currentInt != null){
				previousInt = currentInt;
				currentInt = null;
			}
			operator = "^";
		}
		else if (x> 800 && x<1000 && y>600 && y<700){                                 // e
			currentInt = "2.71928182846";
		}
		else if (x>600 && x<800 && y>600 && y<700){                                    //backspace
			if (currentInt != null && currentInt.length() >= 2) currentInt = currentInt.substring(0, currentInt.length()-1);
			else if(currentInt.length()<=1) currentInt = "0";
		}
		
	}
	
	public static void main(String[] args) {
		Calculator theCalc = new Calculator();
		theCalc.drawCalculator();
		while (true) {
            if (StdDraw.mousePressed()) {
                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();
                StdDraw.clear();
                theCalc.inputs(x,y);
                theCalc.drawCalculator();
                //delay for .1 seconds to prevent fast double clicks
                try {
                	  Thread.sleep(100L);	  // one tenth second
                	}
                	catch (Exception e) {StdOut.println("error in sleep delay");}	   // should not happen
            }
            StdDraw.show(50);
        }
	}

}

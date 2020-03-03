package sample;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
public class PieChart {

    private String[] Alphabet;
    private int [] Occurrence ;
    private int size ;
    private int NumberToShow ;

    Color[] color ={Color.GREEN, Color.RED, Color.BLUE, Color. YELLOW ,Color.PALEGREEN ,Color. GREY ,Color. VIOLET ,Color.DARKSALMON ,
            Color.ORCHID ,Color.BROWN,Color. INDIGO , Color. LAVENDER ,Color. CYAN ,Color. CORNFLOWERBLUE ,Color.KHAKI ,
            Color.MAGENTA ,Color.TAN , Color. MEDIUMPURPLE ,Color. SNOW ,Color. BISQUE ,Color. YELLOWGREEN ,
            Color.MEDIUMAQUAMARINE , Color.AZURE ,Color.CORNSILK ,Color.STEELBLUE,Color.LEMONCHIFFON };


    PieChart(HashMap<String,Integer> input, int Input)
    {
        Alphabet = new String[input.size()];
        Occurrence = new int [input.size()];
        int currIndex = 0 ;
        for (Map.Entry<String, Integer> mapEntry : input.entrySet()) {
            Alphabet [currIndex] = mapEntry.getKey();
            Occurrence [currIndex] = mapEntry.getValue();
            currIndex++;
        }
         size =input.size();
        NumberToShow =Input;
    }
    void draw(GraphicsContext gc)
    {
        double diameter=.7*(gc.getCanvas().getWidth());
        double center=gc.getCanvas().getWidth()/ 2 ;
        double radius= diameter/ 2 ;
        sort();
        double totalFrequency= 0 ;
        for ( int i= 0 ;i< size ;i++)
        {
            totalFrequency=totalFrequency+ Occurrence [i];
        }
        double [] percentage= new double [ size ];
        for ( int i= 0 ;i< size ;i++)
        {
            percentage[i]= Occurrence [i]/totalFrequency;
        }
//Getting graphical coordinates to display text begins here
        double [] angle= new double [size];
        double [] x = new double [size+1];
        double [] y = new double [size+1];
        double temp= 1.5*Math. PI ;
        for ( int i= 0 ;i< size ;i++)
        {
            angle[i]=temp;
            temp=temp+( 2 *Math. PI )*percentage[i];
        }
        for ( int i= 0 ; i< size ;i++)
        {
            x[i]=Math.abs(radius*Math.cos(angle[i])+center);
            y[i]=Math.abs(radius*Math.sin(angle[i])+center);
        }
        y[size]=center-radius;
        x[size]=center;

        double [] angle1= new double [size];
        for ( int i= 0 ;i< size ;i++)
        {
            angle1[i]=percentage[i]* 360 ;
        }
        double currentAngle= 90;
        DecimalFormat f = new DecimalFormat( "#.####" );
        for ( int i= 0 ;i< NumberToShow ;i++) {
            gc.setFill( color [i]);
            gc.fillArc(center - radius, center - radius, diameter, diameter,
                    currentAngle, -1 * angle1[i], ArcType. ROUND );
            currentAngle = currentAngle - angle1[i];
            if (currentAngle < 0 ) {
                currentAngle = 360 + currentAngle;
            }
            gc.setFill(Color. BLACK );
            gc.fillText( Alphabet [i]+ ", " +f.format(Occurrence[i]),
                    (x[i]+x[i+ 1 ])/ 2 , (y[i]+y[i+ 1 ])/ 2 );
        }
        if ( NumberToShow != size)
        {
            double lastAngle= 360 ;
            double lastPercentage= 1 ;
            for ( int i= 0 ;i< NumberToShow ;i++)
            {
                lastAngle=lastAngle-angle1[i];
                lastPercentage-=percentage[i];
            }
            gc.setFill(Color.GREY );
            gc.fillArc(center - radius, center - radius, diameter, diameter,
                    currentAngle, - 1 * lastAngle, ArcType. ROUND );
            gc.setFill(Color. BLACK );
          /*  gc.fillText( "All others Alphabet " +f.format(lastPercentage),
                    center/ 2 , (y[ NumberToShow ]+y[ size ])/ 2 );*/
        }
    }
    void sort() //Sorts from highest probability to lowest
    {
        for ( int i= 0 ;i< size - 1 ;i++)
        {
            for ( int j=i+ 1 ;j< size ;j++)
            {;
                if ( Occurrence [j]> Occurrence [i])
                {
                    int temp= Occurrence [j];
                    Occurrence [j]= Occurrence [i];
                    Occurrence [i]=temp;
                    String temp2= Alphabet [j];
                    Alphabet [j]= Alphabet [i];
                    Alphabet [i]=temp2;
                }
            }
        }
    }
}
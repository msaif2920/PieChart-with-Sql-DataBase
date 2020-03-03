package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.sql.SQLException;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        CreateDataBase MakeDatabase= new CreateDataBase();
        MakeDatabase.open();
        MakeDatabase.CreateTable();
        CreateDataBase dataSource = new CreateDataBase();
        QueryDataBase query = new QueryDataBase();



        int canvasWidth = 600;
        int canvasHeight = 600;
        Canvas canvas = new Canvas(canvasWidth, canvasHeight);
        GraphicsContext gc = canvas.getGraphicsContext2D();


        QueryDataBase d1=new QueryDataBase();
        d1.count(21700, "FALL 2019");
        d1.draw_chart(gc);



        Pane root = new Pane();
        Scene scene = new Scene(root, canvasWidth, canvasHeight);
        primaryStage.setScene(scene);
        primaryStage.show();
        root.getChildren().add(canvas);
    }

    public static void main(String[] args) throws SQLException {
        launch(args);
    }
}


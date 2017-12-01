package org.loader.astar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.loader.astar.helper.Pane;
import org.loader.astar.view.AStarView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Pane[][] panes = new Pane[30][10];
        Pane pane;

        Pane start = null;
        Pane end = null;

        for (int i = 0; i < 30;i++) {
            for (int j = 0; j < 10;j++) {
                pane = new Pane(j, i, j == 6 && i > 10 && i < 20);
                if (i == 15 && j == 3) { start = pane;}
                if (i == 15 && j == 8) { end = pane;}
                panes[i][j] = pane;
            }
        }

        AStarView view = (AStarView) findViewById(R.id.astar);
        view.setPanes(panes, start, end);
    }
}

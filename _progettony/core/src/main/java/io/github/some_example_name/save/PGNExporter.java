package io.github.some_example_name.save;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.util.List;

public class PGNExporter {

    public static void save(String filename, List<String> moves) {

        try {

            FileHandle file = Gdx.files.local(filename);

            StringBuilder sb = new StringBuilder();

            sb.append("[Event \"Chess Game\"]\n\n");

            int moveNumber = 1;

            for (int i = 0; i < moves.size(); i++) {

                if (i % 2 == 0) {
                    sb.append(moveNumber).append(". ");
                    moveNumber++;
                }

                sb.append(moves.get(i)).append(" ");
            }

            file.writeString(sb.toString(), false);

            System.out.println("PGN saved to: " + file.file().getAbsolutePath());

        } catch (Exception e) {
            System.out.println("PGN save failed!");
            e.printStackTrace();
        }
    }
}

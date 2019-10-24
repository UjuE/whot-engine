package pink.digitally.games.whot.whotcore.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

public class QueueShuffler {
    private QueueShuffler(){}

    public static <T> void shuffle(Queue<T> queue){
        List<T> copy = new ArrayList<>(queue);
        Collections.shuffle(copy);

        queue.removeAll(copy);
        queue.addAll(copy);
    }
}

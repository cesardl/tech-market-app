package pe.edu.unmsm.fisi.market.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Runs one query off the Event Dispatch Thread and hands its result back on it. The screens
 * load through this instead of writing a SwingWorker each: the query has no Swing in it and
 * the widgets are touched where Swing expects them to be.
 *
 * <p>Created on 12/11/2026.
 *
 * @author Cesardl
 */
public final class BackgroundTask {

    private static final Logger LOG = LoggerFactory.getLogger(BackgroundTask.class);

    private BackgroundTask() {
    }

    /**
     * Queries in the background, shows the result on the Event Dispatch Thread.
     *
     * @param query nothing in here may touch a Swing component: it runs on a worker thread.
     * @param show  runs on the Event Dispatch Thread once the query answers, and not at all
     *              if the task was cancelled.
     * @return the task, so a caller that asks again can cancel the one still in flight and
     *         keep a slower earlier answer from overwriting a newer one.
     */
    public static <T> javax.swing.SwingWorker<T, Void> run(final Supplier<T> query, final Consumer<T> show) {
        javax.swing.SwingWorker<T, Void> task = new javax.swing.SwingWorker<T, Void>() {

            @Override
            protected T doInBackground() {
                return query.get();
            }

            @Override
            protected void done() {
                if (isCancelled()) {
                    return;
                }
                try {
                    show.accept(get());
                } catch (ExecutionException ex) {
                    // The DAOs log and swallow SQLException and answer an empty collection,
                    // so getting here means the screen itself is broken, not the database.
                    LOG.error("A background task failed, its widget is left as it was", ex.getCause());
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        };
        task.execute();
        return task;
    }
}

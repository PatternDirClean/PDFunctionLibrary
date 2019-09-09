package fybug.nulll.pdfunctionlibrary.expandwith;
import com.sun.istack.internal.NotNull;

import java.io.Closeable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public final
class AsnyDirte implements Closeable {
    @NotNull public static final AsnyDirte GLODAL = new AsnyDirte();

    /** <p>线程池.</p> */
    private volatile ExecutorService pool;

    public
    AsnyDirte() {this(Executors.newCachedThreadPool());}

    public
    AsnyDirte(@NotNull final ExecutorService service) {pool = service;}

    public synchronized
    void subim(Runnable runnable) {
        if (pool == null)
            throw new Error("is Close");
        pool.submit(runnable);
    }

    public
    void close() {
        if (this == GLODAL)
            return;

        pool.shutdown();
        pool = null;
    }
}

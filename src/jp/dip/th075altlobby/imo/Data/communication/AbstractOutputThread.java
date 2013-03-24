package jp.dip.th075altlobby.imo.Data.communication;

import java.io.OutputStream;
import java.util.concurrent.LinkedBlockingDeque;

public abstract class AbstractOutputThread implements Runnable {
    protected final CommunicationDataOutput out;
    protected final LinkedBlockingDeque<WrappedData> queue;

    /**
     * 出力ストリームを指定してOutputThreadを構築する
     * 
     * @param out
     *            対象となる出力ストリーム
     */
    public AbstractOutputThread(OutputStream out) {
        this.out = new CommunicationDataOutput(out);
        this.queue = new LinkedBlockingDeque<WrappedData>();
    }

    /**
     * 送信キューにデータを追加します。 このメソッドは内部で保持しているBlockingQueueのaddメソッドを呼び出します。
     * このメソッドがブロックされることはありません。 キューに追加されたデータは、送信可能であればすぐクライアントに配信されます。
     * 
     * @param data
     *            キューに追加されるデータ
     */
    public void put(WrappedData data) {
        queue.add(data);
    }

    /**
     * 出力スレッドを安全な形で停止する.
     * 
     * 出力スレッドに終了用ダミーデータを割り込ませ、出力スレッドを安全な形で停止させます。
     */
    public void interruptExit() {
        WrappedData data = new WrappedData(WrappedData.EXIT_DUMMY, new byte[0]);
        queue.addFirst(data);
    }
}

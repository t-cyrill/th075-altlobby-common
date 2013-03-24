package jp.dip.th075altlobby.imo.Data.communication;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * <h1>ResponseStoredInstantMessages</h1> <h2>サーバーに保存されたIMを要求した結果が格納されるクラス</h2>
 * <p>
 * DataSplitter.toResponseStoredInstantMessagesが返すサーバーに保存されたIMを要求した結果が格納されるクラスです
 * 。
 * </p>
 * <p>
 * サーバーのレスポンスコードを示すbyte値responseCodeとインスタントメッセージが格納されたキューをフィールドに持ちます。
 * </p>
 * <p>
 * このクラスのインスタンスは不可変です。
 * getInstantMessagesQueueが返すキューは内部キューのコピーです。コピーされたキューを変更しても元のキューには影響を与えません。
 * </p>
 * 
 * @author Cyrill
 */
public class ResponseStoredInstantMessages {
    private final byte responseCode;
    private final Queue<InstantMessage> queue;

    /**
     * <h1>ResponseStoredInstantMessages</h1> <h2>インスタンスを生成する</h2>
     * <p>
     * ResponseStoredInstantMessagesのインスタンスを生成します。
     * </p>
     * <p>
     * コンストラクタはimsのコピーを作成し、内部で保持します。インスタンスが生成された後にimsを変更しても、
     * インスタンスが保持している内部キューには影響を与えません。
     * </p>
     * <p>
     * 生成されるインスタンスは不可変です。
     * </p>
     * 
     * @param responseCode
     *            レスポンスコード
     * @param ims
     *            InstantMessageが格納されたキュー
     */
    public ResponseStoredInstantMessages(byte responseCode,
            Queue<InstantMessage> ims) {
        this.responseCode = responseCode;
        if (ims != null)
            this.queue = new ArrayDeque<InstantMessage>(ims);
        else
            this.queue = new ArrayDeque<InstantMessage>();
    }

    /**
     * <h1>getResponseCode</h1> <h2>このインスタンスが保持しているレスポンスコードを返す</h2>
     * 
     * @return 保持しているレスポンスコード
     */
    public byte getResponseCode() {
        return responseCode;
    }

    /**
     * <h1>getInstantMessagesQueue</h1> <h2>このインスタンスが内部で保持しているキューのコピーを返す</h2>
     * <p>
     * このインスタンスが内部で保持しているキューのコピーを返します。
     * 返されるキューは内部キューのコピーです。このメソッドが返すキューを変更しても元のキューには影響を与えません。
     * <p>
     * 
     * @return 内部キューのコピー
     */
    public Queue<InstantMessage> getInstantMessagesQueue() {
        return new ArrayDeque<InstantMessage>(queue);
    }
}

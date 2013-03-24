package jp.dip.th075altlobby.imo.Data.communication;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicInteger;

import jp.dip.th075altlobby.imo.Exception.InvalidDataLengthException;

/**
 * <h1>RefinedCommunicationDataInput</h1> <h2>
 * InputStreamから抽象的なデータの読み取りを行う機能を実装する</h2>
 * <p>
 * CommunicationDataOutputから送られてくるデータを読み取り、WrappedDataとして返します。
 * </p>
 * <p>
 * このクラスはCommunicationDataInputのより安全な実装です。
 * {@link CommunicationDataInput#recieve()}は入力データ量に制限を課しませんが、
 * {@link RefinedCommunicationDataInput#recieve()}は、入力するデータ量を制限することができます。
 * 制限量以上のデータを送信しようとするクライアントのソケットを一方的に切断します。
 * </p>
 * <p>
 * 制限量は{@link RefinedCommunicationDataInput#setRecieveLimit(int)}により設定します。
 * </p>
 * 
 * @author Cyrill
 */
public class RefinedCommunicationDataInput extends CommunicationDataInput {
    private AtomicInteger recieveLimit = new AtomicInteger(1024 * 64);

    public RefinedCommunicationDataInput(InputStream in) {
        super(in);
    }

    /**
     * <h1>setRecieveLimit</h1> <h2>入力可能なデータ量の制限値を設定する</h2>
     * <p>
     * {@link RefinedCommunicationDataInput#recieve()}が入力可能なデータサイズの最大値を設定します。
     * この設定値を超える送信要求があった場合、{@link InvalidDataLengthException}を発生させます。
     * </p>
     * <p>
     * デフォルトの制限量は1024 * 64です。
     * </p>
     * 
     * @param limit
     *            制限バイト数
     */
    public void setRecieveLimit(int limit) {
        this.recieveLimit.set(limit);
    }

    /**
     * <h1>recieve</h1> <h2>データの到着を待つ</h2>
     * <p>
     * CommunicationDataOutputのsendメソッドにより送信されたデータの到着を待ちます。
     * </p>
     * <p>
     * このメソッドはまず4byteを読み込み、実データのサイズを決定します。 次に1byteを読み込み、コマンドとして格納します。
     * 最後に、実データサイズ分だけデータの読み取りを試みます。
     * </p>
     * <p>
     * データが到達するまで、このメソッドはブロックされます。
     * </p>
     * 
     * @return 到着したデータ
     * @throws IOException
     *             通信中にSocketが閉じられたまたはリセットされた場合。その他の入出力エラーが発生した場合。
     */
    @Override
    public WrappedData recieve() throws IOException {
        int limit = recieveLimit.get();

        Integer length;
        Byte command;
        byte[] rawData;

        length = getIn().readInt();
        if (length >= limit) {
            throw new InvalidDataLengthException("入力データ量が想定量を超えています。");
        }

        command = getIn().readByte();

        rawData = new byte[length];

        getIn().readFully(rawData);

        WrappedData recieved = new WrappedData(length, command, rawData);
        return recieved;
    }
}
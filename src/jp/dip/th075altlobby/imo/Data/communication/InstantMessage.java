package jp.dip.th075altlobby.imo.Data.communication;

import java.io.Serializable;

public class InstantMessage implements Serializable {

    /**
     * シリアルバージョン
     */
    private static final long serialVersionUID = 1414354008385459122L;

    private final String from;
    private final String to;
    private final String message;
    private final Long time;
    private final Byte color;

    /**
     * <h1>InstantMessage</h1> <h2>インスタントメッセージのインスタンスを生成する</h2>
     * <p>
     * インスタントメッセージのインスタンスを生成するコンストラクタです。
     * </p>
     * <p>
     * timeはサーバーサイドで設定されます。
     * </p>
     * <p>
     * このクラスのインスタンスは不可変です。
     * </p>
     * 
     * @param from
     *            送信元を表す文字列
     * @param to
     *            送信先を表す文字列
     * @param message
     *            送信するメッセージ
     * @param time
     *            送信された時間
     * @param color
     *            IMの色を表すbyte
     */
    public InstantMessage(String from, String to, String message, Long time,
            Byte color) {
        this.from = from;
        this.to = to;
        this.message = message;
        this.time = time;
        this.color = color;
    }

    /**
     * <h1>toString</h1> <h2>このインスタンスの文字列表現を返す</h2>
     * <p>
     * このインスタンスの文字列表現を返します。
     * </p>
     * <p>
     * インスタンスが保持している「送信元」、「送信先」、「メッセージ」、「時間」、「色」を文字列として返します。
     * </p>
     * 
     */
    public String toString() {
        return "" + from + "/" + to + "/" + message + "/" + time + "/" + color;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getMessage() {
        return message;
    }

    public Long getTime() {
        return time;
    }

    public Byte getColor() {
        return color;
    }

    public static final String[] color_set = { "black", "red", "green", "blue",
            "purple", "teal", "olive", "maroon", "navy", "gray" };
}

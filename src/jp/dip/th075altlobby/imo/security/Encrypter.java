package jp.dip.th075altlobby.imo.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * ハッシュ計算のラッパークラス
 * 
 * @auther Cyrill
 */
public class Encrypter {
    /**
     * 文字列のハッシュ値を文字列で返す
     * 
     * @param string
     *            ハッシュを計算する文字列
     * @param algo
     *            ハッシュアルゴリズム(MD5やSHA-1など)
     * @return ハッシュ値を示す
     */
    public static String getHash(String string, String algo) {
        if (string == null)
            throw new NullPointerException();
        return getHash(string.getBytes(), algo);
    }

    /**
     * バイト列のハッシュ値を文字列で返す
     * 
     * @param input
     *            ハッシュを計算するバイト列
     * @param algo
     *            ハッシュアルゴリズム(MD5やSHA-1など)
     * @return ハッシュ値
     */
    public static String getHash(byte[] input, String algo) {
        if ((input == null) || (algo == null)) {
            return null;
        }

        MessageDigest md;
        try {
            md = MessageDigest.getInstance(algo);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }

        md.reset();
        md.update(input);

        return toHashString(md.digest());
    }

    /**
     * ハッシュバイト列を16進文字列に変換する
     * 
     * @param b
     *            変換対象のバイト列
     * @return このbyteの16進数表現
     */
    private static String toHashString(byte[] b) {
        int cnt = b.length;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < cnt; i++) {
            sb.append(Integer.toHexString((b[i] >> 4) & 0xF));
            sb.append(Integer.toHexString(b[i] & 0xF));
        }
        return sb.toString();
    }
}

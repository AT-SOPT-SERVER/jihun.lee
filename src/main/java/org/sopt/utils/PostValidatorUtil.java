package org.sopt.utils;

public class PostValidatorUtil {

    public static int countGraphemeClusters(String s) {
        int count = 0;
        int i = 0;
        final int len = s.length();

        while (i < len) {
            i = nextGraphemeClusterIndex(s, i, len);
            count++;
        }

        return count;
    }

    private static int nextGraphemeClusterIndex(String s, int start, int len) {
        int i = start;
        int cp = s.codePointAt(i);

        if (isRegionalIndicator(cp) && (i + Character.charCount(cp) < len)) {
            int nextCp = s.codePointAt(i + Character.charCount(cp));

            if (isRegionalIndicator(nextCp)) {
                i += Character.charCount(cp) + Character.charCount(nextCp);
                return skipZWJ(s, i, len);
            }
        }
        i += Character.charCount(cp);

        return skipZWJ(s, i, len);
    }

    private static int skipZWJ(String s, int index, int len) {
        while (index < len) {
            int cp = s.codePointAt(index);

            if (cp == 0x200D) {
                index += Character.charCount(cp);

                if (index < len) {
                    int cpAfterZWJ = s.codePointAt(index);
                    index += Character.charCount(cpAfterZWJ);
                }
            } else {
                break;
            }
        }

        return index;
    }

    private static boolean isRegionalIndicator(int cp) {

        return cp >= 0x1F1E6 && cp <= 0x1F1FF;
    }

}

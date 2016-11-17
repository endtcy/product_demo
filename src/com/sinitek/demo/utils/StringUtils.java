package com.sinitek.demo.utils;

import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Collection;
import java.util.Map;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class StringUtils {
    private static final int ALIAS_TRUNCATE_LENGTH = 10;

    public static final String WHITESPACE = " \n\r\f\t";

    private StringUtils() { /* static methods only - hide constructor */
    }

    /*
      * public static boolean containsDigits(String string) { for ( int i=0; i<string.length();
      * i++ ) { if ( Character.isDigit( string.charAt(i) ) ) return true; }
      * return false; }
      */

    public static int lastIndexOfLetter(String string) {
        for (int i = 0; i < string.length(); i++) {
            char character = string.charAt(i);
            if (!Character.isLetter(character) /* && !('_'==character) */)
                return i - 1;
        }
        return string.length() - 1;
    }

    public static String join(String seperator, String[] strings) {
        int length = strings.length;
        if (length == 0)
            return "";
        StringBuffer buf = new StringBuffer(length * strings[0].length())
                .append(strings[0]);
        for (int i = 1; i < length; i++) {
            buf.append(seperator).append(strings[i]);
        }
        return buf.toString();
    }

    public static String join(String seperator, Iterator objects) {
        StringBuffer buf = new StringBuffer();
        if (objects.hasNext())
            buf.append(objects.next());
        while (objects.hasNext()) {
            buf.append(seperator).append(objects.next());
        }
        return buf.toString();
    }

    public static String[] add(String[] x, String sep, String[] y) {
        String[] result = new String[x.length];
        for (int i = 0; i < x.length; i++) {
            result[i] = x[i] + sep + y[i];
        }
        return result;
    }

    public static String repeat(String string, int times) {
        StringBuffer buf = new StringBuffer(string.length() * times);
        for (int i = 0; i < times; i++)
            buf.append(string);
        return buf.toString();
    }

    public static String replace(String template, String placeholder,
                                 String replacement) {
        return StringUtils.replace(template, placeholder, replacement, false);
    }

    public static String[] replace(String templates[], String placeholder,
                                   String replacement) {
        String[] result = new String[templates.length];
        for (int i = 0; i < templates.length; i++) {
            result[i] = StringUtils.replace(templates[i], placeholder, replacement);
            ;
        }
        return result;
    }

    public static String replace(String template, String placeholder,
                                 String replacement, boolean wholeWords) {
        int loc = template == null ? -1 : template.indexOf(placeholder);
        if (loc < 0) {
            return template;
        } else {
            final boolean actuallyReplace = !wholeWords
                    || loc + placeholder.length() == template.length()
                    || !Character.isJavaIdentifierPart(template.charAt(loc
                            + placeholder.length()));
            String actualReplacement = actuallyReplace ? replacement
                    : placeholder;
            return new StringBuffer(template.substring(0, loc)).append(
                    actualReplacement).append(
                    StringUtils.replace(template.substring(loc + placeholder.length()),
                            placeholder, replacement, wholeWords)).toString();
        }
    }

    public static String replaceOnce(String template, String placeholder,
                                     String replacement) {
        int loc = template == null ? -1 : template.indexOf(placeholder);
        if (loc < 0) {
            return template;
        } else {
            return new StringBuffer(template.substring(0, loc)).append(
                    replacement).append(
                    template.substring(loc + placeholder.length())).toString();
        }
    }

    public static String[] split(String seperators, String list) {
        return StringUtils.split(seperators, list, false);
    }

    public static String[] split(String seperators, String list, boolean include) {
        StringTokenizer tokens = new StringTokenizer(list, seperators, include);
        String[] result = new String[tokens.countTokens()];
        int i = 0;
        while (tokens.hasMoreTokens()) {
            result[i++] = tokens.nextToken();
        }
        return result;
    }

    public static String unqualify(String qualifiedName) {
        int loc = qualifiedName.lastIndexOf(".");
        return (loc < 0) ? qualifiedName : qualifiedName
                .substring(qualifiedName.lastIndexOf(".") + 1);
    }

    public static String qualifier(String qualifiedName) {
        int loc = qualifiedName.lastIndexOf(".");
        return (loc < 0) ? "" : qualifiedName.substring(0, loc);
    }

    public static String[] suffix(String[] columns, String suffix) {
        if (suffix == null)
            return columns;
        String[] qualified = new String[columns.length];
        for (int i = 0; i < columns.length; i++) {
            qualified[i] = StringUtils.suffix(columns[i], suffix);
        }
        return qualified;
    }

    private static String suffix(String name, String suffix) {
        return (suffix == null) ? name : name + suffix;
    }

    public static String root(String qualifiedName) {
        int loc = qualifiedName.indexOf(".");
        return (loc < 0) ? qualifiedName : qualifiedName.substring(0, loc);
    }

    public static String unroot(String qualifiedName) {
        int loc = qualifiedName.indexOf(".");
        return (loc < 0) ? qualifiedName : qualifiedName.substring(loc + 1,
                qualifiedName.length());
    }

    public static boolean booleanValue(String tfString) {
        String trimmed = tfString.trim().toLowerCase();
        return trimmed.equals("true") || trimmed.equals("t");
    }

    public static String toString(Object[] array) {
        int len = array.length;
        if (len == 0)
            return "";
        StringBuffer buf = new StringBuffer(len * 12);
        for (int i = 0; i < len - 1; i++) {
            buf.append(array[i]).append(", ");
        }
        return buf.append(array[len - 1]).toString();
    }

    public static String[] multiply(String string, Iterator placeholders,
                                    Iterator replacements) {
        String[] result = new String[] { string };
        while (placeholders.hasNext()) {
            result = StringUtils.multiply(result, (String) placeholders.next(),
                    (String[]) replacements.next());
        }
        return result;
    }

    private static String[] multiply(String[] strings, String placeholder,
                                     String[] replacements) {
        String[] results = new String[replacements.length * strings.length];
        int n = 0;
        for (int i = 0; i < replacements.length; i++) {
            for (int j = 0; j < strings.length; j++) {
                results[n++] = StringUtils.replaceOnce(strings[j], placeholder,
                        replacements[i]);
            }
        }
        return results;
    }

    public static int countUnquoted(String string, char character) {
        if ('\'' == character) {
            throw new IllegalArgumentException(
                    "Unquoted count of quotes is invalid");
        }
        if (string == null)
            return 0;
        // Impl note: takes advantage of the fact that an escpaed single quote
        // embedded within a quote-block can really be handled as two seperate
        // quote-blocks for the purposes of this method...
        int count = 0;
        int stringLength = string.length();
        boolean inQuote = false;
        for (int indx = 0; indx < stringLength; indx++) {
            char c = string.charAt(indx);
            if (inQuote) {
                if ('\'' == c) {
                    inQuote = false;
                }
            } else if ('\'' == c) {
                inQuote = true;
            } else if (c == character) {
                count++;
            }
        }
        return count;
    }

    /*
      * public static int[] locateUnquoted(String string, char character) { if (
      * '\'' == character ) { throw new IllegalArgumentException( "Unquoted count
      * of quotes is invalid" ); } if (string == null) { return new int[0]; }
      *
      * ArrayList locations = new ArrayList( 20 ); // Impl note: takes advantage
      * of the fact that an escpaed single quote // embedded within a quote-block
      * can really be handled as two seperate // quote-blocks for the purposes of
      * this method... int stringLength = string.length(); boolean inQuote =
      * false; for ( int indx = 0; indx < stringLength; indx++ ) { char c =
      * string.charAt( indx ); if ( inQuote ) { if ( '\'' == c ) { inQuote =
      * false; } } else if ( '\'' == c ) { inQuote = true; } else if ( c ==
      * character ) { locations.add( new Integer( indx ) ); } } return
      * ArrayHelper.toIntArray( locations ); }
      */

    public static boolean isNotEmpty(String string) {
        return string != null && string.length() > 0;
    }

    public static boolean isEmpty(String string) {
        return string == null || string.length() == 0;
    }

    public static String qualify(String prefix, String name) {
        if (name == null || prefix == null) {
            throw new NullPointerException();
        }
        return new StringBuffer(prefix.length() + name.length() + 1).append(
                prefix).append('.').append(name).toString();
    }

    public static String[] qualify(String prefix, String[] names) {
        if (prefix == null)
            return names;
        int len = names.length;
        String[] qualified = new String[len];
        for (int i = 0; i < len; i++) {
            qualified[i] = StringUtils.qualify(prefix, names[i]);
        }
        return qualified;
    }

    public static int firstIndexOfChar(String sqlString, String string,
                                       int startindex) {
        int matchAt = -1;
        for (int i = 0; i < string.length(); i++) {
            int curMatch = sqlString.indexOf(string.charAt(i), startindex);
            if (curMatch >= 0) {
                if (matchAt == -1) { // first time we find match!
                    matchAt = curMatch;
                } else {
                    matchAt = Math.min(matchAt, curMatch);
                }
            }
        }
        return matchAt;
    }

    public static String truncate(String string, int length) {
        if (string.length() <= length) {
            return string;
        } else {
            return string.substring(0, length);
        }
    }

    public static String generateAlias(String description) {
        return StringUtils.generateAliasRoot(description) + '_';
    }

    /**
     * Generate a nice alias for the given class name or collection role name
     * and unique integer. Subclasses of ILoader do <em>not</em> have to use
     * aliases of this form.
     *
     * @return an alias of the form <tt>foo1_</tt>
     */
    public static String generateAlias(String description, int unique) {
        return StringUtils.generateAliasRoot(description) + Integer.toString(unique) + '_';
    }

    /**
     * Generates a root alias by truncating the "root name" defined by the
     * incoming decription and removing/modifying any non-valid alias
     * characters.
     *
     * @param description
     *            The root name from which to generate a root alias.
     * @return The generated root alias.
     */
    private static String generateAliasRoot(String description) {
        String result = StringUtils.truncate(StringUtils.unqualifyEntityName(description),
                StringUtils.ALIAS_TRUNCATE_LENGTH).toLowerCase().replace('/', '_') // entityNames
                // may
                // now
                // include
                // slashes
                // for
                // the
                // representations
                .replace('$', '_'); // classname may be an inner class
        result = StringUtils.cleanAlias(result);
        if (Character.isDigit(result.charAt(result.length() - 1))) {
            return result + "x"; // ick!
        } else {
            return result;
        }
    }

    /**
     * Clean the generated alias by removing any non-alpha characters from the
     * beginning.
     *
     * @param alias
     *            The generated alias to be cleaned.
     * @return The cleaned alias, stripped of any leading non-alpha characters.
     */
    private static String cleanAlias(String alias) {
        char[] chars = alias.toCharArray();
        // short cut check...
        if (!Character.isLetter(chars[0])) {
            for (int i = 1; i < chars.length; i++) {
                // as soon as we encounter our first letter, return the
                // substring
                // from that position
                if (Character.isLetter(chars[i])) {
                    return alias.substring(i);
                }
            }
        }
        return alias;
    }

    public static String unqualifyEntityName(String entityName) {
        String result = StringUtils.unqualify(entityName);
        int slashPos = result.indexOf('/');
        if (slashPos > 0) {
            result = result.substring(0, slashPos - 1);
        }
        return result;
    }

    public static String toUpperCase(String str) {
        return str == null ? null : str.toUpperCase();
    }

    public static String toLowerCase(String str) {
        return str == null ? null : str.toLowerCase();
    }

    public static String moveAndToBeginning(String filter) {
        if (filter.trim().length() > 0) {
            filter += " and ";
            if (filter.startsWith(" and "))
                filter = filter.substring(4);
        }
        return filter;
    }

    public static void checkBlank(String str) {
        if (str == null || str.trim() == "") {
            throw new IllegalArgumentException("paramter should not be blank");
        }
    }

    public static boolean isBlank(String str) {
        if (str == null || str.trim() == "") {
            return true;
        }
        return false;
    }

    /**
     * Check if a String has length.
     * <p>
     *
     * <pre>
     *    StringUtils.hasLength(null) = false
     *    StringUtils.hasLength(&quot;&quot;) = false
     *    StringUtils.hasLength(&quot; &quot;) = true
     *    StringUtils.hasLength(&quot;Hello&quot;) = true
     * </pre>
     *
     * @param str
     *            the String to check, may be <code>null</code>
     * @return <code>true</code> if the String is not null and has length
     */
    public static boolean hasLength(String str) {
        return (str != null && str.length() > 0);
    }

    /**
     * Check if a String has text. More specifically, returns <code>true</code>
     * if the string not <code>null<code>, it's <code>length is > 0</code>, and
     * it has at least one non-whitespace character.
     * <p><pre>
     *    StringUtils.hasText(null) = false
     *    StringUtils.hasText(&quot;&quot;) = false
     *    StringUtils.hasText(&quot; &quot;) = false
     *    StringUtils.hasText(&quot;12345&quot;) = true
     *    StringUtils.hasText(&quot; 12345 &quot;) = true
     * </pre>
     * @param str the String to check, may be <code>null</code>
     * @return <code>true</code> if the String is not null, length > 0,
     * and not whitespace only
     * @see Character#isWhitespace
     */
    public static boolean hasText(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return false;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static String build(Object obj) {
        String result = "[" + obj.getClass().getSimpleName() + "]\n";
        Field[] fields = obj.getClass().getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                result += "  " + field.getName() + ":" + field.get(obj) + "\n";
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        result += "[/" + obj.getClass().getSimpleName() + "]\n";
        return result;
    }
    //Ignore Maps or Collections
    public static String buildEntity(Object obj) {
        String result = "[" + obj.getClass().getSimpleName() + "]\n";
        Field[] fields = obj.getClass().getDeclaredFields();
        try {
            for (Field field : fields) {
                if(Collection.class.isAssignableFrom(field.getType()))continue;
                if(Map.class.isAssignableFrom(field.getType()))continue;
//				if(field.getType().isAnnotationPresent(Entity.class))continue;
                if(Modifier.isStatic(field.getModifiers()))continue;
                field.setAccessible(true);
                result += "  " + field.getName() + ":" + field.get(obj) + "\n";
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        result += "[/" + obj.getClass().getSimpleName() + "]\n";
        return result;
    }

    public static String capitalize(String str){
        return str.substring(0,1).toUpperCase()+str.substring(1);
    }

    public static String uncapitalize(String str){
        return str.substring(0,1).toLowerCase()+str.substring(1);
    }

}
